package com.lldj.tc.match;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lldj.tc.R;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.MatchBean;
import com.lldj.tc.mainUtil.GlobalVariable;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogGameSelect extends BaseDialog {
    List<ResultsModel> list;
    @BindView(R.id.gamerecycleview)
    RecyclerView gamerecycleview;

    public DialogGameSelect(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.gameselect, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window window = this.getWindow();
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.animStyleTop);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);
    }

    public void showView() {
        HttpMsg.getInstance().sendGetGameList(MatchBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                MatchBean res = (MatchBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    list = (List<ResultsModel>) res.getResult();

                    gamerecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    gamerecycleview.setAdapter(new Adapter_GameSelect(getContext(), list));
                    gamerecycleview.setItemAnimator(new DefaultItemAnimator());
                }
            }
        });

        show();
    }

    @OnClick({R.id.toolbar_left_menu_iv, R.id.toolbar_gameselect, R.id.connectservice, R.id.tv_selectgame})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_menu_iv:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEFTMENU);
                break;
            case R.id.toolbar_gameselect:
                dismiss();
                break;
            case R.id.connectservice:
                Toast.makeText(getContext(),"---------------test2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_selectgame:

                break;
        }
    }
}
