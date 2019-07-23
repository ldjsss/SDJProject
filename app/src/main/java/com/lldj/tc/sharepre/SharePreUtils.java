package com.lldj.tc.sharepre;

import android.content.Context;
import android.text.TextUtils;

import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.util.Calendar;


public class SharePreUtils {
    public static SharePreUtils instance;

    public boolean agent;
    public String birthday;

    public static synchronized SharePreUtils getInstance() {
        if (null == instance) {
            instance = new SharePreUtils();
        }
        return instance;
    }


    //获取用户id
    public static String getUserId(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.UID, "");
    }

    public static void setUserId(Context context, String id) {
        SharedPreferencesUtil.setValue(context, SharedKeys.UID, id);

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
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.MONEY, "");
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

    public static void setToken(Context context, String phone) {
        SharedPreferencesUtil.setValue(context, SharedKeys.TOKEN, phone);
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

    public static void setRecharge_url(Context context, String url) {
        SharedPreferencesUtil.setValue(context, SharedKeys.RECHARURL, url);
    }

    public static String getRecharge_url(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.RECHARURL, "");
    }

    public static void setSelectBank(Context context, int bankid) {
        SharedPreferencesUtil.setValue(context, SharedKeys.BANKID, bankid);
    }

    public static int getSelectBank(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getInt(SharedKeys.BANKID, -1);
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
        setUserId(context, openid);
        setToken(context, token);

        System.out.println("--------token:" + token);

    }

    public static void setUserInfo(Context context, ResultsModel ret) {

        setUserId(context, ret.getOpenid());
        setPhone(context, ret.getMobile());
        setUserName(context, ret.getUsername());
        setMoney(context, ret.getMoney());
        setName(context, ret.getReal_name());
        setRecharge_url(context, ret.getRecharge_url());

        getInstance().agent = ret.isAgent();
        getInstance().birthday = ret.getBirthday();

        if(TextUtils.isEmpty(getInstance().birthday)) getInstance().birthday = Calendar.getInstance().getTimeInMillis() + "";
    }
}
