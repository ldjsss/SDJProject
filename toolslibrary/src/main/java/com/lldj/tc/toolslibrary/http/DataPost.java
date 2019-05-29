package com.lldj.tc.toolslibrary.http;

import java.io.Serializable;

//实现Serializable接口，使dataPost可序列化。
public class DataPost implements Serializable {

    /*指定序列化版本号，保证序列化版本的一致性。在服务器端，JVM会把传来的字节流的
    serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如果相同就认
    为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。*/
    private static final long serialVersionUID = 1L;

//    String name;
//    String password;
//    public dataPost(String name, String password) {
//        this.name = name;
//        this.password = password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

}