package com.lldj.tc.mine.info;

import java.util.ArrayList;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/7 13:38<p>
 * Modify Time: 2018/12/7 13:38<p>
 */


public class DepartmentOneBean {
    private boolean isSelect;
    private String dapartment;
    private ArrayList<DepartmentOneBean> mtwoDepartmentList;

    public DepartmentOneBean(String dapartment) {
        this.dapartment = dapartment;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getDapartment() {
        return dapartment;
    }

    public void setDapartment(String dapartment) {
        this.dapartment = dapartment;
    }

    public ArrayList<DepartmentOneBean> getMtwoDepartmentList() {
        return mtwoDepartmentList;
    }

    public void setMtwoDepartmentList(ArrayList<DepartmentOneBean> mtwoDepartmentList) {
        this.mtwoDepartmentList = mtwoDepartmentList;
    }
}
