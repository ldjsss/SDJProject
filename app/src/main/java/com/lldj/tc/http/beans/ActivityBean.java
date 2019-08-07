package com.lldj.tc.http.beans;

import java.util.List;

public class ActivityBean {

    private int code = 0;
    private String message = "";
    private List<ActivityMode> result;

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

    public List<ActivityMode> getResult() {
        return result;
    }

    public void setResult(List<ActivityMode> result) {
        this.result = result;
    }

    public static class ActivityMode{
        private int id;
        private String activity_name;
        private String activity_desc;
        private String activity_begin_time;
        private String activity_end_time;
        private String created_time;
        private String activity_jump;
        private String activity_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getActivity_desc() {
            return activity_desc;
        }

        public void setActivity_desc(String activity_desc) {
            this.activity_desc = activity_desc;
        }

        public String getActivity_begin_time() {
            return activity_begin_time;
        }

        public void setActivity_begin_time(String activity_begin_time) {
            this.activity_begin_time = activity_begin_time;
        }

        public String getActivity_end_time() {
            return activity_end_time;
        }

        public void setActivity_end_time(String activity_end_time) {
            this.activity_end_time = activity_end_time;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getActivity_jump() {
            return activity_jump;
        }

        public void setActivity_jump(String activity_jump) {
            this.activity_jump = activity_jump;
        }

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }
    }
}
