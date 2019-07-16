package com.lldj.tc.http.beans;

import com.lldj.tc.http.beans.FormatModel.RecordModel;

public class RecordBean {
    private int code = -9999;
    private String message = "";
    private RecordModel result;

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

    public RecordModel getResult() {
        return result;
    }

    public void setResult(RecordModel result) {
        this.result = result;
    }
}
