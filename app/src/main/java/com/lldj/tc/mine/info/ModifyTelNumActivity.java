package com.lldj.tc.mine.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.login.VerifyCodeLogin.IVerifyLoginPresenterCompl;
import com.lldj.tc.login.VerifyCodeLogin.IVerifyloginView;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改手机号码
 */
public class ModifyTelNumActivity extends BaseActivity implements IVerifyloginView {


    public static final String TEL_NUM = "telNum";
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
    @BindView(R.id.save_tv)
    TextView saveTv;
    private IVerifyLoginPresenterCompl iLoginPresenter;
    private String mTelNumStr;

    public static void launch(Context pContext, String pTelNum) {
        Intent mIntent = new Intent(pContext, ModifyTelNumActivity.class);
        mIntent.putExtra(TEL_NUM, pTelNum);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        toolbarTitleTv.setText("修改手机号");
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        mTelNumStr = getIntent().getStringExtra(TEL_NUM);
        //设置登录按钮的状态
        setLoginStatus(false);
        iLoginPresenter = new IVerifyLoginPresenterCompl(this);
        iLoginPresenter.telNumOperate(telNumEt, telStatus);
        iLoginPresenter.verifyCodeOperate(verifyCodeEt);
        if (!TextUtils.isEmpty(mTelNumStr)) {
            telNumEt.setText(mTelNumStr);
        }
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
        if (isEnable) {
            saveTv.setEnabled(true);
            saveTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            saveTv.setEnabled(false);
            saveTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }


    @OnClick({R.id.toolbar_back_iv, R.id.get_verify_code, R.id.save_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.get_verify_code:
                iLoginPresenter.getVerifyCode(getVerifyCode);
                break;
            case R.id.save_tv:
                mTelNumStr = telNumEt.getText().toString().trim();
                SharePreUtils.setTelNum(mContext, mTelNumStr);
                finish();
                break;
        }
    }
}
