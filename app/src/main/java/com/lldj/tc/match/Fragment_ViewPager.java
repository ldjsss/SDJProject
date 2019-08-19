package com.lldj.tc.match;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.CountBean;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.MatchBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    int colorSelect = Color.parseColor("#f0e6d8");
    int colorUnSelect = Color.parseColor("#6c6b63");

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
    private void initViewPager() {

        mPagerAdapter = new Adapter_MainPager(mContext, mContext.getSupportFragmentManager());
        firstpageViewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(firstpageViewpager);
        tabLayout.setTabRippleColor(null);
//        tabLayout.setTabIndicatorFullWidth(false);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(2);
        tabLayout.setSelectedTabIndicator(gradientDrawable);
        definedTablayout();
        firstpageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
//                Log.e("currentPosition", "currentPosition===" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        this.registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.MATCHCOUNT)) {
                    CountBean _value = (CountBean)data.getValue();
                    if(_value == null) return;
                    CountBean.CountMode _data = (CountBean.CountMode) _value.getResult();
                    updateTitle(0, String.valueOf(_data.getToday()));
                    updateTitle(1, String.valueOf(_data.getLive()));
                    updateTitle(2, String.valueOf(_data.getEarly()));
                }
            }
        });

//        AppUtils.reduceMarginsInTabs(tabLayout, 30);
//        AppUtils.setIndicator(getContext(), tabLayout,20, 20);
    }

    //自定义tab布局
    public void definedTablayout() {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.tab_item);
            View cusView = tab.getCustomView();
//            AppUtils.screenAdapterLoadView((ViewGroup)cusView);
            setTextSelect(tab, i == 0);
            TextView textView = cusView.findViewById(R.id.tab_text);
            textView.setText(mPagerAdapter.getPageTitle(i));

        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTextSelect(tab, true);
                firstpageViewpager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTextSelect(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setTextSelect(TabLayout.Tab tab, boolean select){
        View view = tab.getCustomView();
        ((TextView)view.findViewById(R.id.tab_text)).setTextColor(select ? colorSelect : colorUnSelect);
        ((TextView)view.findViewById(R.id.tab_no_reading_count_tv)).setTextColor(select ? colorSelect : colorUnSelect);
    }

    public void updateTitle(int index, String title) {
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab == null) return;
        TextView textView = tab.getCustomView().findViewById(R.id.tab_no_reading_count_tv);
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
                Toast.makeText(mContext,"This functionality is not yet implemented!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
