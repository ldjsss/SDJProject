package com.lldj.tc.httpMgr;

import android.os.Bundle;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.httpMgr.beans.FormatModel.JsonBean;
import com.lldj.tc.httpMgr.beans.FormatModel.Results;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.mainUtil.GlobalVariable;

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

    public static HttpTool.msgListener getListener(Listener listener){
        return new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {

                if(code == HttpURLConnection.HTTP_OK) {
//                    String mjson = "{\"code\":200,\"message\":\"\",\"result\":[{\"id\":136,\"name\":\"DOTA2\",\"short_name\":\"Dota 2\",\"logo\":\"https://www.nmgdjkj.com//file/60d062143c4cc70ac9e36e3e61c372f1.svg\",\"early\":0,\"live\":0,\"today\":9},{\"id\":137,\"name\":\"CSGO\",\"short_name\":\"CS:GO\",\"logo\":\"https://www.nmgdjkj.com//file/e6edb8165fc906c02ed6dc74dafb1702.svg\",\"early\":15,\"live\":0,\"today\":7},{\"id\":138,\"name\":\"英雄联盟\",\"short_name\":\"LOL\",\"logo\":\"https://www.nmgdjkj.com//file/4a920eed6bc2bef7862e0a51fe469ff4.svg\",\"early\":51,\"live\":0,\"today\":7},{\"id\":139,\"name\":\"王者荣耀\",\"short_name\":\"KOG\",\"logo\":\"https://www.nmgdjkj.com//file/5155ce2645f2486533bd28f9e9c2026e.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":140,\"name\":\"篮球\",\"short_name\":\"Basketball\",\"logo\":\"https://www.nmgdjkj.com//file/f299629fdf01bfb98c7b2686700c9cd7.svg\",\"early\":1,\"live\":0,\"today\":2},{\"id\":141,\"name\":\"足球\",\"short_name\":\"Soccer\",\"logo\":\"https://www.nmgdjkj.com//file/896b863382a913294251a6daadcaaab9.svg\",\"early\":1,\"live\":0,\"today\":1},{\"id\":142,\"name\":\"穿越火线\",\"short_name\":\"CF\",\"logo\":\"https://www.nmgdjkj.com//file/fafb12fece0c833ed3761948333d3a2a.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":143,\"name\":\"守望先锋\",\"short_name\":\"Overwatch\",\"logo\":\"https://www.nmgdjkj.com//file/c30a43b86ebd99d76c3652234befce15.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":144,\"name\":\"星际争霸II\",\"short_name\":\"StarCraft2\",\"logo\":\"https://www.nmgdjkj.com//file/f0abdb1c6d408973875bf17f8015b335.svg\",\"early\":2,\"live\":0,\"today\":3},{\"id\":145,\"name\":\"火箭联盟\",\"short_name\":\"Rocket League\",\"logo\":\"https://www.nmgdjkj.com//file/adb213d45ff762a6c9d9d5bb5bd7be4b.svg\",\"early\":0,\"live\":0,\"today\":3},{\"id\":146,\"name\":\"彩虹六号\",\"short_name\":\"Rainbow 6\",\"logo\":\"https://www.nmgdjkj.com//file/8561a708439fcee7e401900170a7f539.svg\",\"early\":13,\"live\":0,\"today\":1},{\"id\":147,\"name\":\"炉石传说\",\"short_name\":\"Hearthstone\",\"logo\":\"https://www.nmgdjkj.com//file/4fbea59631b613e64626584e0284190a.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":148,\"name\":\"NBA2K\",\"short_name\":\"NBA2K\",\"logo\":\"https://www.nmgdjkj.com//file/231c6a2bf90dc1fad0fc427aeb33eafd.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":149,\"name\":\"FIFA\",\"short_name\":\"FIFA\",\"logo\":\"https://www.nmgdjkj.com//file/bc89fb93ee7b515a276595f9fa211759.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":150,\"name\":\"魔兽争霸3\",\"short_name\":\"WOW3\",\"logo\":\"https://www.nmgdjkj.com//file/6760b72f17ddb2801f99d3467a489ae8.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":151,\"name\":\"石器牌\",\"short_name\":\"Artifact\",\"logo\":\"https://www.nmgdjkj.com//file/2d2f91777b0e1d1224f074d5f987325f.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":152,\"name\":\"使命召唤\",\"short_name\":\"COD\",\"logo\":\"https://www.nmgdjkj.com//file/829a498b4f4abcc0163c5d2f1837f8d9.svg\",\"early\":0,\"live\":0,\"today\":2},{\"id\":153,\"name\":\"绝地求生\",\"short_name\":\"PUBG\",\"logo\":\"https://www.nmgdjkj.com//file/7184f7a8546e31e6b50f227964d6b4b9.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":154,\"name\":\"风暴英雄\",\"short_name\":\"Heroes of the Storm\",\"logo\":\"https://www.nmgdjkj.com//file/91a465ae105a46344319500344757fa6.svg\",\"early\":0,\"live\":0,\"today\":1},{\"id\":155,\"name\":\"皇室战争\",\"short_name\":\"Clash Royale\",\"logo\":\"https://www.nmgdjkj.com//file/2561b5f63f87a9ed5d67313ab443e373.png\",\"early\":0,\"live\":0,\"today\":1},{\"id\":156,\"name\":\"蒸汽动力\",\"short_name\":\"Paladins\",\"logo\":\"https://www.nmgdjkj.com//file/09bfc32004e15578eaad365d6c722edf.png\",\"early\":0,\"live\":0,\"today\":1}]}";
                    int _code = -1000;
                    String _msg = "";
                    int intIndex = msg.indexOf("\"result\":[{");
                    if(intIndex == - 1){
                        JsonBean<Results> jsonBean = new Gson().fromJson(msg, new TypeToken<JsonBean<Results>>() {}.getType());
//                        JsonBean jsonBean = new Gson().fromJson(msg, JsonBean.class);
                        listener.onFinish(jsonBean);
                        _code = jsonBean.getCode();
                        _msg = jsonBean.getMessage();
                    }else{
                        Type jsonType = new TypeToken<JsonBean<List<Results>>>() {}.getType();
                        JsonBean<List<Results>>  jsonBean =  new Gson().fromJson(msg, jsonType);

                        listener.onFinish(jsonBean);
                        _code = jsonBean.getCode();
                        _msg = jsonBean.getMessage();
                    }


                    if(_code != GlobalVariable.succ){
                        toastMess("ERROR CODE " + _code + _msg);
                    }

                }else{
                    toastMess("NET ERROR CODE" + code + msg);
                }
                AppUtils.getInstance().hideLoading();
            }
        };
    }

