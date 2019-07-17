package com.lldj.tc.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lldj.tc.R;
import com.lldj.tc.http.beans.BaseBean;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;
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

    public static String baseUrl = "http://192.168.1.118/";

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
                System.out.println("ret:" + code + " msg:" + msg);
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

        HttpTool.httpPost(baseUrl + "register/sms", URLParams, new HttpMsg().getListener(service, callbackListener), "");

//        HttpTool.sendGet(baseUrl + "register/sms?mobile=" + phone, callbackListener);

    }

    public void sendLogin(final String username, final String password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);

        HttpTool.httpPost(baseUrl + "login", URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendTokenLogin(final String token, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", token);

        HttpTool.httpPost(baseUrl + "oauth2", URLParams, new HttpMsg().getListener(service, callbackListener), "");
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

        HttpTool.httpPost(baseUrl + "register/submit", URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendForgetKey(final String mobile, final String password, String sms, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", mobile);
        URLParams.put("password", password);
        URLParams.put("sms", sms);

        HttpTool.httpPost(baseUrl + "register/forget", URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendGetUserInfo(Context context, final String access_token, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", access_token);

        HttpTool.sendPost(baseUrl + "user/info", access_token, "", new HttpMsg().getListener(service, new Listener(){
            @Override
            public void onFinish(Object msg) {
                JsonBean res = (JsonBean) msg;
                if (res.getCode() == GlobalVariable.succ) {
                    ResultsModel ret = (ResultsModel) res.getResult();
                    SharePreUtils.getInstance().setUserInfo(context, ret.getOpenid(), ret.getMobile(), ret.getMoney(), ret.getUsername(), ret.getName());
                }
                else Toast.makeText(context, context.getResources().getString(R.string.getUseInfoFail), Toast.LENGTH_SHORT).show();

                callbackListener.onFinish(msg);
            }
        }));
    }

    /////////////////////////

    public void sendGetGameList(Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl + "game", new HttpMsg().getListener(service, callbackListener));
    }

    public void sendGetMatchList(int type, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl + "match/type/" + type, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendGetMatchDetial(int matchID, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl + "match/detail/" + matchID, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendBetList(final String access_token, final String json, Class<T>service, Listener callbackListener){
        HttpTool.sendPost(baseUrl + "bet/submit", access_token, json, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendBetRecords(final String access_token, final String page_num, final String status, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("page_num", page_num);
        URLParams.put("status", status);

        HttpTool.httpPost(baseUrl + "user/betrecord", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public interface Listener<T> {
        void onFinish(T msg);
    }

}
