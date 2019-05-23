package com.lldj.tc.toolslibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 屏幕工具类
 *
 * @author dada
 */
public class ScreenUtil {
    public static ScreenUtil instance;

    public static ScreenUtil getInstance() {
        if (null == instance) {
            synchronized (ScreenUtil.class) {
                if (null == instance) {
                    instance = new ScreenUtil();
                }
            }
        }
        return instance;
    }

    private static int WIDTH = 0;

    //获取屏幕的宽
    public static int getScreenWidth(Context context) {
        if (WIDTH == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            WIDTH = dm.widthPixels;
        }
        return WIDTH;

    }

    //获取屏的高
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    //获取屏幕宽高的最大值
    public int getScreenMAX(Context pContext) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return Math.max(dm.widthPixels, dm.heightPixels);
    }

    //获取屏幕宽高的最小值
    public int getScreenMIN(Context pContext) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return Math.min(dm.widthPixels, dm.heightPixels);
    }

    //获取屏幕的分辨率
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }


    public static float getScreenDensityDpi(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }


    //dp转px
    public static int dipTopx(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    //dp转px
    public static int dipTopx(Context context, int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, context.getResources()
                .getDisplayMetrics());
    }

    //根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断当前屏幕是否为横屏
     */
    public boolean isScreenLandscape(Activity activity) {
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return true;
        } else if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            return false;
        }
        return false;
    }

    /**
     * 设置屏幕为横屏
     */
    public void setScreenLandscap(Activity activity) {
        if (!isScreenLandscape(activity))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     */
    public void setScreenPortrait(Activity activity) {
        if (isScreenLandscape(activity))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    //todo 获得状态栏的高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    //获取底部状态栏的高度
    public int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);
        int contentHeight = getScreenHeight(context);
        return totalHeight - contentHeight;
    }

    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    //todo 获取当前屏幕截图，包含状态栏
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }


    //todo 获取当前屏幕截图，不包含状态栏
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }


    /**
     * 设置RelativeLayout中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setRelativeLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public void setRelativeLayoutParamsMATCH_PARENT(View view) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(lp);
    }


    /**
     * 设置FrameLayout中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setFrameLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置ViewGroup中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public void setViewGroupParams(View view, int width, int height) {
//        if (width == 0 || height == 0) return;

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

//    public static void setRecyleviewParams(View view, int width, int height) {
//        if (width == 0 || height == 0) return;
//
//        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
//        lp.width = width;
//        lp.height = height;
//        view.setLayoutParams(lp);
//    }

    /**
     * 设置LinearLayout中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setLinearLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

//    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//    }
}
