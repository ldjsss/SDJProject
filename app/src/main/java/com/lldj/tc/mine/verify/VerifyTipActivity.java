package com.lldj.tc.mine.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.mine.verify.job.JobVerifyNameActivity;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 实名认证第一页<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/10 11:24<p>
 * Modify Time: 2018/12/10 11:24<p>
 */


public class VerifyTipActivity extends BaseActivity {
    public static final String TYPE = "type";
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.start_verify_tv)
    TextView startVerifyTv;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    //0职业认证 1个人认证
    private int mType;

    public static void launch(Context pContext, int pType) {
        Intent mIntent = new Intent(pContext, VerifyTipActivity.class);
        mIntent.putExtra(TYPE, pType);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        mType = getIntent().getIntExtra(TYPE, 0);
        if (mType == 0) {
            toolbarTitleTv.setText(getResourcesString(R.string.job_verify));
            tipTv.setText(getResourcesString(R.string.job_tip));
        } else {
            toolbarTitleTv.setText(getResourcesString(R.string.verify_str));
            tipTv.setText(getResourcesString(R.string.tips1));
        }
    }

    @OnClick({R.id.toolbar_back_iv, R.id.start_verify_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.start_verify_tv:
                if (mType == 1) {
                    VerifyInputIdentityInfoActivity.launch(mContext);
                } else {
                    JobVerifyNameActivity.launch(mContext);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            finish();
        }
    }
}