//    public static HttpTool.msgListener getListener(Listener listener){
//        return new HttpTool.msgListener(){
//            @Override
//            public void onFinish(int code, String msg) {
//
//                if(code == HttpURLConnection.HTTP_OK) {
//                    Gson gson = new Gson();
////                    JsonBean jsonBean = gson.fromJson(msg, JsonBean.class);  //把JSON数据转化为对象
//
//                    JsonBean<Results> jsonBean = new Gson().fromJson(msg, new TypeToken<JsonBean<Results>>() {}.getType());
//                    if(jsonBean.getCode() != GlobalVariable.succ){
//                        toastMess("ERROR CODE " + jsonBean.getCode() + jsonBean.getMessage());
//                    }
//                    listener.onFinish(jsonBean);
//                }else{
//                    toastMess("NET ERROR CODE" + code + msg);
//                }
//                AppUtils.getInstance().hideLoading();
//            }
//        };
//    }

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

    public static void sendGetGameList(Listener callbackListener) {
        HttpTool.sendGet(baseUrl3 + "game", getListener(callbackListener));
    }

    public static void sendGetMatchList(int type, Listener callbackListener) {
        HttpTool.sendGet(baseUrl3 + "match/type/" + type, getListener(callbackListener));
    }

    public interface Listener {
        void onFinish(JsonBean msg);
    }

//    public interface Listener2 {
//        void onFinish(JsonBeans msg);
//    }
}
