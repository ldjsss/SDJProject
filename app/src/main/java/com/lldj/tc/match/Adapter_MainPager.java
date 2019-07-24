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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Adapter_MainPager extends FragmentStatePagerAdapterCompat {
    private String[] mTitle;
    private Map<Integer,Fragment> fragmentList = new HashMap<>();


    public Adapter_MainPager(Context context, FragmentManager fm) {
        super(fm);
        Resources mResources = context.getResources();
        mTitle = new String[]{mResources.getString(R.string.matchTodayTitle), mResources.getString(R.string.matchCurrentTitle), mResources.getString(R.string.matchFrontTitle), mResources.getString(R.string.matchOverTitle)};
    }

    private Fragment getFrament(int position){
        Fragment fragment = fragmentList.get(position);
        if(fragment == null){
            fragment = new Fragment_Main();
            Bundle bundle = new Bundle();
            bundle.putInt("ARG", position);
            fragment.setArguments(bundle);
            fragmentList.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    public Fragment getFragment(int position){
        return getFrament(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        return getFrament(position);
    }
}


