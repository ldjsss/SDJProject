package com.lldj.tc.httpMgr;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.lldj.tc.toolslibrary.http.HttpCallbackListener;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.http.MsgCal;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class HttpMsg {
    public static Map<String, HttpCallbackListener> calMap = new HashMap<String, HttpCallbackListener>();
    public static Gson gson = new Gson();

    public static void handleMsg(Message msg) {
        Bundle b = msg.getData();
        MsgCal jsonM = gson.fromJson(b.getString("httpMsg"), MsgCal.class);
        if(jsonM == null) return;
        String _tag = jsonM.getTag();
        if(_tag == null) return;
        HttpCallbackListener callbackListener = calMap.get(_tag);
        if(callbackListener == null) return;
        callbackListener.onFinish(jsonM.getCode(), jsonM.getMsg());
    }

    public static void test(HttpCallbackListener callbackListener) {
        String tag = "test";
        calMap.put(tag, callbackListener);
        HttpTool.sendGet(tag,"http://www.baidu.com");
    }

    public static void sendLogin(final String count, final String password, HttpCallbackListener callbackListener) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("count", count);
            body.put("password", password);
        } catch (Exception e) {}

        String tag = "sendLogin";
        calMap.put(tag, new HttpCallbackListener() {
            @Override
            public void onFinish(int code, String msg) {
                Log.w("---dddddddddddd--code", code + "");
                callbackListener.onFinish(code, msg);
            }
        });
        HttpTool.sendPost(tag,"http://www.baidu.com", body);
    }
}
