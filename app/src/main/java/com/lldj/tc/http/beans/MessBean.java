package com.lldj.tc.http.beans;

import java.util.List;

public class MessBean {

    private int code = 0;
    private String message = "";
    private List<MessMode> result;

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

    public List<MessMode> getResult() {
        return result;
    }

    public void setResult(List<MessMode> result) {
        this.result = result;
    }

    public static class MessMode{
        private long time;
        private String title;
        private String content;

        public MessMode(String title, String content, long time) {
            this.title = title;
            this.content = content;
            this.time = time;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
