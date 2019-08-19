package com.lldj.tc.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lldj.tc.R;
import com.lldj.tc.http.beans.BaseBean;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.http.beans.MapBean;
import com.lldj.tc.http.beans.UrlBean;
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
    public static HttpMsg getInstance() { return ourInstance; }
    private static Map<String,String> sParams = new HashMap();

    public static final String baseUrl = "http://192.168.1.118/";

//      private static final String baseUrl = "http://server.yjwl.ltd/";

//    public static final StringBuilder baseUrl = new StringBuilder("http://123.56.4.224/");


    private static void toastMess(String msg, int code){
        if(msg == null) msg = "";
        Message message=new Message();
        Bundle bundle=new Bundle();
        bundle.putString("msg", msg);
        bundle.putInt("code", code);
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

//                    if(_code != GlobalVariable.succ){ toastMess("ERROR CODE " + _code + _msg, _code); }
                    if(_code != GlobalVariable.succ){ toastMess(_msg, _code); }

                }else{
                    toastMess("NET ERROR CODE" + code + msg, code);
                }
                AppUtils.getInstance().hideLoading();
            }
        };
    }

    private static final String sgetCodeURL = new StringBuilder(baseUrl).append("register/sms").toString();
    public void sendGetCode(final String phone, Class<T>service, final Listener callbackListener) {
        sParams.clear();
        sParams.put("mobile", phone);

        HttpTool.httpPost(sgetCodeURL, sParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    private static final String loginURL = new StringBuilder(baseUrl).append("login").toString();
    public void sendLogin(final String username, final String password, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("username", username);
        sParams.put("password", password);

        HttpTool.httpPost(loginURL, sParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    private static final String tokenURL = new StringBuilder(baseUrl).append("oauth2").toString();
    public void sendTokenLogin(final String token, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("access_token", token);

        HttpTool.httpPost(tokenURL, sParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    private static final String registerURL = new StringBuilder(baseUrl).append("register/submit").toString();
    public void sendRegister(final String username, final String password, String name, String mobile, String sms, String channel, String devices, String package_info, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("username", username);
        sParams.put("password", password);
        sParams.put("name", name);
        sParams.put("mobile", mobile);
        sParams.put("sms", sms);
        sParams.put("channel", channel);
        sParams.put("devices", devices);
        sParams.put("package_info", package_info);

        HttpTool.httpPost(registerURL, sParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    private static final String forgetkeyURL = new StringBuilder(baseUrl).append("register/forget").toString();
    public void sendForgetKey(final String mobile, final String password, String sms, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("mobile", mobile);
        sParams.put("password", password);
        sParams.put("sms", sms);

        HttpTool.httpPost(forgetkeyURL, sParams, new HttpMsg().getListener(service, callbackListener), "");
    }

    private static final String usetInfoURL = new StringBuilder(baseUrl).append("user/info").toString();
    public void sendGetUserInfo(Context context, final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.sendPost(usetInfoURL, access_token, "", new HttpMsg().getListener(service, new Listener(){
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

    private static final String gameListURL = new StringBuilder(baseUrl).append("game").toString();
    public void sendGetGameList(Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(gameListURL, new HttpMsg().getListener(service, callbackListener), null);
    }

    private static final String matchListURL = new StringBuilder(baseUrl).append("match/type/%d?page_num=%d%s").toString();
    public void sendGetMatchList(int type, int page_num, String game_ids, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(String.format(matchListURL, type, page_num, game_ids), new HttpMsg().getListener(service, callbackListener), null);
    }

    private static final String matchDetailURL = new StringBuilder(baseUrl).append("match/detail/%s").toString();
    public void sendGetMatchDetial(int matchID, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(String.format(matchDetailURL, matchID), new HttpMsg().getListener(service, callbackListener), null);
    }

    private static final String betListURL = new StringBuilder(baseUrl).append("bet/submit").toString();
    public void sendBetList(final String access_token, final String json, Class<T>service, Listener callbackListener){
        HttpTool.sendPost(betListURL, access_token, json, new HttpMsg().getListener(service, callbackListener));
    }

    private static final String betRecordsURL = new StringBuilder(baseUrl).append("user/betrecord").toString();
    public void sendBetRecords(final String access_token, final String page_num, final String status, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("page_num", page_num);
        sParams.put("status", status);
        HttpTool.httpPost(betRecordsURL, sParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String bankListURL = new StringBuilder(baseUrl).append("user/banks").toString();
    public void sendBankList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(bankListURL, null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String cashURL = new StringBuilder(baseUrl).append("user/cash").toString();
    public void sendGetCash(final String access_token, final String amount, final String bind_id, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("amount", amount);
        sParams.put("bind_id", bind_id);

        HttpTool.httpPost(cashURL, sParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String bindCardURL = new StringBuilder(baseUrl).append("user/addbank").toString();
    public void sendBindCard(final String access_token, final String card, final String bank_id, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("bank_id", bank_id);
        URLParams.put("card", card);

        HttpTool.httpPost(bindCardURL, URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String sbankListURL = new StringBuilder(baseUrl).append("user/sysbanks").toString();
    public void sendSuportBankList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(sbankListURL, null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String tradingsURL = new StringBuilder(baseUrl).append("user/traderecord").toString();
    public void sendTradings(final String access_token, final String type, final String page_num, Class<T>service, Listener callbackListener) {
        Map<String,String> URLParams = new HashMap();
        URLParams.put("page_num", page_num);
        URLParams.put("type", type);

        HttpTool.httpPost(tradingsURL, URLParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String namesURL = new StringBuilder(baseUrl).append("match/stage").toString();
    public void sendGetName() {
        HttpTool.sendGet(namesURL, new HttpMsg().getListener(MapBean.class, new HttpMsg.Listener(){
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

    private static final String changeBirURL = new StringBuilder(baseUrl).append("user/modifybirthday?birthday=%s").toString();
    public void sendChangeBir(final String access_token, final String birthday, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(String.format(changeBirURL, birthday), new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String changeKeyURL = new StringBuilder(baseUrl).append("user/modifypassword").toString();
    public void sendChangeKey(final String access_token, final String new_password, final String old_password, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("new_password", new_password);
        sParams.put("old_password", old_password);

        HttpTool.httpPost(changeKeyURL, sParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String changePhoneURL = new StringBuilder(baseUrl).append("user/modifyphone").toString();
    public void sendChangePhone(final String access_token, final String code, final String newphone, final String password, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("code", code);
        sParams.put("newphone", newphone);
        sParams.put("password", password);

        HttpTool.httpPost(changePhoneURL, sParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String getUserCodeURL = new StringBuilder(baseUrl).append("user/code").toString();
    public void sendGetUseCode(final String access_token, final String phone, Class<T>service, final Listener callbackListener) {
        sParams.clear();
        sParams.put("phone", phone);

        HttpTool.httpPost(getUserCodeURL, sParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String getOddsURL = new StringBuilder(baseUrl).append("match/oddssimple?%s").toString();
    public void sendGetOdds(String game_ids, Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(String.format(getOddsURL, game_ids), new HttpMsg().getListener(service, callbackListener), null);
    }

    private static final String getGameCountURL = new StringBuilder(baseUrl).append("game/count").toString();
    public void sendGetGamesCount(Class<T>service, Listener callbackListener) {
        HttpTool.sendGet(getGameCountURL, new HttpMsg().getListener(service, new HttpMsg.Listener(){
            @Override
            public void onFinish(Object msg) {
                AppUtils.dispatchEvent(new ObData(EventType.MATCHCOUNT, msg));
                if(callbackListener != null) callbackListener.onFinish(msg);
            }
        }),null);
    }

    private static final String taskListURL = new StringBuilder(baseUrl).append("user/tasks").toString();
    public void sendTastList(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(taskListURL, null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String getTaskURL = new StringBuilder(baseUrl).append("user/taskreward").toString();
    public void sendGetTask(final String task_id, final String access_token, Class<T>service, Listener callbackListener) {
        sParams.clear();
        sParams.put("task_id", task_id);
        HttpTool.httpPost(getTaskURL, sParams, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String getActivityURL = new StringBuilder(baseUrl).append("user/activitys").toString();
    public void sendGetActivity(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(getActivityURL, null, new HttpMsg().getListener(service, callbackListener), access_token);
    }

    private static final String getUrlURL = new StringBuilder(baseUrl).append("user/urls").toString();
    public void sendGetUrl(final String access_token, Class<T>service, Listener callbackListener) {
        HttpTool.httpPost(getUrlURL, null, new HttpMsg().getListener(service, new HttpMsg.Listener(){
            @Override
            public void onFinish(Object msg) {
                UrlBean res = (UrlBean) msg;
                if(res.getCode() == GlobalVariable.succ){
                    SharePreUtils.getInstance().setUrlInfo(res.getResult());
                }
            }
        }), access_token);
    }

    public interface Listener<T> {
        void onFinish(T msg);
    }

}
