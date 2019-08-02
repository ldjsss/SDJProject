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
import com.lldj.tc.http.beans.MapBean;
import com.lldj.tc.match.Activity_MainUI;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.lldj.tc.toolslibrary.util.AppUtils.DEBUG;

public class HttpMsg<T>{
    private static final HttpMsg ourInstance = new HttpMsg();
    public static HttpMsg getInstance() {
        return ourInstance;
    }

    public static String baseUrl = "http://192.168.1.118/";
//    public static String baseUrl = "http://server.yjwl.ltd/";

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
                if(DEBUG)System.out.println("ret:" + code + " msg:" + msg);
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
                    ResultsModel ret = res.getResult();
                    SharePreUtils.getInstance().setUserInfo(context, res.getResult());
                }
                else Toast.makeText(context, context.getResources().getString(R.string.getUseInfoFail), Toast.LENGTH_SHORT).show();

                callbackListener.onFinish(msg);
            }
        }));
    }

    /////////////////////////

    public void sendGetGameList(Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl + "game", new HttpMsg().getListener(service, callbackListener), null);
    }

    public void sendGetMatchList(int type, int page_num, String game_ids, Class<T>service, Listener callbackListener) {
        StringBuffer buffer = new StringBuffer(baseUrl);
        buffer.append(String.format("match/type/%s?page_num=%s%s", type, page_num, game_ids));
        HttpTool.sendGet(buffer.toString(), new HttpMsg().getListener(service, callbackListener), null);
    }

    public void sendGetMatchDetial(int matchID, Class<T>service, Listener callbackListener) {
        StringBuffer buffer = new StringBuffer(baseUrl);
        buffer.append(String.format("match/detail/%s", matchID));
        HttpTool.sendGet(buffer.toString(), new HttpMsg().getListener(service, callbackListener), null);
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

    public void sendBankList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(baseUrl + "user/banks", null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetCash(final String access_token, final String amount, final String bind_id, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("amount", amount);
        URLParams.put("bind_id", bind_id);

        HttpTool.httpPost(baseUrl + "user/cash", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendBindCard(final String access_token, final String card, final String bank_id, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("bank_id", bank_id);
        URLParams.put("card", card);

        HttpTool.httpPost(baseUrl + "user/addbank", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendSuportBankList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(baseUrl + "user/sysbanks", null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendTradings(final String access_token, final String type, final String page_num, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("page_num", page_num);
        URLParams.put("type", type);

        HttpTool.httpPost(baseUrl + "user/traderecord", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetName() {
        HttpTool.sendGet(baseUrl + "match/stage", new HttpMsg().getListener(MapBean.class, new HttpMsg.Listener(){
            @Override
            public void onFinish(Object _res) {
                MapBean res = (MapBean) _res;
                if(res.getCode() == GlobalVariable.succ){
                    Map<String, String> result = res.getResult();
                    if(result!= null)SharePreUtils.getInstance().setMapNames(result);
                }
            }
        }), null);
    }

    public void sendChangeBir(final String access_token, final String birthday, Class<T>service, Listener callbackListener) {

        HttpTool.sendGet(baseUrl + "user/modifybirthday?birthday=" + birthday, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendChangeKey(final String access_token, final String new_password, final String old_password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("new_password", new_password);
        URLParams.put("old_password", old_password);

        HttpTool.httpPost(baseUrl + "user/modifypassword", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendChangePhone(final String access_token, final String code, final String newphone, final String password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("code", code);
        URLParams.put("newphone", newphone);
        URLParams.put("password", password);

        HttpTool.httpPost(baseUrl + "user/modifyphone", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetUseCode(final String access_token, final String phone, Class<T>service, final Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("phone", phone);

        HttpTool.httpPost(baseUrl + "user/code", URLParams, new HttpMsg().getListener(service, callbackListener), access_token);

//        HttpTool.sendGet(baseUrl + "register/sms?mobile=" + phone, callbackListener);

    }

    public void sendGetOdds(String game_ids, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(baseUrl + "match/oddssimple?" + game_ids, new HttpMsg().getListener(service, callbackListener), null);
    }

    public interface Listener<T> {
        void onFinish(T msg);
    }

}
