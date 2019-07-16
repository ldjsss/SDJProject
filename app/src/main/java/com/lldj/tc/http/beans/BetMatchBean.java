package com.lldj.tc.http.beans;

import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;

import java.util.List;

public class BetMatchBean {
    private int code;
    private String message;
    private List<betResult> result;

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

    public List<betResult> getResult() {
        return result;
    }

    public void setResult(List<betResult> result) {
        this.result = result;
    }

    public class betResult{
        private int bet_id;
        private int code;
        private int odds_id;
        private BetModel record;

        public int getBet_id() {
            return bet_id;
        }

        public void setBet_id(int bet_id) {
            this.bet_id = bet_id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getOdds_id() {
            return odds_id;
        }

        public void setOdds_id(int odds_id) {
            this.odds_id = odds_id;
        }

        public BetModel getRecord() {
            return record;
        }

        public void setRecord(BetModel record) {
            this.record = record;
        }
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
