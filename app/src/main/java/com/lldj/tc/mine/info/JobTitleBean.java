package com.lldj.tc.mine.info;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/7 13:38<p>
 * Modify Time: 2018/12/7 13:38<p>
 */


public class JobTitleBean {
    private boolean isSelect;
    private String jobtitle;

    public JobTitleBean(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }
}
