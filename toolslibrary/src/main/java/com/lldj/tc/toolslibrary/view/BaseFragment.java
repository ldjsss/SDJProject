package com.lldj.tc.toolslibrary.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;

import java.util.ArrayList;


public abstract class BaseFragment extends Fragment {

    /**
     * 全局activity
     */
    protected FragmentActivity mContext;

    protected FragmentManager mFragmentManager;

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    private ArrayList<Observer<ObData>> eventList = new ArrayList<>();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("dd", "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
//        Clog.d(getClass().getSimpleName(), "onAttach");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = mContext.getSupportFragmentManager();
        initVariable();
//        Clog.d(getClass().getSimpleName(), "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentView() != 0) {
            if (rootView == null) {
                rootView = inflater.inflate(getContentView(), container, false);
//                AppUtils.screenAdapterLoadView((ViewGroup)rootView);
                initView(rootView);
            } else {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null) {
                    parent.removeView(rootView);
                }
            }

            return rootView;
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
//        Log.w("dd", "onFragmentVisibleChange -> isVisible: " + isVisible);
    }

    public abstract int getContentView();


    public abstract void initView(View view);

    @Override
    public void onStop() {
        super.onStop();
        Clog.d(getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Clog.d(getClass().getSimpleName(), "onDestroy");

        for (int i = 0; i < eventList.size(); i++) {
            AppUtils.unregisterEvent(eventList.get(i));
        }
        eventList.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 将resid布局替换成fragment
     *
     * @param resid
     * @param fragment
     */
    public void replace(int resid, Fragment fragment) {
        mFragmentManager.beginTransaction().replace(resid, fragment).commitAllowingStateLoss();
    }

    /**
     * @param resid
     * @param fragment
     */
    public void add(int resid, Fragment fragment) {
        mFragmentManager.beginTransaction().add(resid, fragment).commitAllowingStateLoss();
    }

    /**
     * @param resid
     * @param fragment
     */
    public void add(int resid, Fragment fragment, String tag) {
        mFragmentManager.beginTransaction().add(resid, fragment, tag).commitAllowingStateLoss();
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    public void show(Fragment fragment) {
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    public void hide(Fragment fragment) {
        mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    public void selectView(int position){
    }

}
