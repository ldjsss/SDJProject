package com.lldj.tc.bean;

import java.util.List;

public class TestBean {


    /**
     * title : 推荐项目
     * type : 1
     * data : [{"id":1,"type":1,"name":"北京国际简化不孕治疗和配子及胚胎冷冻专题讨论会","imageUrl":"https://f.cangg.cn:82/data/201812211640179166.jpg","creditType":"国家I类","credit":"2","livePlayUrl":null,"starLevel":4.1,"peopleNum":1741,"place":null,"time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100001"},{"id":2,"type":1,"name":"医学蛋白质组学理论及相关实验讲习班","imageUrl":"https://f.cangg.cn:82/data/201812211637597494.jpg","creditType":"国家I类","credit":"3","livePlayUrl":null,"starLevel":4.6,"peopleNum":1493,"place":null,"time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100002"},{"id":12,"type":1,"name":"门脉高压性出血的内镜下治疗","imageUrl":"https://f.cangg.cn:82/data/201812211642274101.jpg","creditType":"国家I类","credit":"5","livePlayUrl":null,"starLevel":4.1,"peopleNum":1390,"place":"北京","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100004"}]
     * total : 13
     * jumpUrl : null
     * time : null
     * introduce : null
     */

    private String title;
    private int type;
    private int total;
    private Object jumpUrl;
    private Object time;
    private Object introduce;
    private List<DataBean> data;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(Object jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getIntroduce() {
        return introduce;
    }

    public void setIntroduce(Object introduce) {
        this.introduce = introduce;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * type : 1
         * name : 北京国际简化不孕治疗和配子及胚胎冷冻专题讨论会
         * imageUrl : https://f.cangg.cn:82/data/201812211640179166.jpg
         * creditType : 国家I类
         * credit : 2
         * livePlayUrl : null
         * starLevel : 4.1
         * peopleNum : 1741
         * place : null
         * time : 2018-12-11 12:23
         * introduce : 简介
         * jumpUrl : 100001
         */

        private int id;
        private int type;
        private String name;
        private String imageUrl;
        private String creditType;
        private String credit;
        private Object livePlayUrl;
        private double starLevel;
        private int peopleNum;
        private Object place;
        private String time;
        private String introduce;
        private String jumpUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getCreditType() {
            return creditType;
        }

        public void setCreditType(String creditType) {
            this.creditType = creditType;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public Object getLivePlayUrl() {
            return livePlayUrl;
        }

        public void setLivePlayUrl(Object livePlayUrl) {
            this.livePlayUrl = livePlayUrl;
        }

        public double getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(double starLevel) {
            this.starLevel = starLevel;
        }

        public int getPeopleNum() {
            return peopleNum;
        }

        public void setPeopleNum(int peopleNum) {
            this.peopleNum = peopleNum;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
