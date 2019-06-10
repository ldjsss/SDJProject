package com.lldj.tc.httpMgr.beans.FormatModel;

public class JsonBean {
    private int code = -9999;
    private String message = "";
    private Results result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public Results getResult() {
        return result;
    }
    public void setResults(Results result) {
        this.result = result;
    }
}


