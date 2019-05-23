package com.lldj.tc.register;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public interface IRegisterPresenter {
    void login();
    void telNumChangedListener(EditText pTelNumEt, ImageView pTelStatusIv);
    void verifyCodeChangeListener(EditText pverifyCodeEt);
    void getVerifyCode(TextView pGetVerifyCodeTv);
    //密码
    void pswChangeListener(EditText pPswEt);
    void pswStatus(EditText pPswEt, ImageView pPswStatusIv);
}
