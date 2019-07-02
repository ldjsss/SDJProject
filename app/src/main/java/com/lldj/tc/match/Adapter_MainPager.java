package com.lldj.tc.match;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.FragmentStatePagerAdapterCompat;

import java.util.ArrayList;
import java.util.List;


public class Adapter_MainPager extends FragmentStatePagerAdapterCompat {
    private Resources mResources;
    private String[] mTitle;
    public List<Fragment> fragmentList = new ArrayList<>();  //创建List，用来管理所有要添加到ViewPager的Fragment


    public Adapter_MainPager(Context content, FragmentManager fm) {
        super(fm);
        mResources = content.getResources();
        mTitle = new String[]{mResources.getString(R.string.matchTodayTitle), mResources.getString(R.string.matchCurrentTitle), mResources.getString(R.string.matchFrontTitle), mResources.getString(R.string.matchOverTitle)};

        setUpFragments();
    }

    //添加Fragment
    private void setUpFragments() {
        fragmentList.clear();
        for (int i = 0; i < mTitle.length; i++) {
            Fragment_Main fragment = new Fragment_Main();
            Bundle bundle = new Bundle();
            bundle.putInt("ARG", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
    }

    public void selectView(int position){
        Log.e("currentPosition","selectView currentPosition==="+position);
        BaseFragment fragment = (BaseFragment)getItem(position);
        fragment.selectView(position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    public Fragment getFragment(int position){
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}


