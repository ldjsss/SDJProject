package com.lldj.tc.mine.verify;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/10 14:55<p>
 * Modify Time: 2018/12/10 14:55<p>
 */


public class IDTypeBean {
    private int id;
    private String name;

    public IDTypeBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
