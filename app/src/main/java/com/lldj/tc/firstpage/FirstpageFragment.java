package com.lldj.tc.firstpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.firstpage.banner.BannerInfo;
import com.lldj.tc.firstpage.banner.MyBannerUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.util.ScreenUtil;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;
import com.lldj.tc.toolslibrary.view.ViewPagerSlide;
import com.lldj.tc.webview.WebviewNormalActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import refresh.swipe.OnRefreshListener;
import refresh.swipe.SwipeToLoadLayout;


/**
 * description: 首页<p>
 */
public class FirstpageFragment extends BaseLazyFragment implements AppBarLayout.OnOffsetChangedListener, OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.firstpage_viewpager)
    ViewPagerSlide firstpageViewpager;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.sear_top_layout)
    FrameLayout searTopLayout;
    Unbinder unbinder1;
    @BindView(R.id.refresh_iv)
    ImageView refreshIv;
    @BindView(R.id.pro_bar)
    ProgressBar proBar;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.title_and_search_layout)
    LinearLayout titleAndSearchLayout;

    @BindView(R.id.convenient_banner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.my_refresh_iv)
    ImageView myRefreshIv;
    @BindView(R.id.xuefen_zhushou_layout)
    ImageView xuefenZhushouLayout;
    @BindView(R.id.viewpage_layout)
    RelativeLayout viewpageLayout;
    @BindView(R.id.swipe_target)
    CoordinatorLayout swipeTarget;


    private FirstPageViewPagerAdapter mPagerAdapter;


    public static FirstpageFragment newInstance() {
        FirstpageFragment fragment = new FirstpageFragment();
        return fragment;
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public int getContentView() {
        return R.layout.fragement_firstpage;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        titleTv.setText("首页");
//        ImmersionBar.with(this).titleBar(titleTv).init();
        initViewPager();
        appbar.addOnOffsetChangedListener(this);
        //设置banner数据
        List<BannerInfo> mBannerList = new ArrayList<>();
        mBannerList.add(new BannerInfo());
        mBannerList.add(new BannerInfo());
        int mWidth = ScreenUtil.getScreenWidth(mContext);
        int mHeight = mWidth * 200 / 375;
        ScreenUtil.setLinearLayoutParams(convenientBanner, mWidth, mHeight);
        MyBannerUtils.INSTANCE.initBanner(mContext, convenientBanner, mBannerList);
        swipeToLoadLayout.setOnRefreshListener(this);

    }

    //初始化viewpager
    public void initViewPager() {
        mPagerAdapter = new FirstPageViewPagerAdapter(mContext, getFragmentManager());
        firstpageViewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(firstpageViewpager);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.e("打印偏移量", verticalOffset + "");
        if (verticalOffset < -ScreenUtil.dipTopx(mContext, 44)) {
            searTopLayout.setVisibility(View.VISIBLE);
        } else {
            searTopLayout.setVisibility(View.GONE);
        }
        titleAndSearchLayout.setTranslationY(verticalOffset);


        //设置下拉刷新
        if (null != swipeToLoadLayout) {
            if (verticalOffset == 0) {
                swipeToLoadLayout.setRefreshEnabled(true);
            } else {
                swipeToLoadLayout.setRefreshEnabled(false);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning();
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }


    @Override
    public void onRefresh() {
        myRefreshIv.setVisibility(View.GONE);
        RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                swipeToLoadLayout.setRefreshing(false);
//                if(firstpageViewpager.getCurrentItem()==0){
//                    SubjectRecommendFragment subjectRecommendFragment = (SubjectRecommendFragment) mPagerAdapter.getItem(0);
//                    subjectRecommendFragment.onRefresh();
//                }

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @OnClick({R.id.xuefen_zhushou_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xuefen_zhushou_layout:
                WebviewNormalActivity.launch(mContext,"学分查询");
                break;

        }
    }
}

