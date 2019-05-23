package com.lldj.tc.mine.verify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/10 17:26<p>
 * Modify Time: 2018/12/10 17:26<p>
 */


public class VerifySuccActivity extends BaseActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.coninue_job_renzheng_tv)
    TextView coninueJobRenzhengTv;
    @BindView(R.id.goto_mycenter_tv)
    TextView gotoMycenterTv;

    public static void launch(Activity pContext) {
        Intent mIntent = new Intent(pContext, VerifySuccActivity.class);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_succ);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        titleTv.setText(getResourcesString(R.string.verify_str));
    }

    @OnClick({R.id.coninue_job_renzheng_tv, R.id.goto_mycenter_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.coninue_job_renzheng_tv:
                setResult(100);
                finish();
                break;
            case R.id.goto_mycenter_tv:
                EventBus.getDefault().post(new VerifyEventBus(true));
                setResult(100);
                finish();
                break;
        }
    }
}
