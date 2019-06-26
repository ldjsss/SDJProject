package com.lldj.tc.httpMgr.beans;

import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;

public class JsonBean{
    private int code = -9999;
    private String message = "";
    private ResultsModel result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public ResultsModel getResult() {
        return result;
    }
    public void setResults(ResultsModel result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}


