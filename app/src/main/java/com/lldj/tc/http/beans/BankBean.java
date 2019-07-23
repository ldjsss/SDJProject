package com.lldj.tc.http.beans;

import androidx.annotation.DrawableRes;

import java.util.List;

public class BankBean {

    private int code = -9999;
    private String message = "";
    private List<BankModel> result;

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

    public List<BankModel> getResult() {
        return result;
    }

    public void setResult(List<BankModel> result) {
        this.result = result;
    }


    public static class BankModel {

        private String card;
        private String bank_name;
        private String create_time;
        private int id = -11111;
        private int uid;
        private int logo;
        private int bank_id;
        private int status;

        public BankModel() { }

        public BankModel(String card_name, int logo) {
            this.bank_name = bank_name;
            this.logo = logo;
        }

        public BankModel(String bank_name, int logo, String card) {
            this.bank_name = bank_name;
            this.logo = logo;
            this.card = card;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public int getBank_id() {
            return bank_id;
        }

        public void setBank_id(int bank_id) {
            this.bank_id = bank_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
