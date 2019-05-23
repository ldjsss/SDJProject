package com.lldj.tc.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lldj.tc.mine.MineFragment;
import com.lldj.tc.firstpage.FirstpageFragment;
import com.lldj.tc.leye.LeyeFragment;
import com.lldj.tc.knowledge.KnowledgeFragment;


public class ViewPagerAdapterMain extends FragmentStatePagerAdapter {
    public final int COUNT = 4;


    public ViewPagerAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://首页
                return FirstpageFragment.newInstance();
            case 1://求知
                return KnowledgeFragment.newInstance();
            case 2://乐业
                return LeyeFragment.newInstance();
            case 3://我的
                return MineFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

}
