package com.lldj.tc.toolslibrary.view;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lldj.tc.toolslibrary.util.Clog;


public abstract class BaseLazyFragment extends Fragment {

    /**
     * 全局activity
     */
    protected FragmentActivity mContext;

    protected FragmentManager mFragmentManager;

    protected View rootView;
    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
        Clog.d(getClass().getSimpleName(), "onAttach");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = mContext.getSupportFragmentManager();
        Clog.d(getClass().getSimpleName(), "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentView() != 0) {
            if (rootView == null) {
                rootView = inflater.inflate(getContentView(), container, false);
                initView(rootView);
                isPrepared = true;
                lazyLoad();
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
    protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}

    public abstract int getContentView();


    public abstract void initView(View rootView);

    @Override
    public void onStop() {
        super.onStop();
        Clog.d(getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Clog.d(getClass().getSimpleName(), "onDestroy");
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
}
