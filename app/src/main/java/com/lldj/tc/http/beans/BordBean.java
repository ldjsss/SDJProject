package com.lldj.tc.http.beans;

import java.util.List;

public class BordBean {

    private int code = 0;
    private String message = "";
    private List<BordMode> result;

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

    public List<BordMode> getResult() {
        return result;
    }

    public void setResult(List<BordMode> result) {
        this.result = result;
    }

    public static class BordMode{
        private int id;
        private String title;
        private String task_begin_time;
        private String created_time;
        private String body;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTask_begin_time() {
            return task_begin_time;
        }

        public void setTask_begin_time(String task_begin_time) {
            this.task_begin_time = task_begin_time;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
