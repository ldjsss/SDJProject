package com.lldj.tc.sharepre;

import android.content.Context;
import android.text.TextUtils;

import com.lldj.tc.http.beans.BordBean;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.UrlBean;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SharePreUtils {
    public static SharePreUtils instance;

    private boolean agent;
    private String birthday;
    private String about_url;
    private String agent_url;
    private String qrcode_url;
    private String recharge_url;
    private String rule_url;
    private String userId;
    private List<BordBean.BordMode> bordlist;
    private Map<String, String> mapNames = new HashMap<>();

    private Map<Integer,ResultsModel > Gamelist;

    public static synchronized SharePreUtils getInstance() {
        if (null == instance) {
            instance = new SharePreUtils();
        }
        return instance;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getMapNames() {
        return mapNames;
    }

    public void setMapNames(Map<String, String> mapNames) {
        this.mapNames = mapNames;
    }

    public static void setUserName(Context context, String name) {
        SharedPreferencesUtil.setValue(context, SharedKeys.UNAME, name);
    }

    public static String getUserName(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.UNAME, "");
    }

    public static void setPassWord(Context context, String password) {
        SharedPreferencesUtil.setValue(context, SharedKeys.PASSWORD, password);
    }

    public static String getPassword(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.PASSWORD, "");
    }

    public static void setName(Context context, String name) {
        SharedPreferencesUtil.setValue(context, SharedKeys.NICK_NAME, name);
    }

    public static String getName(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.NICK_NAME, "");
    }

    public static void setMoney(Context context, String money) {
        SharedPreferencesUtil.setValue(context, SharedKeys.MONEY, money);
    }

    public static String getMoney(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.MONEY, "0");
    }

    public static void setPhone(Context context, String phone) {
        SharedPreferencesUtil.setValue(context, SharedKeys.PHONE, phone);
    }

    public static String getPhone(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.PHONE, "");
    }

    public static void setChannel(Context context, String channel) {
        SharedPreferencesUtil.setValue(context, SharedKeys.CHANNEL, channel);
    }

    public static String getChannel(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.CHANNEL, "");
    }

    public static void setToken(Context context, String token) {
        SharedPreferencesUtil.setValue(context, SharedKeys.TOKEN, token);
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.TOKEN, "");
    }

    public static void setDevices(Context context, String pjobTItle) {
        SharedPreferencesUtil.setValue(context, SharedKeys.DEVICES, pjobTItle);
    }

    public static String getDevices(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.DEVICES, "");
    }

    public static void setIP(Context context, String pTempName) {
        SharedPreferencesUtil.setValue(context, SharedKeys.IP, pTempName);
    }

    public static String getIP(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.IP, "");
    }

    public static void setSelectGame(Context context, String select) {
        SharedPreferencesUtil.setValue(context, SharedKeys.SELECTGAME, select);
    }

    public static String getSelectGame(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.SELECTGAME, "");
    }

    public static void setSelectBank(Context context, int bankid) {
        SharedPreferencesUtil.setValue(context, SharedKeys.BANKID, bankid);
    }

    public static int getSelectBank(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getInt(SharedKeys.BANKID, -1);
    }

    public boolean isAgent() {
        return agent;
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRecharge_url() {
        return recharge_url;
    }

    public void setRecharge_url(String recharge_url) {
        this.recharge_url = recharge_url;
    }

    public static void setInstance(SharePreUtils instance) {
        SharePreUtils.instance = instance;
    }

    public String getAbout_url() {
        return about_url;
    }

    public void setAbout_url(String about_url) {
        this.about_url = about_url;
    }

    public String getAgent_url() {
        return agent_url;
    }

    public void setAgent_url(String agent_url) {
        this.agent_url = agent_url;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public String getRule_url() {
        return rule_url;
    }

    public void setRule_url(String rule_url) {
        this.rule_url = rule_url;
    }

    public Map<Integer,ResultsModel > getGamelist() {
        return Gamelist;
    }

    public void setGamelist(Map<Integer,ResultsModel > gamelist) {
        Gamelist = gamelist;
    }

    public List<BordBean.BordMode> getBordlist() {
        return bordlist;
    }

    public void setBordlist(List<BordBean.BordMode> bordlist) {
        this.bordlist = bordlist;
    }

    public static void setRegistInfo(Context context, final String username, final String password, String name, String mobile, String channel, String devices) {
        setUserName(context, username);
        setPassWord(context, password);
        setName(context, name);
        setPhone(context, mobile);
        setChannel(context, channel);
        setDevices(context, devices);
    }

    public static void setLoginInfo(Context context, final String token, final String expires_in, final String openid) {
        getInstance().userId = openid;
        setToken(context, token);

//        System.out.println("--------token:" + token);

    }

    public static void setUserInfo(Context context, ResultsModel ret) {

        setPhone(context, ret.getMobile());
        setUserName(context, ret.getUsername());
        setMoney(context, ret.getMoney());
        setName(context, ret.getReal_name());

        getInstance().userId = ret.getOpenid();
        getInstance().agent = ret.isAgent();
        getInstance().birthday = ret.getBirthday();

        if(TextUtils.isEmpty(getInstance().birthday)) getInstance().birthday = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
    }

    public static void setUrlInfo(UrlBean.urlModel ret) {
        getInstance().about_url    = ret.getAbout_url();
        getInstance().agent_url    = ret.getAgent_url();
        getInstance().qrcode_url   = ret.getQrcode_url();
        getInstance().rule_url     = ret.getRule_url();
        getInstance().recharge_url = ret.getRecharge_url();

    }
}
