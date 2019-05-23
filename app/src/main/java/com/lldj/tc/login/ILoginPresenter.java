package com.lldj.tc.login;

import android.widget.EditText;
import android.widget.ImageView;

public interface ILoginPresenter {
    void login();
    void telNumOperate(EditText pTelNumEt, ImageView pTelStatusIv);
    void pswOperate(EditText pPswEt);
    void pswStatus(EditText pPswEt, ImageView pPswStatusIv);
}
