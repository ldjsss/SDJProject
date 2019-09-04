package com.lldj.tc.match;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.beans.BordBean;
import com.lldj.tc.info.Adapter_MessCell;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.util.AppUtils.DEBUG;

public class DialogNotice extends BaseDialog implements LRecyclerView.LScrollListener{

    private Adapter_MessCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private int mTotal = 0;
    private LinearLayoutManager layoutManager;
    @BindView(R.id.jingcairecycleview)
    LRecyclerView jingcairecycleview;

    private List<BordBean.BordMode> messList = new ArrayList<>();

    public DialogNotice(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_notice_layout, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_bet_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);

        initRecycleview();

    }

    @Override
    public void show() {
        super.show();

        messList.clear();

        messList = SharePreUtils.getInstance().getBordlist();
        if(messList == null) messList = new ArrayList<>();
        mTotal = messList.size();
       if(mAdapter != null) mAdapter.changeData(messList);
    }

    @Override
    public void onRefresh() {
    }

    private void initRecycleview() {

        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new Adapter_MessCell(getContext());
            lAdapter = new LRecyclerViewAdapter(getContext(), mAdapter);
            jingcairecycleview.setAdapter(lAdapter);
            jingcairecycleview.setLScrollListener(this);
            jingcairecycleview.setNoMore(true);
            lAdapter.setPullRefreshEnabled(false);
        }

        mAdapter.changeData(messList);
    }

    @Override
    public void onScrollUp() {
        if(DEBUG)Log.e("onScrollUp", "onScrollUp");
    }

    @Override
    public void onScrollDown() {
        if(DEBUG) Log.e("onScrollDown", "onScrollDown");
    }

    @Override
    public void onBottom() {
//        Log.e("打印", "滚动到底部");
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
    }


    @OnClick({R.id.closelayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closelayout:
                DialogManager.getInstance().removeDialog(this);
                break;
        }
    }

}
