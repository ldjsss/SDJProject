package com.lldj.tc.sharepre;

import android.content.Context;


public class SharePreUtils {
    public static SharePreUtils instance;

    public static synchronized SharePreUtils getInstance() {
        if (null == instance) {
            instance = new SharePreUtils();
        }
        return instance;
    }


    //是否登录 true ,false
    public static boolean getIsLogin(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getBoolean(SharedKeys.IS_LOGIN, false);
    }

    public static void setLoginStatus(Context context, boolean isLogin) {
        SharedPreferencesUtil.setValue(context, SharedKeys.IS_LOGIN, isLogin);
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

    public static void setNickName(Context context, String name) {
        SharedPreferencesUtil.setValue(context, SharedKeys.NICK_NAME, name);
    }

    public static String getNickName(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.NICK_NAME, "");
    }

    public static void setTelNum(Context context, String phone) {
        SharedPreferencesUtil.setValue(context, SharedKeys.PHONE, phone);
    }

    public static String getTelNum(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.PHONE, "");
    }

    public static void setDepartment(Context context, String phone) {
        SharedPreferencesUtil.setValue(context, SharedKeys.DEPARTMENT, phone);
    }

    public static String getDepartment(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.DEPARTMENT, "");
    }

    public static void setJobTitle(Context context, String pjobTItle) {
        SharedPreferencesUtil.setValue(context, SharedKeys.JOB_TITLE, pjobTItle);
    }

    public static String getJobTitle(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.JOB_TITLE, "");
    }

    public static void setVerifyTempName(Context context, String pTempName) {
        SharedPreferencesUtil.setValue(context, SharedKeys.VERIFY_TEMP_NAME, pTempName);
    }

    public static String getVerifyTempName(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.VERIFY_TEMP_NAME, "");
    }
}
