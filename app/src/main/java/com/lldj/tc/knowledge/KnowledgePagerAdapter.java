package com.lldj.tc.knowledge;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lldj.tc.knowledge.Live.LiveFragment;
import com.lldj.tc.knowledge.SmallClass.SmallClassFragment;
import com.lldj.tc.knowledge.Subject.SubjectFragment;


public class KnowledgePagerAdapter extends FragmentStatePagerAdapter {
    public final int COUNT = 3;
    private String[] mTitle = {"课程推荐", "直播推荐", "微课推荐"};

    public KnowledgePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://课程推荐
                return SubjectFragment.newInstance();
            case 1://资直播推荐
                return LiveFragment.newInstance();
            case 2://微课推荐
                return SmallClassFragment.newInstance();
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
