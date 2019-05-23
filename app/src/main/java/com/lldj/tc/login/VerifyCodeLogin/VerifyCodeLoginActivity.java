package com.lldj.tc.login.VerifyCodeLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.JobSelectActivity;
import com.lldj.tc.register.RegisterActivity;
import com.lldj.tc.webview.WebviewNormalActivity;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 验证码登录
 */
public class VerifyCodeLoginActivity extends BaseActivity implements IVerifyloginView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.quhao_tv)
    TextView quhaoTv;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tel_num_et)
    EditText telNumEt;
    @BindView(R.id.tel_status)
    ImageView telStatus;
    @BindView(R.id.tel_num_line)
    View telNumLine;
    @BindView(R.id.tel_layout)
    RelativeLayout telLayout;
    @BindView(R.id.account_not_exist_tip)
    TextView accountNotExistTip;
    @BindView(R.id.get_verify_code)
    TextView getVerifyCode;
    @BindView(R.id.verify_code_status)
    ImageView verifyCodeStatus;
    @BindView(R.id.verify_code_et)
    EditText verifyCodeEt;
    @BindView(R.id.verify_code_line)
    View verifyCodeLine;
    @BindView(R.id.verify_code_tip)
    TextView verifyCodeTip;
    @BindView(R.id.verify_code_layout)
    RelativeLayout verifyCodeLayout;
    @BindView(R.id.account_psw_login_tv)
    TextView accountPswLoginTv;
    @BindView(R.id.register_tv)
    TextView registerTv;
    @BindView(R.id.register_layout)
    RelativeLayout registerLayout;
    @BindView(R.id.other_layout)
    RelativeLayout otherLayout;
    @BindView(R.id.service_agrement_checkbox)
    CheckBox serviceAgrementCheckbox;
    @BindView(R.id.service_agrement)
    TextView serviceAgrement;
    @BindView(R.id.quick_login_tv)
    TextView quickLoginTv;
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    private IVerifyLoginPresenterCompl iLoginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        toolbarTitleTv.setText(mResources.getString(R.string.login));
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        //设置登录按钮的状态
        setLoginStatus(false);
        iLoginPresenter = new IVerifyLoginPresenterCompl(this);
        iLoginPresenter.telNumOperate(telNumEt, telStatus);
        iLoginPresenter.verifyCodeOperate(verifyCodeEt);
        serviceAgrementCheckbox.setOnCheckedChangeListener(this);
    }


    @Override
    public void telNumFocus(boolean isFocus) {
        if (isFocus) {
            telNumLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            telNumLine.setBackgroundColor(getResources().getColor(R.color.color_969696));
        }
    }

    @Override
    public void verifyCodeFocus(boolean isFocus) {
        if (isFocus) {
            verifyCodeLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            verifyCodeLine.setBackgroundColor(getResources().getColor(R.color.color_969696));
        }
    }

    @Override
    public void setLoginStatus(boolean isEnable) {
        if (isEnable && serviceAgrementCheckbox.isChecked()) {
            quickLoginTv.setEnabled(true);
            quickLoginTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            quickLoginTv.setEnabled(false);
            quickLoginTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }


    @OnClick({R.id.get_verify_code, R.id.account_psw_login_tv, R.id.register_layout, R.id.service_agrement, R.id.quick_login_tv, R.id.toolbar_back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_verify_code:
                iLoginPresenter.getVerifyCode(getVerifyCode);
                break;
            case R.id.account_psw_login_tv:
                finish();
                break;
            case R.id.register_layout:
                RegisterActivity.launch(this,0);
                break;
            case R.id.service_agrement:
                WebviewNormalActivity.launch(VerifyCodeLoginActivity.this,"服务协议");
                break;
            case R.id.quick_login_tv:
                startActivity(new Intent(this, JobSelectActivity.class));
                break;
            case R.id.toolbar_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


    }


}
