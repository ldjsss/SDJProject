package com.lldj.tc.http.beans;

import com.lldj.tc.http.beans.FormatModel.ResultsModel;

import java.util.List;

public class MatchBean{
    private int code = -9999;
    private String message = "";
    private List<ResultsModel> result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultsModel> getResult() {
        return result;
    }
    public void setResults(List<ResultsModel> result) {
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


