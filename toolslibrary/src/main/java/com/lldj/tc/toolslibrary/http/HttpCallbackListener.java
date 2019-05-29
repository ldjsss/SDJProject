package com.lldj.tc.toolslibrary.http;

// 定义HttpCallbackListener接口
// 包含两个方法，成功和失败的回调函数定义
public interface HttpCallbackListener {
    void onFinish(int code, String msg);
}