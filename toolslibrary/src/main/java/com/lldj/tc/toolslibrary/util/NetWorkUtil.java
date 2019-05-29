package com.lldj.tc.toolslibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Description: 从asset下读取文本
 * Author: sunbolin
 * Time: 2016/6/17 11:08
 */
public class NetWorkUtil {

    private static final String NET_TYPE_WIFI = "WIFI";
    public static final int NET_NOT_AVAILABLE = 0;
    public static final int NET_WIFI = 1;
    public static final int NET_PROXY = 2;
    public static final int NET_NORMAL = 3;

    /**
     * 网络类型
     */
    private volatile static int networkType = NET_NOT_AVAILABLE;

    /**
     * 获取网络连接类型
     */
    public synchronized static int getNetworkType(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            // 当前网络不可用
            networkType = NET_NOT_AVAILABLE;
        } else {
            // 如果当前是WIFI连接
            if (NET_TYPE_WIFI.equals(networkinfo.getTypeName())) {
                networkType = NET_WIFI;
            }
            // 非WIFI联网
            else {
                String proxyHost = android.net.Proxy.getDefaultHost();
                // 代理模式
                if (proxyHost != null) {
                    networkType = NET_PROXY;
                }
                // 直连模式
                else {
                    networkType = NET_NORMAL;
                }
            }
        }
        return networkType;
    }

    public static boolean networkStatusOK(final Context context) {
        boolean netStatus = false;

        try {
            ConnectivityManager connectManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                    netStatus = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return netStatus;
    }

    /**
     * 网络已经连接，然后去判断是wifi连接还是GPRS连接 设置一些自己的逻辑调用
     */
    public static boolean isWifiNetworkAvailable(Context context) {
        if (networkStatusOK(context)) {
            ConnectivityManager connectManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State gprs = connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            NetworkInfo.State wifi = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING) {
                return false;
            }
            // 判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
                return true;
            }
        } else {
//            setNetwork(context);
        }
        return false;
    }


    /**
     * 网络未连接时，调用设置方法
     */
//    public static void setNetwork(final Context context) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        // builder.setIcon(R.drawable.ic_launcher);
//        builder.setTitle(R.string.str_wang_luo_ti_shi_xin_xi);
//        builder.setMessage(R.string.str_wang_luo_bu_ke_yong_qing_xian_she_zhi_wang_luo);
//        builder.setPositiveButton(context.getString(R.string.setting), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = null;
//                /**
//                 * 判断手机系统的版本！如果API大于10 就是3.0 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
//                 */
//                if (Build.VERSION.SDK_INT > 10) {
//                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
//                } else {
//                    intent = new Intent();
//                    ComponentName component = new ComponentName("com.android.settings",
//                            "com.android.settings.WirelessSettings");
//                    intent.setComponent(component);
//                    intent.setAction("android.intent.action.VIEW");
//                }
//                context.startActivity(intent);
//            }
//        });
//
//        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.create();
//        builder.show();
//    }
}
