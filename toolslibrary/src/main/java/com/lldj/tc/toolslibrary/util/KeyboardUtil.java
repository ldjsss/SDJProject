package com.lldj.tc.toolslibrary.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by WANG on 2016/4/6.软键盘的控制
 */
public class KeyboardUtil {

    /**
     * 隐藏输入法键盘
     *
     * @param context
     * @param editText
     */
    public static void hideInput(Context context, EditText editText) {
        if (context != null && editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入法键盘
     *
     * @param context
     * @param editText
     */
    public static void showInput(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static boolean isSoftInputShow(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(IBinder token, Activity activity) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //如果输入法在窗口上已经显示，则隐藏，反之则显示
    public static void showOrHide(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //view为接受软键盘输入的视图，SHOW_FORCED表示强制显示
    public static void showOrHide(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //  imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);//SHOW_FORCED表示强制显示
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    //调用隐藏系统默认的输入法
    public static void showOrHide(Context context, Activity activity) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //获取输入法打开的状态
    public static boolean isShowing(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//isOpen若返回true，则表示输入法打开
    }
}
