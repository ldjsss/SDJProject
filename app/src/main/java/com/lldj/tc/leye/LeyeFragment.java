package com.lldj.tc.leye;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.util.ScreenUtil;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;
import com.lldj.tc.toolslibrary.view.ViewPagerSlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import refresh.swipe.OnRefreshListener;

/*
乐业
 */
public class LeyeFragment extends BaseLazyFragment implements AppBarLayout.OnOffsetChangedListener, OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.leye_viewpager)
    ViewPagerSlide leyePager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.coordatorlayout)
    CoordinatorLayout coordatorlayout;
    @BindView(R.id.sear_top_layout)
    FrameLayout searTopLayout;
    @BindView(R.id.title_and_search_layout)
    LinearLayout titleAndSearchLayout;


    private LeYeViewPagerAdapter mPagerAdapter;


    public static LeyeFragment newInstance() {
        LeyeFragment fragment = new LeyeFragment();
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragement_leye;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        titleTv.setText("乐业");
        initViewPager();
        appbar.addOnOffsetChangedListener(this);
    }

    //初始化viewpager
    public void initViewPager() {
        mPagerAdapter = new LeYeViewPagerAdapter(getFragmentManager());
        leyePager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(leyePager);
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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
        RxTimerUtil.interval(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
//                swipeToLoadLayout.setRefreshing(false);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}

