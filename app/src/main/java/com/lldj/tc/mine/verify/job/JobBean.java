package com.lldj.tc.mine.verify.job;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/17 11:13<p>
 * Modify Time: 2018/12/17 11:13<p>
 */


public class JobBean {
    private String job;
    private boolean select;

    public JobBean(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
