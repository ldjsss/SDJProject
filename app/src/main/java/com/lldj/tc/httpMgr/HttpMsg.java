package com.lldj.tc.httpMgr;

import android.os.Bundle;
import android.os.Message;

import com.google.gson.Gson;
import com.lldj.tc.httpMgr.beans.BaseBean;
import com.lldj.tc.mainUtil.GlobalVariable;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpMsg<T>{
    private static final HttpMsg ourInstance = new HttpMsg();
    public static HttpMsg getInstance() {
        return ourInstance;
    }

    public static String baseUrl = "http://192.168.1.118:9001/";
    public static String baseUrl2 = "http://192.168.1.118:9002/";
    public static String baseUrl3 = "http://192.168.1.118:9004/";
    public static String baseUrl4 = "http://192.168.1.118:9000/";

    private static void toastMess(String msg){
        if(msg == null) msg = "";
        Message message=new Message();
        Bundle bundle=new Bundle();
        bundle.putString("msg", msg);
        message.setData(bundle);
        message.what=HandlerType.SHOWTOAST;
        HandlerInter.getInstance().sendMessage(message);
    }

    public HttpTool.msgListener getListener(Class<T>service, Listener listener){
        return new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {
//                System.out.println("ret:" + code + " msg:" + msg);
                if(code == HttpURLConnection.HTTP_OK) {
                    Object data = new Gson().fromJson(msg, service);
                    listener.onFinish(data);

                    BaseBean _data = new Gson().fromJson(msg, BaseBean.class);
                    int _code = _data.getCode();
                    String _msg  = _data.getMessage();

                    if(_code != GlobalVariable.succ){ toastMess("ERROR CODE " + _code + _msg); }

                }else{
                    toastMess("NET ERROR CODE" + code + msg);
                }
                AppUtils.getInstance().hideLoading();
            }
        };
    }

    public void sendGetCode(final String phone, Class<T>service, final Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", phone);

        HttpTool.httpPost(baseUrl + "register/sms", URLParams, new HttpMsg().getListener(service, callbackListener));

//        HttpTool.sendGet(baseUrl + "register/sms?mobile=" + phone, callbackListener);

    }

    public void sendLogin(final String username, final String password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);

        HttpTool.httpPost(baseUrl2 + "login", URLParams, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendTokenLogin(final String token, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", token);

        HttpTool.httpPost(baseUrl2 + "oauth2", URLParams, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendRegister(final String username, final String password, String name, String mobile, String sms, String channel, String devices, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);
        URLParams.put("name", name);
        URLParams.put("mobile", mobile);
        URLParams.put("sms", sms);
        URLParams.put("channel", channel);
        URLParams.put("devices", devices);

        HttpTool.httpPost(baseUrl + "register/submit", URLParams, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendForgetKey(final String mobile, final String password, String sms, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", mobile);
        URLParams.put("password", password);
        URLParams.put("sms", sms);

        HttpTool.httpPost(baseUrl + "register/forget", URLParams, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendGetUserInfo(final String access_token, final String openid, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", access_token);
        URLParams.put("openid", openid);

        HttpTool.httpPost(baseUrl2 + "user/info", URLParams, new HttpMsg().getListener(service, callbackListener));
    }

    /////////////////////////

    public void sendGetGameList(Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl3 + "game", new HttpMsg().getListener(service, callbackListener));
    }

    public void sendGetMatchList(int type, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl3 + "match/type/" + type, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendGetMatchDetial(int matchID, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl3 + "match/detail/" + matchID, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendBetList(String access_token, String json, Class<T>service, Listener callbackListener){
        HttpTool.sendPost(baseUrl4 + "bet/submit", access_token, json, new HttpMsg().getListener(service, callbackListener));
    }

    public interface Listener<T> {
        void onFinish(T msg);
    }

}
