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
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.EventType;
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

        HttpTool.httpPost((new StringBuffer(baseUrl).append("register/sms")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendLogin(final String username, final String password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("username", username);
        URLParams.put("password", password);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("login")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendTokenLogin(final String token, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", token);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("oauth2")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), "");
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

        HttpTool.httpPost((new StringBuffer(baseUrl).append("register/submit")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendForgetKey(final String mobile, final String password, String sms, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("mobile", mobile);
        URLParams.put("password", password);
        URLParams.put("sms", sms);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("register/forget")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    public void sendGetUserInfo(Context context, final String access_token, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("access_token", access_token);

        HttpTool.sendPost((new StringBuffer(baseUrl).append("user/info")).toString(), access_token, "", new HttpMsg().getListener(service, new Listener(){
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
        StringBuffer buffer = new StringBuffer(baseUrl);
        buffer.append("game");
        HttpTool.sendGet(buffer.toString(), new HttpMsg().getListener(service, callbackListener), null);
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
        HttpTool.sendPost((new StringBuffer(baseUrl).append("bet/submit")).toString(), access_token, json, new HttpMsg().getListener(service, callbackListener));
    }

    public void sendBetRecords(final String access_token, final String page_num, final String status, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("page_num", page_num);
        URLParams.put("status", status);
        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/betrecord")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendBankList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/banks")).toString(), null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetCash(final String access_token, final String amount, final String bind_id, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("amount", amount);
        URLParams.put("bind_id", bind_id);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/cash")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendBindCard(final String access_token, final String card, final String bank_id, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("bank_id", bank_id);
        URLParams.put("card", card);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/addbank")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendSuportBankList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/sysbanks")).toString(), null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendTradings(final String access_token, final String type, final String page_num, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("page_num", page_num);
        URLParams.put("type", type);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/traderecord")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetName() {
        HttpTool.sendGet((new StringBuffer(baseUrl).append("match/stage")).toString(), new HttpMsg().getListener(MapBean.class, new HttpMsg.Listener(){
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
        HttpTool.sendGet((new StringBuffer(baseUrl).append("user/modifybirthday?birthday=").append(birthday)).toString(), new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendChangeKey(final String access_token, final String new_password, final String old_password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("new_password", new_password);
        URLParams.put("old_password", old_password);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/modifypassword")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendChangePhone(final String access_token, final String code, final String newphone, final String password, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("code", code);
        URLParams.put("newphone", newphone);
        URLParams.put("password", password);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/modifyphone")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetUseCode(final String access_token, final String phone, Class<T>service, final Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("phone", phone);

        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/code")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetOdds(String game_ids, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet((new StringBuffer(baseUrl).append("match/oddssimple?").append(game_ids)).toString(), new HttpMsg().getListener(service, callbackListener), null);
    }

    public void sendGetGamesCount(Class<T>service, Listener callbackListener) {
        HttpTool.sendGet((new StringBuffer(baseUrl).append("game/count")).toString(), new HttpMsg().getListener(service, new HttpMsg.Listener(){
            @Override
            public void onFinish(Object msg) {
                AppUtils.dispatchEvent(new ObData(EventType.MATCHCOUNT, msg));
                if(callbackListener != null) callbackListener.onFinish(msg);
            }
        }), null);
    }

    public void sendTastList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/tasks")).toString(), null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetTask(final String task_id, final String access_token, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("task_id", task_id);
        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/taskreward")).toString(), URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public void sendGetActivity(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost((new StringBuffer(baseUrl).append("user/activitys")).toString(), null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    public interface Listener<T> {
        void onFinish(T msg);
    }

}
