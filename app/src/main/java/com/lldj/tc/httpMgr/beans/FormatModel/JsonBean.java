package com.lldj.tc.httpMgr.beans.FormatModel;

public class JsonBean <T>{
    private int code = -9999;
    private String message = "";
    private T result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }
    public void setResults(T result) {
        this.result = result;
    }

}


