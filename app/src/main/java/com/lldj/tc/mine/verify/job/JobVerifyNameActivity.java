package com.lldj.tc.mine.verify.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 职业认证--真实姓名<p>
 * user: wlj<p>
 * Creat Time: 2018/12/17 10:18<p>
 * Modify Time: 2018/12/17 10:18<p>
 */


public class JobVerifyNameActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.real_name_et)
    EditText realNameEt;
    @BindView(R.id.next_step_tv)
    TextView nextStepTv;
    private String mRealNameStr;

    public static void launch(Activity pContext) {
        Intent mIntent = new Intent(pContext, JobVerifyNameActivity.class);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_job_name);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        realNameEt.addTextChangedListener(this);
        toolbarTitleTv.setText("职业认证");
        nextstepStatus();
    }

    @OnClick({R.id.toolbar_back_iv, R.id.next_step_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.next_step_tv:
                JobVerifyJobActivity.launch(mContext);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        nextstepStatus();
    }

    /**
     * 下一步按钮的状态
     */
    public void nextstepStatus() {
        mRealNameStr = realNameEt.getText().toString().trim();
        if (!TextUtils.isEmpty(mRealNameStr)) {
            nextStepTv.setEnabled(true);
            nextStepTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            nextStepTv.setEnabled(false);
            nextStepTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
