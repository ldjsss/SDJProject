package com.lldj.tc.http.beans;

import com.lldj.tc.http.beans.FormatModel.ResultsModel;

import java.util.List;

public class OddsBean{
    private int code = -9999;
    private String message = "";
    private List<simpleOdd> result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<simpleOdd> getResult() {
        return result;
    }

    public void setResult(List<simpleOdd> result) {
        this.result = result;
    }

    public class simpleOdd {
        private int id;
        private String odds;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOdds() {
            return odds;
        }

        public void setOdds(String odds) {
            this.odds = odds;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}


