package com.lldj.tc.login;

public interface IloginView {
    void onSubmitResult();
    void telNumFocus(boolean isFocus);
    void pswFocus(boolean isFocus);
    void setLoginStatus(boolean isEnable);
}
