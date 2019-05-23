package com.lldj.tc.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lldj.tc.login.LoginActivity;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;

import java.util.ArrayList;

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ArrayList<View> mviews;
    private ViewPager mPager;
    private GuidePagerAdapter mPagerAdapter;
    /**
     * @description 底部的小圆点
     */
    private ImageView[] mdots;
    /**
     * @description 当前为第几张
     */
    private int currentIndex;
    /**
     * @description 一共有几张图片
     */
    private int mLength = 3;
    private TextView mEnterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
        init_viewpager();

    }

    private void init_viewpager() {

        mviews = new ArrayList<View>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = getLayoutInflater();
        mviews.add(inflater.inflate(R.layout.guide_page_one, null));
        mviews.add(inflater.inflate(R.layout.guide_page_two, null));
        View page5 = inflater.inflate(R.layout.guide_page_three, null);
        mviews.add(page5);

        mEnterBtn = (TextView) page5.findViewById(R.id.enter_btn);
        mEnterBtn.setOnClickListener(this);


        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setVisibility(View.VISIBLE);
        mPagerAdapter = new GuidePagerAdapter(mviews);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(this);
        initBottomDots();
    }

    private void initBottomDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.lll);
        mdots = new ImageView[mLength];
        for (int i = 0; i < mLength; i++) {
            mdots[i] = (ImageView) ll.getChildAt(i);
            mdots[i].setEnabled(true);
            mdots[i].setTag(i);
        }
        currentIndex = 0;
        mdots[currentIndex].setEnabled(false);
        mdots[currentIndex].setImageResource(R.mipmap.dot_guide_red);
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > mLength - 1 || currentIndex == positon) {
            return;
        }
        mdots[positon].setEnabled(false);
        mdots[currentIndex].setEnabled(true);
        currentIndex = positon;
        draw_Point(currentIndex);
//        Clog.e("positon", positon + "");
    }

    public void draw_Point(int index) {
        for (int i = 0; i < mdots.length; i++) {
            if (index == i) {
                mdots[i].setImageResource(R.mipmap.dot_guide_red);
            } else
                mdots[i].setImageResource(R.mipmap.dot_guide_write);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurDot(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_btn:
                Intent mIntent = new Intent(this, LoginActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
    }
}
