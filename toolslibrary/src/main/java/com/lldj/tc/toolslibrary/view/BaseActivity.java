package com.lldj.tc.toolslibrary.view;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;


public abstract class BaseActivity extends FragmentActivity {
    protected Handler handler;
    protected boolean isPause;

    //资源
    protected Resources mResources;
    public BaseActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        handler = new Handler();
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

}
