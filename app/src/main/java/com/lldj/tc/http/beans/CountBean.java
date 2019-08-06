package com.lldj.tc.http.beans;

public class CountBean {

    private int code = 0;
    private String message = "";
    private CountMode result;

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

    public CountMode getResult() {
        return result;
    }

    public void setResult(CountMode result) {
        this.result = result;
    }

    public static class CountMode{
        private int early;
        private int live;
        private int today;

        public int getEarly() {
            return early;
        }

        public void setEarly(int early) {
            this.early = early;
        }

        public int getLive() {
            return live;
        }

        public void setLive(int live) {
            this.live = live;
        }

        public int getToday() {
            return today;
        }

        public void setToday(int today) {
            this.today = today;
        }
    }
}
