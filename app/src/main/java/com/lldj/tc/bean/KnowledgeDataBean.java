package com.lldj.tc.bean;

import java.util.List;

public class KnowledgeDataBean {


    /**
     * msg : success
     * code : 200
     * data : [{"title":"推荐项目","type":1,"data":[{"id":1,"type":1,"name":"北京国际简化不孕治疗和配子及胚胎冷冻专题讨论会","imageUrl":"https://f.cangg.cn:82/data/201812211640179166.jpg","creditType":"国家I类","credit":"2","livePlayUrl":null,"starLevel":4.1,"peopleNum":1741,"place":null,"time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100001"},{"id":2,"type":1,"name":"医学蛋白质组学理论及相关实验讲习班","imageUrl":"https://f.cangg.cn:82/data/201812211637597494.jpg","creditType":"国家I类","credit":"3","livePlayUrl":null,"starLevel":4.6,"peopleNum":1493,"place":null,"time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100002"},{"id":12,"type":1,"name":"门脉高压性出血的内镜下治疗","imageUrl":"https://f.cangg.cn:82/data/201812211642274101.jpg","creditType":"国家I类","credit":"5","livePlayUrl":null,"starLevel":4.1,"peopleNum":1390,"place":"北京","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100004"}],"total":13,"jumpUrl":null,"time":null,"introduce":null},{"title":"线下项目","type":2,"data":[{"id":3,"type":2,"name":"干细胞理论与实验技术","imageUrl":"https://f.cangg.cn:82/data/201812211638493908.jpg","creditType":"国家I类","credit":"4","livePlayUrl":null,"starLevel":3.3,"peopleNum":1022,"place":"北京","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100003"},{"id":4,"type":2,"name":"\u201c十五\u201d国家规划人体解剖学教材应用培训班","imageUrl":"https://f.cangg.cn:82/data/201812211640319004.jpg","creditType":"国家I类","credit":"6","livePlayUrl":null,"starLevel":3,"peopleNum":1253,"place":"上海","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100004"},{"id":5,"type":2,"name":"全国呼吸内科主任高级研修班","imageUrl":"https://f.cangg.cn:82/data/201812211640179166.jpg","creditType":"国家I类","credit":"6","livePlayUrl":null,"starLevel":3.5,"peopleNum":1810,"place":"天津","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100001"}],"total":12,"jumpUrl":null,"time":null,"introduce":null},{"title":"直播项目","type":3,"data":[{"id":6,"type":3,"name":"脑卒中及颅脑损伤康复治疗学习班","imageUrl":"https://f.cangg.cn:82/data/201812211640319004.jpg","creditType":"国家I类","credit":"4","livePlayUrl":null,"starLevel":4.6,"peopleNum":1538,"place":"北京","time":"2018-12-11 12:23","introduce":null,"jumpUrl":"100002"},{"id":7,"type":3,"name":"世界神经外科学会联盟  神经外科技术讲习班 （修）","imageUrl":"https://f.cangg.cn:82/data/201812211637597494.jpg","creditType":"国家I类","credit":"5","livePlayUrl":null,"starLevel":3.9,"peopleNum":1146,"place":"上海","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100003"},{"id":8,"type":3,"name":"胃肠超声新技术学习班","imageUrl":"https://f.cangg.cn:82/data/201812211639028324.jpg","creditType":"国家I类","credit":"6","livePlayUrl":null,"starLevel":4.9,"peopleNum":1326,"place":"天津","time":"2018-12-11 12:23","introduce":"简介简介","jumpUrl":"100004"}],"total":12,"jumpUrl":null,"time":null,"introduce":null},{"title":"推荐项目","type":4,"data":[{"id":9,"type":4,"name":"现代流行病学进展和新发传染病控制对策","imageUrl":"https://f.cangg.cn:82/data/201812211638359418.jpg","creditType":"国家I类","credit":"3","livePlayUrl":null,"starLevel":4.5,"peopleNum":1829,"place":"北京","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100001"},{"id":10,"type":4,"name":"性病艾滋病临床诊治进展学习班","imageUrl":"https://f.cangg.cn:82/data/201812211637597494.jpg","creditType":"国家I类","credit":"5","livePlayUrl":null,"starLevel":4.4,"peopleNum":1802,"place":"上海","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100002"},{"id":11,"type":4,"name":"护理管理的发展及趋势","imageUrl":"https://f.cangg.cn:82/data/201812211640556223.jpg","creditType":"国家I类","credit":"2","livePlayUrl":null,"starLevel":3.8,"peopleNum":1866,"place":"天津","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100003"}],"total":12,"jumpUrl":null,"time":null,"introduce":null}]
     */

    private String msg;
    private int code;
    private List<TestBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TestBean> getData() {
        return data;
    }

