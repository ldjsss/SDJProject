package com.lldj.tc.leye;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lldj.tc.leye.caseDiscussion.ScientificFragment;
import com.lldj.tc.leye.drugInfo.DrugInfoFragment;


public class LeYeViewPagerAdapter extends FragmentStatePagerAdapter {
    public final int COUNT = 2;
    private String[] mTitle = {"药品资讯", "科研动态"};

    public LeYeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://药品资讯
                return DrugInfoFragment.newInstance();
            case 1://科研动态
                return ScientificFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
