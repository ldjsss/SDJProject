package com.lldj.tc.firstpage;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;

public class TablePageManger {

    private FirstPageViewPagerAdapter mPagerAdapter;
    private FragmentManager fm = null;
    private TabLayout tabLayout = null;
    private ViewPager firstpageViewpager = null;
    private Context content;

    public TablePageManger(Context _context,FragmentManager _fm, TabLayout _tabLayout, ViewPager _firstpageViewpager) {
            fm = _fm;
            tabLayout = _tabLayout;
            firstpageViewpager = _firstpageViewpager;
            content = _context;

            initViewPager();
        }

        //初始化viewpager
        public void initViewPager() {
            mPagerAdapter = new FirstPageViewPagerAdapter(content, fm);
            firstpageViewpager.setAdapter(mPagerAdapter);
            tabLayout.setupWithViewPager(firstpageViewpager);
            definedTablayout();
            firstpageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Log.e("currentPosition","currentPosition==="+position);
                    mPagerAdapter.selectView(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        //自定义tab布局
        public void definedTablayout(){
            for (int i = 0; i < mPagerAdapter.getCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
                tab.setCustomView(R.layout.tab_item);//给每一个tab设置view
                if (i == 0) {
                    // 设置第一个tab的TextView是被选择的样式
                    tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
                }
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
                textView.setText(mPagerAdapter.getPageTitle(i));//设置tab上的文字

            }
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                    firstpageViewpager.setCurrentItem(tab.getPosition(), true);

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }

        public void updateTitle(int index, String title){
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            if(tab == null) return;
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_no_reading_count_tv);
            if(textView == null) return;
            textView.setText(title);
        }
}
