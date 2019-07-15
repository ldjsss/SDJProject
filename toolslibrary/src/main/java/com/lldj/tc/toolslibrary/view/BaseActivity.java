package com.lldj.tc.toolslibrary.view;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lldj.tc.toolslibrary.BuildConfig;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.util.ArrayList;


public abstract class BaseActivity extends FragmentActivity{
    protected boolean isPause;
    protected HandlerInter mHandler;


    //资源
    protected Resources mResources;
    public    BaseActivity mContext;
    private   ArrayList<Observer<ObData>> eventList = new ArrayList<>();
    public static FragmentActivity bActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            // 针对线程的相关策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());

            // 针对VM的相关策略
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        super.onCreate(savedInstanceState);
        mContext = this;
        bActivity = this;

        ImmersionBar.with(this).init();

        mHandler = HandlerInter.getInstance();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        ScreenAdapterTools.getInstance().loadView((ViewGroup) getWindow().getDecorView());

    }

    protected void registEvent(Observer<ObData> observer){
        AppUtils.registEvent(observer);
        eventList.add(observer);
    }

    protected void unregisterEvent(Observer<ObData> observer){
        AppUtils.unregisterEvent(observer);
        for (int i = 0; i < eventList.size(); i++) {
            if (observer == eventList.get(i)){
                eventList.remove(i);
                break;
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mResources = getResources();
        initData();

    }

    protected abstract void initData();

    //获取String资源
    public String getResourcesString(int ResouseId){
       return mResources.getString(ResouseId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.hideLoading();

        for (int i = 0; i < eventList.size(); i++) {
            AppUtils.unregisterEvent(eventList.get(i));
        }
        eventList.clear();
    }

    /**
     * 将resid布局替换成fragment
     *
     * @param resid
     * @param fragment
     */
    public void replace(int resid, Fragment fragment) {
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        f.replace(resid, fragment).commitAllowingStateLoss();
    }

    /**
     * @param resid
     * @param fragment
     */
    public void add(int resid, Fragment fragment) {
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        f.add(resid, fragment).commitAllowingStateLoss();
    }

    /**
     * @param resid
     * @param fragment
     */
    public void add(int resid, Fragment fragment, String tag) {
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        f.add(resid, fragment, tag).commitAllowingStateLoss();
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    public void show(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    public void hide(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
    }


    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void removeFragmentToActivity(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void hideFragmentToActivity(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void showFragmentToActivity(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
