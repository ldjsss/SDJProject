package com.lldj.tc.httpMgr.beans;

import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.BetModel;

import java.util.List;

public class BetMatchBean {
    private int code;
    private String message;
    private List<BetModel> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BetModel> getResult() {
        return result;
    }

    public void setResult(List<BetModel> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BetMatchBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
