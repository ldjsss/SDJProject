package com.lldj.tc.httpMgr.beans.test;

import java.util.List;

public class JsonBean {

    private int error;
    private String status;
    private List<Results> results;
    public int getError() {
        return error;
    }
    public void setError(int error) {
        this.error = error;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Results> getResults() {
        return results;
    }
    public void setResults(List<Results> results) {
        this.results = results;
    }
}


