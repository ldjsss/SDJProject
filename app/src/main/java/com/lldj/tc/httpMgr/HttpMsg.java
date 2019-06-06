package com.lldj.tc.httpMgr;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lldj.tc.mine.info.InfoBean;
import com.lldj.tc.retrofit_services.UserServices;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.retrofit.BaseEntity;
import com.lldj.tc.toolslibrary.retrofit.BaseObserver;
import com.lldj.tc.toolslibrary.retrofit.RetrofitUtils;
import com.lldj.tc.toolslibrary.retrofit.RxSchedulerHepler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpMsg {
    public static Gson gson = new Gson();
    public static String baseUrl = "http://192.168.1.109:9001/";

    public static void test(HttpTool.msgListener callbackListener) {
        HttpTool.sendGet("http://www.baidu.com", new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {
                callbackListener.onFinish(code, msg);
            }
        });
    }

    public static void sendGetCode(final String phone, HttpTool.msgListener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", phone);

        HttpTool.http(baseUrl + "register/sms", URLParams, callbackListener);

//        HttpTool.sendGet(baseUrl + "register/sms?mobile=" + phone, callbackListener);

//        UserServices mservices = RetrofitUtils.INSTANCE.getSpecialClient(baseUrl, UserServices.class);
//        UserServices mservices = RetrofitUtils.INSTANCE.getClient(UserServices.class);
//        mservices.getPhoneCode(phone)
//                .compose(RxSchedulerHepler.<BaseEntity<InfoBean>>io_main())
//                .subscribe(new BaseObserver<InfoBean>() {
//                    @Override
//                    protected void onSuccess(InfoBean infoTotalBean) {
//                        Log.w("-----ssssss", "sssssss");
//                    }
//
//                    @Override
//                    protected void onFail(int code, String msg) {
//                        Log.w("-----eeeee", "sssssss");
//
//                    }
//                });
    }

    public static void sendLogin(final String count, final String password, HttpTool.msgListener callbackListener) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("count", count);
            body.put("password", password);
        } catch (Exception e) {}

        HttpTool.sendPost("http://www.baidu.com", String.valueOf(body), new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {
                callbackListener.onFinish(code, msg);
            }
        });
    }

    public static void sendRegister(final String username, final String password, String name, String mobile, String sms, String channel, String devices, HttpTool.msgListener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);
        URLParams.put("name", name);
        URLParams.put("mobile", mobile);
        URLParams.put("sms", sms);
        URLParams.put("channel", channel);
        URLParams.put("devices", devices);

        HttpTool.http(baseUrl + "register/submit", URLParams, callbackListener);
    }

    public static void sendForgetKey(final String phone, final String password, String phoneKey, HttpTool.msgListener callbackListener) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("phone", phone);
            body.put("password", password);
            body.put("phoneKey", phoneKey);
        } catch (Exception e) {}

        HttpTool.sendPost("http://www.baidu.com", String.valueOf(body), new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {
                callbackListener.onFinish(code, msg);
            }
        });
    }
}
