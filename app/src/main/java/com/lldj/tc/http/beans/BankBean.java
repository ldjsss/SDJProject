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
        private String card_name;
        private String create_time;
        private int id = -11111;
        private int uid;
        private int logo;

        public BankModel() { }

        public BankModel(String card_name, int logo) {
            this.card_name = card_name;
            this.logo = logo;
        }

        public BankModel(String card_name, int logo, String card) {
            this.card_name = card_name;
            this.logo = logo;
            this.card = card;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getCard_name() {
            return card_name;
        }

        public void setCard_name(String card_name) {
            this.card_name = card_name;
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
    }
}
