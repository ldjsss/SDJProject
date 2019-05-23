package com.lldj.tc.login.VerifyCodeLogin;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public interface IVerifyLoginPresenter {
    void login();
    void telNumOperate(EditText pTelNumEt, ImageView pTelStatusIv);
    void verifyCodeOperate(EditText pverifyCodeEt);
    void getVerifyCode(TextView pGetVerifyCodeTv);
}
