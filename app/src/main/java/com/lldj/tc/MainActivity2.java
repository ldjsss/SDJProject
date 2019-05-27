package com.lldj.tc;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.firstpage.TablePageManger;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ViewPagerSlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity2 extends BaseActivity {
    @BindView(R.id.toolbar_left_menu_iv)
    ImageView toolbarLeftMenuIv;
    @BindView(R.id.toolbar_title_tv)
    ImageView toolbarTitleTv;
    @BindView(R.id.toolbar_right_tv)
    ImageView toolbarRightTv;
    @BindView(R.id.mainflayout)
    FrameLayout mainflayout;
    @BindView(R.id.left_layout)
    RelativeLayout leftLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.back_main_iv)
    ImageView backMainIv;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.firstpage_viewpager)
    ViewPager firstpageViewpager;
    private  TablePageManger tablePageManger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        //禁止手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        tablePageManger = new TablePageManger(this, getSupportFragmentManager(), tabLayout, firstpageViewpager);
    }


    @OnClick({R.id.toolbar_left_menu_iv, R.id.toolbar_title_tv, R.id.back_main_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_menu_iv:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.toolbar_title_tv:
                break;
            case R.id.back_main_iv:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }
}
