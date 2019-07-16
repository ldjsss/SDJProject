package com.lldj.tc.match;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.BetMatchBean;
import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.info.Activity_Records;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class DialogBetResult extends BaseDialog {

    @BindView(R.id.betlrecycleview)
    RecyclerView betlrecycleview;

    public DialogBetResult(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.betresult, null);
        setContentView(view);

        ButterKnife.bind(this, view);

        this.setCanceledOnTouchOutside(false);

        getWindow().setDimAmount(0.5f);

        Window window = this.getWindow();
        window.setLayout((int) (AppUtils.getDisplayMetrics(getContext()).widthPixels * 0.96), ViewGroup.LayoutParams.WRAP_CONTENT);//设置对话框大小
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
    }

    public void showView(List<BetMatchBean.betResult> list) {
        int len = list.size() >= 2 ? 2 : 1;
        betlrecycleview.getLayoutParams().height = betlrecycleview.getLayoutParams().height * len;
        betlrecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        betlrecycleview.setAdapter(new Adapter_BetResultCell(getContext(), true, list));
        betlrecycleview.setItemAnimator(new DefaultItemAnimator());

        show();
    }

    @OnClick({R.id.bt_betlist, R.id.bt_continue})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_betlist:
                bActivity.startActivity(new Intent(bActivity, Activity_Records.class));
                dismiss();
                break;
            case R.id.bt_continue:
                dismiss();
                break;
        }
    }
}
