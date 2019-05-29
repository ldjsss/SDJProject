package com.lldj.tc.toolslibrary.Sharepre;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lldj.tc.toolslibrary.util.Clog;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtil {

    private static final String SHARED_NAME = "zhiyi";

    public static SharedPreferences getSharedPreferences(Context context) {
//        return (context == null ? MedicalApplication.getContext() : context).getSharedPreferences(SHARED_NAME,
//                Context.MODE_PRIVATE);
        return  context.getSharedPreferences(SHARED_NAME,
                Context.MODE_PRIVATE);
    }

    public static String getStringValue(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public static String getStringValue(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static int getIntValue(Context context, String key) {
        return getSharedPreferences(context).getInt(key, -1);
    }


    public static int getIntValue(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    protected static float getFloatValue(Context context, String key) {
        return getSharedPreferences(context).getFloat(key, -1);
    }

    public static float getFloatValue(Context context, String key, float defValue) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static boolean getBooleanValue(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public static boolean getBooleanValue(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static long getLongValue(Context context, String key) {
        return getSharedPreferences(context).getLong(key, (long) -1);
    }

    public static long getLongValue(Context context, String key, long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static void setValue(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    public static void setValue(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    public static void setValue(Context context, String key, float value) {
        getSharedPreferences(context).edit().putFloat(key, value).commit();
    }

    public static void setValue(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static void setValue(Context context, String key, long value) {
        getSharedPreferences(context).edit().putLong(key, value).commit();
    }


    public static void removeKeyArray(Context context, String... keys) {
        try {
            Editor edit = getSharedPreferences(context).edit();
            for (String key : keys) {
                edit.remove(key);
            }
            edit.commit();
        } catch (Exception e) {
            Clog.e("removeKeyArray e[" + e + "]", "removeKeyArray e[" + e + "]");
        }
    }

    public static void clearSharePreferences(Context context) {
        Editor edit = getSharedPreferences(context).edit();
        edit.clear();
        edit.commit();
    }


    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setDataList(Context context, String tag, List<T> datalist) {
        Editor editor = getSharedPreferences(context).edit();
        if (null == datalist || datalist.size() <= 0){
            editor.putString(tag, "");
            editor.commit();
            return;
        }


        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(Context context, String tag) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getSharedPreferences(context).getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }
    public static  ArrayList<String> getDataStringList(Context context, String tag) {
        ArrayList<String> datalist = new ArrayList<String>();
        String strJson = getSharedPreferences(context).getString(tag, null);
        if (TextUtils.isEmpty(strJson)) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        return datalist;

    }

}
