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
        private int status;
        private String created_time;
        private String task_begin_time;
        private String task_desc;
        private String task_end_time;
        private String task_name;
        private String task_reward_money;
        private String task_set_num;
        private int task_type;
        private String user_set_num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getTask_begin_time() {
            return task_begin_time;
        }

        public void setTask_begin_time(String task_begin_time) {
            this.task_begin_time = task_begin_time;
        }

        public String getTask_desc() {
            return task_desc;
        }

        public void setTask_desc(String task_desc) {
            this.task_desc = task_desc;
        }

        public String getTask_end_time() {
            return task_end_time;
        }

        public void setTask_end_time(String task_end_time) {
            this.task_end_time = task_end_time;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public String getTask_reward_money() {
            return task_reward_money;
        }

        public void setTask_reward_money(String task_reward_money) {
            this.task_reward_money = task_reward_money;
        }

        public String getTask_set_num() {
            return task_set_num;
        }

        public void setTask_set_num(String task_set_num) {
            this.task_set_num = task_set_num;
        }

        public int getTask_type() {
            return task_type;
        }

        public void setTask_type(int task_type) {
            this.task_type = task_type;
        }

        public String getUser_set_num() {
            return user_set_num;
        }

        public void setUser_set_num(String user_set_num) {
            this.user_set_num = user_set_num;
        }
    }
}
