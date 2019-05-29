package com.lldj.tc.httpMgr.beans.test;

import java.util.List;

public class Results {

    private String currentCity;
    private List<Index> index;
    public String getCurrentCity() {
        return currentCity;
    }
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
    public List<Index> getIndex() {
        return index;
    }
    public void setIndex(List<Index> index) {
        this.index = index;
    }

}
