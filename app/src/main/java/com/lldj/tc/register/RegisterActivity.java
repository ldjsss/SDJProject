package com.lldj.tc.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.JobSelectActivity;
import com.lldj.tc.R;
import com.lldj.tc.webview.WebviewNormalActivity;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 注册or忘记密码<p>
 * user: wlj<p>
 * Creat Time: 2018/11/30 9:34<p>
 * Modify Time: 2018/11/30 9:34<p>
 */
public class RegisterActivity extends BaseActivity implements IRegisterView {
    public static final String TYPE = "type";
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
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
    @BindView(R.id.tel_root_layout)
    RelativeLayout telRootLayout;
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
    @BindView(R.id.verify_code_root_layout)
    RelativeLayout verifyCodeRootLayout;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw_show_or_hid_iv)
    ImageView pswShowOrHidIv;
    @BindView(R.id.psw_line)
    View pswLine;
    @BindView(R.id.psw_layout)
    RelativeLayout pswLayout;
    @BindView(R.id.psw_wrong_tip)
    TextView pswWrongTip;
    @BindView(R.id.psw_status)
    ImageView pswStatus;
    @BindView(R.id.psw_root_layout)
    RelativeLayout pswRootLayout;
    @BindView(R.id.service_agrement_checkbox)
    CheckBox serviceAgrementCheckbox;
    @BindView(R.id.service_agrement)
    TextView serviceAgrement;
    @BindView(R.id.agreement_root_layout)
    LinearLayout agreementRootLayout;
    @BindView(R.id.register_tv)
    TextView registerTv;
    @BindView(R.id.goto_login_tv)
    TextView gotoLoginTv;
    @BindView(R.id.goto_login_layout)
    RelativeLayout gotoLoginLayout;
    private IRegisterPresenterCompl iPresenter;

    public static void launch(Context pContext, int pType) {
        Intent mIntent = new Intent(pContext, RegisterActivity.class);
        mIntent.putExtra(TYPE, pType);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        int mType = getIntent().getIntExtra(TYPE, 0);
        switch (mType) {
            case 0:
                //注册
                toolbarTitleTv.setText(mResources.getString(R.string.register_str));
                registerTv.setText(mResources.getString(R.string.register_str));
                gotoLoginLayout.setVisibility(View.VISIBLE);

                break;
            case 1:
                //忘记密码
                toolbarTitleTv.setText(mResources.getString(R.string.forget_psw_str));
                registerTv.setText(mResources.getString(R.string.login));
                gotoLoginLayout.setVisibility(View.GONE);
                break;
        }

        //注册按钮的背景颜色
        setRegisterBgStatus(false);
        iPresenter = new IRegisterPresenterCompl(this, serviceAgrementCheckbox);
        iPresenter.telNumChangedListener(telNumEt, telStatus);
        iPresenter.verifyCodeChangeListener(verifyCodeEt);
        iPresenter.pswChangeListener(pswEt);
    }


    @Override
    public void telNumFocus(boolean isFocus) {
        lineChange(telNumLine, isFocus);
    }

    @Override
    public void verifyCodeFocus(boolean isFocus) {
        lineChange(verifyCodeLine, isFocus);
    }

    @Override
    public void pswFocus(boolean isFocus) {
        lineChange(pswLine, isFocus);
    }

    @Override
    public void setRegisterBgStatus(boolean isEnable) {
        if (isEnable) {
            registerTv.setEnabled(true);
            registerTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            registerTv.setEnabled(false);
            registerTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }


    @OnClick({R.id.toolbar_back_iv, R.id.get_verify_code, R.id.service_agrement, R.id.register_tv, R.id.psw_show_or_hid_iv, R.id.goto_login_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                exitActivity();
                break;
            case R.id.get_verify_code:
                iPresenter.getVerifyCode(getVerifyCode);
                break;
            case R.id.service_agrement:
                WebviewNormalActivity.launch(RegisterActivity.this,"服务协议");
                break;
            case R.id.register_tv:
                JobSelectActivity.launch(RegisterActivity.this);
                exitActivity();
                break;
            case R.id.psw_show_or_hid_iv:
                iPresenter.pswStatus(pswEt, pswShowOrHidIv);
                break;
            case R.id.goto_login_layout:
                exitActivity();
                break;

        }
    }

    //焦点在时的线的颜色
    private void lineChange(View pView, boolean isFocus) {
        if (isFocus) {
            pView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            pView.setBackgroundColor(getResources().getColor(R.color.color_969696));
        }
    }


    @Override
    public void onBackPressed() {
        RxTimerUtil.cancel();
    }

    public void exitActivity() {
        RxTimerUtil.cancel();
        finish();
    }
}
