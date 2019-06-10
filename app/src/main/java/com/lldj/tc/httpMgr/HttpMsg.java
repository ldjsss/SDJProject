package com.lldj.tc.httpMgr;

import android.os.Bundle;
import android.os.Message;

import com.google.gson.Gson;
import com.lldj.tc.R;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.httpMgr.beans.test.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.util.AppURLCode;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpMsg {
    public static String baseUrl = "http://192.168.1.116:9001/";
    public static String baseUrl2 = "http://192.168.1.116:9002/";

    private static void toastMess(String msg){
        Message message=new Message();
        Bundle bundle=new Bundle();
        bundle.putString("msg", msg);
        message.setData(bundle);
        message.what=HandlerType.SHOWTOAST;
        HandlerInter.getInstance().sendMessage(message);
    }

    public static HttpTool.msgListener getListener(Listener listener){
        return new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {

                if(code == HttpURLConnection.HTTP_OK) {
                    Gson gson = new Gson();
                    JsonBean jsonBean = gson.fromJson(msg, JsonBean.class);  //把JSON数据转化为对象
                    if(jsonBean.getCode() != AppURLCode.succ){
                        toastMess("ERROR CODE " + jsonBean.getCode() + jsonBean.getMessage());
                    }
                    listener.onFinish(jsonBean);
                }else{
                    toastMess("NET ERROR CODE" + code + msg);
                }
                AppUtils.hideLoading();
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

    public static void sendLogin(final String username, final String password, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);

        HttpTool.httpPost(baseUrl2 + "login", URLParams, getListener(callbackListener));
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

    public interface Listener {
        void onFinish(JsonBean msg);
    }
}