    public void setData(List<TestBean> data) {
        this.data = data;
    }

//    public static class DataBeanX {
//        /**
//         * title : 推荐项目
//         * type : 1
//         * data : [{"id":1,"type":1,"name":"北京国际简化不孕治疗和配子及胚胎冷冻专题讨论会","imageUrl":"https://f.cangg.cn:82/data/201812211640179166.jpg","creditType":"国家I类","credit":"2","livePlayUrl":null,"starLevel":4.1,"peopleNum":1741,"place":null,"time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100001"},{"id":2,"type":1,"name":"医学蛋白质组学理论及相关实验讲习班","imageUrl":"https://f.cangg.cn:82/data/201812211637597494.jpg","creditType":"国家I类","credit":"3","livePlayUrl":null,"starLevel":4.6,"peopleNum":1493,"place":null,"time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100002"},{"id":12,"type":1,"name":"门脉高压性出血的内镜下治疗","imageUrl":"https://f.cangg.cn:82/data/201812211642274101.jpg","creditType":"国家I类","credit":"5","livePlayUrl":null,"starLevel":4.1,"peopleNum":1390,"place":"北京","time":"2018-12-11 12:23","introduce":"简介","jumpUrl":"100004"}]
//         * total : 13
//         * jumpUrl : null
//         * time : null
//         * introduce : null
//         */
//
//        private String title;
//        private int type;
//        private int total;
//        private Object jumpUrl;
//        private Object time;
//        private Object introduce;
//        private List<DataBean> data;
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public int getTotal() {
//            return total;
//        }
//
//        public void setTotal(int total) {
//            this.total = total;
//        }
//
//        public Object getJumpUrl() {
//            return jumpUrl;
//        }
//
//        public void setJumpUrl(Object jumpUrl) {
//            this.jumpUrl = jumpUrl;
//        }
//
//        public Object getTime() {
//            return time;
//        }
//
//        public void setTime(Object time) {
//            this.time = time;
//        }
//
//        public Object getIntroduce() {
//            return introduce;
//        }
//
//        public void setIntroduce(Object introduce) {
//            this.introduce = introduce;
//        }
//
//        public List<DataBean> getData() {
//            return data;
//        }
//
//        public void setData(List<DataBean> data) {
//            this.data = data;
//        }
//
//        public static class DataBean {
//            /**
//             * id : 1
//             * type : 1
//             * name : 北京国际简化不孕治疗和配子及胚胎冷冻专题讨论会
//             * imageUrl : https://f.cangg.cn:82/data/201812211640179166.jpg
//             * creditType : 国家I类
//             * credit : 2
//             * livePlayUrl : null
//             * starLevel : 4.1
//             * peopleNum : 1741
//             * place : null
//             * time : 2018-12-11 12:23
//             * introduce : 简介
//             * jumpUrl : 100001
//             */
//
//            private int id;
//            private int type;
//            private String name;
//            private String imageUrl;
//            private String creditType;
//            private String credit;
//            private Object livePlayUrl;
//            private double starLevel;
//            private int peopleNum;
//            private Object place;
//            private String time;
//            private String introduce;
//            private String jumpUrl;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getImageUrl() {
//                return imageUrl;
//            }
//
//            public void setImageUrl(String imageUrl) {
//                this.imageUrl = imageUrl;
//            }
//
//            public String getCreditType() {
//                return creditType;
//            }
//
//            public void setCreditType(String creditType) {
//                this.creditType = creditType;
//            }
//
//            public String getCredit() {
//                return credit;
//            }
//
//            public void setCredit(String credit) {
//                this.credit = credit;
//            }
//
//            public Object getLivePlayUrl() {
//                return livePlayUrl;
//            }
//
//            public void setLivePlayUrl(Object livePlayUrl) {
//                this.livePlayUrl = livePlayUrl;
//            }
//
//            public double getStarLevel() {
//                return starLevel;
//            }
//
//            public void setStarLevel(double starLevel) {
//                this.starLevel = starLevel;
//            }
//
//            public int getPeopleNum() {
//                return peopleNum;
//            }
//
//            public void setPeopleNum(int peopleNum) {
//                this.peopleNum = peopleNum;
//            }
//
//            public Object getPlace() {
//                return place;
//            }
//
//            public void setPlace(Object place) {
//                this.place = place;
//            }
//
//            public String getTime() {
//                return time;
//            }
//
//            public void setTime(String time) {
//                this.time = time;
//            }
//
//            public String getIntroduce() {
//                return introduce;
//            }
//
//            public void setIntroduce(String introduce) {
//                this.introduce = introduce;
//            }
//
//            public String getJumpUrl() {
//                return jumpUrl;
//            }
//
//            public void setJumpUrl(String jumpUrl) {
//                this.jumpUrl = jumpUrl;
//            }
//        }
//    }
}
