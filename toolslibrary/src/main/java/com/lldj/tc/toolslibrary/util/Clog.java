package com.lldj.tc.toolslibrary.util;

import android.util.Log;

import com.lldj.tc.toolslibrary.BuildConfig;

/**
 * Description: 打印log的类
 */
public class Clog {
    /**
     * 调试颜色为黑色的，任何消息都会输出，这里的v代表verbose啰嗦的意思，平时使用就是Log.v("","");
     *
     * @param pTag 打印信息的标签
     * @param pMsg 需要打印的信息
     */
    public static void v(String pTag, String pMsg) {
        if (BuildConfig.DEBUG) {
            Log.v(pTag+"", pMsg+"");
        }
    }

    /**
     * Log.d的输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息，过滤起来可以通过DDMS的Logcat标签来选择.
     *
     * @param pTag
     * @param pMsg
     */
    public static void d(String pTag, String pMsg) {
        if (BuildConfig.DEBUG) {
            Log.d(pTag+"", pMsg+"");
        }
    }

    /**
     * Log.i的输出为绿色，一般提示性的消息information，它不会输出Log.v和Log.d的信息，但会显示i、w和e的信息
     *
     * @param pTag
     * @param pMsg
     */
    public static void i(String pTag, String pMsg) {
        if (BuildConfig.DEBUG) {
            Log.i(pTag+"", pMsg+"");
        }
    }

    /**
     * Log.w的意思为橙色，可以看作为warning警告，一般需要我们注意优化Android代码，同时选择它后还会输出Log.e的信息。
     *
     * @param pTag
     * @param pMsg
     */
    public static void w(String pTag, String pMsg) {
        if (BuildConfig.DEBUG) {
            Log.w(pTag+"", pMsg+"");
        }
    }

    /**
     * Log.e为红色，可以想到error错误，这里仅显示红色的错误信息，这些错误就需要我们认真的分析，查看栈的信息了。
     *
     * @param pTag
     * @param pMsg
     */
    public static void e(String pTag, String pMsg) {
        if (BuildConfig.DEBUG) {
            Log.e(pTag+"", pMsg+"");
        }
    }

    public static void e(Class T, String pTag, String pMsg) {
        if (BuildConfig.DEBUG) {
            Log.e(T.getName() + "---" + pTag, pMsg+"");
        }
    }
}
