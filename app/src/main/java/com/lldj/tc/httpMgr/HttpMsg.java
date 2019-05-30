package com.lldj.tc.httpMgr;

import com.google.gson.Gson;
import com.lldj.tc.toolslibrary.http.HttpTool;
import org.json.JSONObject;

public class HttpMsg {
    public static Gson gson = new Gson();

    public static void test(HttpTool.msgListener callbackListener) {
        HttpTool.sendGet("http://www.baidu.com", new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {
                callbackListener.onFinish(code, msg);
            }
        });
    }

    public static void sendLogin(final String count, final String password, HttpTool.msgListener callbackListener) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("count", count);
            body.put("password", password);
        } catch (Exception e) {}

        HttpTool.sendPost("http://www.baidu.com", body, new HttpTool.msgListener(){
            @Override
            public void onFinish(int code, String msg) {

            }
        });
    }
}
