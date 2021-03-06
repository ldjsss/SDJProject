package com.lldj.tc.toolslibrary.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.toolslibrary.BuildConfig;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

public class AppUtils {
    public static boolean DEBUG = false;
    private static final AppUtils ourInstance = new AppUtils();

    public static AppUtils getInstance() {
        return ourInstance;
    }
    private static Dialog loading = null;

    private  static ArrayList<Observer<ObData>> eventList = new ArrayList<>();
    private  static Observable<ObData> observable;

    private AppUtils() { }

    public static void registEvent(Observer<ObData> observer){
        if (observable == null) observable = new Observable<ObData>();

        observable.register(observer);
        eventList.add(observer);
    }

    public static void unregisterEvent(Observer<ObData> observer){
        if(observer == null) return;
        if (observable == null) observable = new Observable<ObData>();
        observable.unregister(observer);
        for (int i = 0; i < eventList.size(); i++) {
            if (observer == eventList.get(i)){
                eventList.remove(i);
                break;
            }
        }
    }

    public static void dispatchEvent(ObData data){
        if (data == null) return;
        if (observable == null) observable = new Observable<ObData>();
        try {
            observable.notifyObserver(data.clone());
        }catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {

        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                }
            }

            tabLayout.requestLayout();
        }
    }

    public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) (getDisplayMetrics(context).density * leftDip);
        int right = (int) (getDisplayMetrics(context).density * rightDip);

        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

