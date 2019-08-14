package com.lldj.tc.http.beans;

public class UrlBean {
    private int code = -9999;
    private String message = "";
    private urlModel result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public urlModel getResult() {
        return result;
    }
    public void setResults(urlModel result) {
        this.result = result;
    }


    public class urlModel{
        private String about_url;
        private String agent_url;
        private String qrcode_url;
        private String recharge_url;
        private String rule_url;

        public String getAbout_url() {
            return about_url;
        }

        public void setAbout_url(String about_url) {
            this.about_url = about_url;
        }

        public String getAgent_url() {
            return agent_url;
        }

        public void setAgent_url(String agent_url) {
            this.agent_url = agent_url;
        }

        public String getQrcode_url() {
            return qrcode_url;
        }

        public void setQrcode_url(String qrcode_url) {
            this.qrcode_url = qrcode_url;
        }

        public String getRecharge_url() {
            return recharge_url;
        }

        public void setRecharge_url(String recharge_url) {
            this.recharge_url = recharge_url;
        }

        public String getRule_url() {
            return rule_url;
        }

        public void setRule_url(String rule_url) {
            this.rule_url = rule_url;
        }
    }
}


