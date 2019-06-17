package com.lldj.tc.httpMgr;

import android.os.Bundle;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.httpMgr.beans.FormatModel.JsonBean;
import com.lldj.tc.httpMgr.beans.FormatModel.JsonBeans;
import com.lldj.tc.httpMgr.beans.FormatModel.Results;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.util.AppURLCode;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpMsg {
    public static String baseUrl = "http://192.168.1.116:9001/";
    public static String baseUrl2 = "http://192.168.1.116:9002/";
    public static String baseUrl3 = "http://192.168.1.116:9004/";

    private static void toastMess(String msg){
        if(msg == null) msg = "";
        Message message=new Message();
        Bundle bundle=new Bundle();
        bundle.putString("msg", msg);
        message.setData(bundle);
        message.what=HandlerType.SHOWTOAST;
        HandlerInter.getInstance().sendMessage(message);
    }

    public static HttpTool.msgListener getListener2(Listener2 listener){
        return new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {

                if(code == HttpURLConnection.HTTP_OK) {
                    Gson gson = new Gson();
                    JsonBeans jsonBean = gson.fromJson(msg, JsonBeans.class);

//                    JsonBean<List<Results>> jsonBean = new Gson().fromJson(msg, new TypeToken<JsonBean<Results>>() {}.getType());//new Gson().fromJson(msg, jsonType);
//                    JsonBean<Results> jsonBean = new Gson().fromJson(msg, new TypeToken<Results>() {}.getType());
                    if(jsonBean.getCode() != AppURLCode.succ){
                        toastMess("ERROR CODE " + jsonBean.getCode() + jsonBean.getMessage());
                    }
                    listener.onFinish(jsonBean);

                }else{
                    toastMess("NET ERROR CODE" + code + msg);
                }
                AppUtils.getInstance().hideLoading();
            }
        };
    }

    public static HttpTool.msgListener getListener(Listener listener){
        return new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {

                if(code == HttpURLConnection.HTTP_OK) {
                    Gson gson = new Gson();
//                    JsonBean jsonBean = gson.fromJson(msg, JsonBean.class);  //把JSON数据转化为对象

                    JsonBean<Results> jsonBean = new Gson().fromJson(msg, new TypeToken<JsonBean<Results>>() {}.getType());
                    if(jsonBean.getCode() != AppURLCode.succ){
                        toastMess("ERROR CODE " + jsonBean.getCode() + jsonBean.getMessage());
                    }
                    listener.onFinish(jsonBean);
                }else{
                    toastMess("NET ERROR CODE" + code + msg);
                }
                AppUtils.getInstance().hideLoading();
            }
        };
    }

    public static void test(Listener callbackListener) {
        HttpTool.sendGet("http://www.baidu.com", getListener(callbackListener));
    }

    public static void sendGetCode(final String phone, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", phone);

        HttpTool.httpPost(baseUrl + "register/sms", URLParams, getListener(callbackListener));

//        HttpTool.sendGet(baseUrl + "register/sms?mobile=" + phone, callbackListener);

    }

    public static void sendLogin(final String username, final String password, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);

        HttpTool.httpPost(baseUrl2 + "login", URLParams, getListener(callbackListener));
    }

    public static void sendTokenLogin(final String token, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", token);

        HttpTool.httpPost(baseUrl2 + "oauth2", URLParams, getListener(callbackListener));
    }

    public static void sendRegister(final String username, final String password, String name, String mobile, String sms, String channel, String devices, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);
        URLParams.put("name", name);
        URLParams.put("mobile", mobile);
        URLParams.put("sms", sms);
        URLParams.put("channel", channel);
        URLParams.put("devices", devices);

        HttpTool.httpPost(baseUrl + "register/submit", URLParams, getListener(callbackListener));
    }

    public static void sendForgetKey(final String mobile, final String password, String sms, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", mobile);
        URLParams.put("password", password);
        URLParams.put("sms", sms);

        HttpTool.httpPost(baseUrl + "register/forget", URLParams, getListener(callbackListener));
    }

    public static void sendGetUserInfo(final String access_token, final String openid, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", access_token);
        URLParams.put("openid", openid);

        HttpTool.httpPost(baseUrl2 + "user/info", URLParams, getListener(callbackListener));
    }

    /////////////////////////

    public static void sendGetGameList(Listener2 callbackListener) {
        HttpTool.sendGet(baseUrl3 + "game", getListener2(callbackListener));
    }

    public static void sendGetMatchList(int type, Listener2 callbackListener) {
        HttpTool.sendGet(baseUrl3 + "match/type/" + type, getListener2(callbackListener));
    }

    public interface Listener {
        void onFinish(JsonBean msg);
    }

    public interface Listener2 {
        void onFinish(JsonBeans msg);
    }
}
