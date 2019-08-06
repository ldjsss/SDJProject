package com.lldj.tc.info;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.FragmentStatePagerAdapterCompat;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Activitys extends BaseActivity {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.recordtablayout)
    TabLayout recordtabLayout;
    @BindView(R.id.recordpager)
    ViewPager recordpager;

    private Adapter_RecordPager mPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogrecordlayout);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);

        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.activity));
        initViewPager();
    }

    private void initViewPager() {
        ButterKnife.bind(this);
        mPagerAdapter = new Adapter_RecordPager(this, getSupportFragmentManager());
        recordpager.setAdapter(mPagerAdapter);

        recordtabLayout.setupWithViewPager(recordpager);
        definedTablayout();
        recordpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("currentPosition", "currentPosition===" + position);
                mPagerAdapter.selectView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void definedTablayout() {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = recordtabLayout.getTabAt(i);
            tab.setCustomView(R.layout.tab_item);
            View cusView = tab.getCustomView();
            if (i == 0) {
                cusView.findViewById(R.id.tab_text).setSelected(true);
            }
            TextView textView = (TextView) cusView.findViewById(R.id.tab_text);
            textView.setText(mPagerAdapter.getPageTitle(i));

        }
        recordtabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                recordpager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        AppUtils.reduceMarginsInTabs(recordtabLayout, 100);
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                overridePendingTransition(0,R.anim.out_to_right);
                break;
        }
    }


    class Adapter_RecordPager extends FragmentStatePagerAdapterCompat {

        private Resources mResources;
        private String[] mTitle;
        public List<Fragment> recordList = new ArrayList<>();

        public Adapter_RecordPager(Context context, FragmentManager fm) {
            super(fm);

            mResources = context.getResources();
            mTitle = new String[]{mResources.getString(R.string.preferential), mResources.getString(R.string.task)};

            setUpFragments();
        }

        private void setUpFragments() {
            recordList.clear();
            for (int i = 0; i < mTitle.length; i++) {
                Frament_Activity fragment = new Frament_Activity();
                Bundle bundle = new Bundle();
                bundle.putInt("ARG", i);
                fragment.setArguments(bundle);
                recordList.add(fragment);
            }
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        public void selectView(int position) {
            Log.e("currentPosition", "selectView currentPosition===" + position);
            BaseFragment fragment = (BaseFragment) getItem(position);
            fragment.selectView(position);
        }

        public Fragment getFragment(int position) {
            return recordList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return recordList.get(position);
        }
    }
}
