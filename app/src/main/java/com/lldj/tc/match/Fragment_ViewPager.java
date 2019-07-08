package com.lldj.tc.match;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_ViewPager extends BaseFragment {

    @BindView(R.id.toolbar_left_menu_iv)
    ImageView toolbarLeftMenuIv;
    @BindView(R.id.toolbar_gameselect)
    ImageView toolbarGameselect;
    @BindView(R.id.toolbar_title_tv)
    ImageView toolbarTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.firstpage_viewpager)
    ViewPager firstpageViewpager;
    private Adapter_MainPager mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() { return R.layout.activity_main_include_layout; }


    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);

        initViewPager();
    }

    //初始化viewpager
    public void initViewPager() {
        mPagerAdapter = new Adapter_MainPager(mContext, mContext.getSupportFragmentManager());
        firstpageViewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(firstpageViewpager);
        definedTablayout();
        firstpageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Log.e("currentPosition", "currentPosition===" + position);
                mPagerAdapter.selectView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        this.registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.MATCHCOUNT)) {
                    updateTitle(Integer.parseInt(data.getTag()), data.getValue() + "");
                }
            }
        });
    }

    //自定义tab布局
    public void definedTablayout() {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.tab_item);//给每一个tab设置view
            View cusView = tab.getCustomView();
//            AppUtils.screenAdapterLoadView((ViewGroup)cusView);
            if (i == 0) {
                cusView.findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }
            TextView textView = (TextView) cusView.findViewById(R.id.tab_text);
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
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void updateTitle(int index, String title) {
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab == null) return;
        TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_no_reading_count_tv);
        if (textView == null) return;
        textView.setText(title);
    }

    @OnClick({R.id.toolbar_left_menu_iv, R.id.toolbar_gameselect, R.id.connectservice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_menu_iv:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEFTMENU);
                break;
            case R.id.toolbar_gameselect:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.GAMESELECT);
                break;
            case R.id.connectservice:
                Toast.makeText(mContext,"---------------test2",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
