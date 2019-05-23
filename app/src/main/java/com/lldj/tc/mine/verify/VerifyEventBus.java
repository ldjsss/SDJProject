package com.lldj.tc.mine.verify;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/5 20:02<p>
 * Modify Time: 2018/12/5 20:02<p>
 */


public class VerifyEventBus {
    //是否为实名认证
    private boolean isRealNameVerify;

    public VerifyEventBus(boolean isRealNameVerify) {
        this.isRealNameVerify = isRealNameVerify;
    }

    public boolean isRealNameVerify() {
        return isRealNameVerify;
    }

    public void setRealNameVerify(boolean realNameVerify) {
        isRealNameVerify = realNameVerify;
    }
}