//    public static void screenAdapterStart(Context content){
//        ScreenAdapterTools.init(content);
//    }
//
//    public static void screenAdapterRest(Context content){
//        ScreenAdapterTools.getInstance().reset(content);
//    }
//
//    public static void screenAdapterLoadView(ViewGroup rootView){
//        ScreenAdapterTools.getInstance().loadView(rootView);
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getFromAssets(Context ct, String fileName) {
        String Result = "";

        try {
            BufferedReader bufReader;
            String line;
            try (InputStreamReader inputReader = new InputStreamReader(
                    ct.getResources().getAssets().open(fileName))) {
                bufReader = new BufferedReader(inputReader);
            }
            line = "";

            while ((line = bufReader.readLine()) != null) {
                Result += line;
                Result += "\r\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 判断字符串是否为空，null、""、"null"，都为空
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return (TextUtils.isEmpty(str) || "null".equals(str));
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    //dip和px之间的转换
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的Metrics，进而可以获取高度、宽度、密度
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.heightPixels;
    }


    // 获取资源字符串
    public static String getString(Context context, int strId) {
        return context.getResources().getString(strId);
    }


    public static String encodeURL(String url) {
        String encodeURL = null;
        try {
            encodeURL = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeURL;
    }

    public static String decodeURL(String url) {
        String decodeURL = null;
        try {
            decodeURL = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeURL;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
		总结起来就是第一位必定为1，第二位必定为3-9，其他位置的可以为0-9
		*/
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[3-9]"代表第二位的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    // 拨打电话
    public static void callPhone(Context context, String phonenumber) {
        if (isExistsSIMCard(context)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:" + phonenumber));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "请插入SIM卡", Toast.LENGTH_LONG).show();
        }
    }

    // 判断是否存在SIM卡
    public static boolean isExistsSIMCard(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (manager.getSimState() == TelephonyManager.SIM_STATE_READY) return true;
        return false;
    }

    /**
     * 判断当前的的网络是否可用
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] networkInfos = null;
        try {
            networkInfos = connectivityManager.getAllNetworkInfo();
        } catch (Throwable t) {

        }
        if (networkInfos == null) {
            return false;
        }
        for (NetworkInfo networkInfo : networkInfos) {
            try {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            } catch (Throwable t) {

            }
        }
        return false;
    }

    /**
     * 跳转进入网络设置界面
     *
     * @param ctx 跳转起始位置
     * @author lilw
     * @CrateTime
     */
    public static void intoNetworkSetting(Context ctx) {
        ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    //获取float两位小数
    public static String getFloatStr(float val) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(val);
    }

    /**
     * bitmap转数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 将字符串中的标点符号过滤掉
     *
     * @param str
     * @return
     */

    public static String trimPunctuation(String str) {
        String punct[] = {",", ".", "!", "?", ";", ":", "，", "。", "！", "？",
                "；", "：", "、", "[", "]"};
        List<String> punctList = Arrays.asList(punct); // 将String数组转List集合
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if (punctList.contains(c.toString())) {

            } else {
                result.append(str.charAt(i));
            }
        }

        return result.toString();
    }

    // 取得屏幕分辨率
    //    public static Point getScreenResolution(Context context) {
    //        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    //        Display display = wm.getDefaultDisplay();
    //        return new Point(display.get(), display.getHeight());
    ////    }

    //获取设备分辨率
    public static String getResolution(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return height + "x" + width;
    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取设备名称 手机型号
     *
     * @return
     */
    public static String getDeviceName() {
        //        return android.os.Build.MANUFACTURER + Build.BRAND  + Build.MODEL;
        return Build.MODEL;
    }

    //    public static String getDeviceId(Context context) {
    //        //        return android.os.Build.MANUFACTURER + Build.BRAND  + Build.MODEL;
    //        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
    //        String szImei = TelephonyMgr.getDeviceId();
    //        return szImei;
    //    }

    /**
     * 获取操作系统版本
     */

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 版本名称
     *
     * @param context
     * @return 版本的名称 如：1.0.1；
     * @throws PackageManager.NameNotFoundException 没有写版本名称时报错
     * @author lilw
     * @CrateTime
     */
    public static String getVersionName(Context context) {
        String version = "0";
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * 应用名字
     *
     * @param context
     */
    public static String getAppName(Context context) {
        return context.getApplicationInfo()
                .loadLabel(context.getPackageManager()).toString();
    }

    public static void setShadow(Activity context, float shadow) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = shadow;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    //    public static void copyToClipboard(Context context, String copy) {
    //        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    //        cmb.setText(copy);
    //    }


    /**
     * 判断应用是否已经启动
     *
     * @param
     * @return boolean
     * * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     */
    //    public static boolean isAppAlive(Context context, String packageName) {
    //        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    //        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);
    //
    //        //判断程序是否在栈顶
    //        if (list.get(0).topActivity.getPackageName().equals(packageName)) {
    //            return true;
    //        } else {
    ////            判断程序是否在栈里
    //            for (ActivityManager.RunningTaskInfo info : list) {
    //                if (info.topActivity.getPackageName().equals(packageName)) {
    //                    return false;
    //                }
    //            }//栈里找不到，返回3
    //            return false;
    //        }
    //    }
    public static boolean checkPasswordRegex(String password) {
        if (password == null || password.isEmpty() || password.length() == 0) return false;

        String regex = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * @param time 传入毫秒，如果是1437538047，即10位，则应该乘以1000
     * @return
     */
    public static String getFormatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    public static String getFormatTime1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    public static String getFormatTime2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    public static String getFormatTime3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    public static String getFormatTime4(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    public static String getFormatTime5(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    public static String getFormatTime6(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if ((time + "").length() == 10) time *= 1000;
        return sdf.format(new Date(time));
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(long s) {
        return new Date(s);
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);

        return date.getTime();
    }

    /* //日期转换为时间戳 */
    public static long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    /**
     * 将时间戳转换成当天零点的时间戳
     *
     * @param milliseconds
     * @return
     */
    public static Calendar zeroFromHour(long milliseconds) {
        Calendar calendar = Calendar.getInstance(); // 获得一个日历

        calendar.setTimeInMillis(completMilliseconds(milliseconds));
        zeroFromHour(calendar);
        return calendar;
    }
    /**
     * 将时，分，秒，以及毫秒值设置为0
     *
     * @param calendar
     */
    public static void zeroFromHour(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    /**
     * 由于服务器返回的是10位，手机端使用需要补全3位
     *
     * @param milliseconds
     * @return
     */
    public static long completMilliseconds(long milliseconds) {
        String milStr = Long.toString(milliseconds);
        if (milStr.length() == 10) {
            milliseconds = milliseconds * 1000;
        }
        return milliseconds;
    }


    /**
     * 最终调用方法
     * @param timeStamp
     * @return
     */

    public static String getWhatDay (long timeStamp) {
        Calendar cal = zeroFromHour(timeStamp);
        String whatDay="";
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
            whatDay="星期六";
        }
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            whatDay="星期日";
        }
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            whatDay = "星期一";
        }
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
            whatDay = "星期二";
        }
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
            whatDay = "星期三";
        }
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
            whatDay = "星期四";
        }
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
            whatDay = "星期五";
        }
        return whatDay;
    }



    /*
    //写一个md5加密的方法
     */
    public static String getMD5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code.toUpperCase();
    }


    public static String getDatePoor(Date startDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        return (hour < 10 ? "0" + hour : hour + "")+ " : "
                + (min < 10 ? "0" + min : min + "")+ " : "
                + (sec < 10 ? "0" + sec : sec);
    }

//    //todo 获得状态栏的高度
//    public int getStatusBarHeight(Context context) {
//        Class<?> c = null;
//        Object obj = null;
//        Field field = null;
//        int x = 0, statusBarHeight = 0;
//        try {
//            c = Class.forName("com.android.internal.R$dimen");
//            obj = c.newInstance();
//            field = c.getField("status_bar_height");
//            x = Integer.parseInt(field.get(obj).toString());
//            statusBarHeight = context.getResources().getDimensionPixelSize(x);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        return statusBarHeight;
//    }

    // 获取渠道号
    public static String getChannel(Context context){
        String channel = "";
        try{
            ApplicationInfo info = context.getPackageManager().
                    getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if(info != null && info.metaData != null){
                String metaData = info.metaData.getString("CP_CHANNEL");
                if(!metaData.isEmpty()){
                    channel = metaData;
                }
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        Log.e("app channel", "当前渠道为："+ channel);
        return channel;
    }

    /**
     * 弹出loading动画
     */
    public static void showLoading(Activity activity) {
        if(AppUtils.loading != null) return;
        if (!activity.isFinishing())//xActivity即为本界面的Activity
        {
            return;
        }

        Dialog _dia = DialogUtils.createLoadingDialog(activity, "loading", false);
        if(_dia == null) return;
        _dia.show();;
        AppUtils.loading = _dia;
    }
    public static void hideLoading() {
        if(AppUtils.loading == null) return;

        AppUtils.loading.dismiss();
        AppUtils.loading = null;

    }

    public static void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    public static boolean isNumer(String str) {
        if(TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * ========================================================
     *         设置EditText的hint字体的大小
     * ========================================================
     */
    public static void  setEditTextHintSize(EditText editText, String hintText, int size){
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size,true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    public static void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

}
