package com.lldj.tc.knowledge;

import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.util.ScreenUtil;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;
import com.lldj.tc.toolslibrary.view.ViewPagerSlide;
import com.lldj.tc.webview.WebviewNormalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import refresh.swipe.OnRefreshListener;

/*
求知
 */
public class KnowledgeFragment extends BaseLazyFragment implements AppBarLayout.OnOffsetChangedListener, OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_and_search_layout)
    LinearLayout titleAndSearchLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.knowledge_viewpager)
    ViewPagerSlide knowledgeViewpager;
    @BindView(R.id.coordatorlayout)
    CoordinatorLayout coordatorlayout;
    @BindView(R.id.sear_top_layout)
    FrameLayout searTopLayout;
    Unbinder unbinder1;
    @BindView(R.id.xuefen_zhushou_layout)
    LinearLayout xuefenZhushouLayout;


    private KnowledgePagerAdapter mPagerAdapter;


    public static KnowledgeFragment newInstance() {
        KnowledgeFragment fragment = new KnowledgeFragment();
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragement_knowledge;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        titleTv.setText("求知");
        appbar.addOnOffsetChangedListener(this);
        initViewPager();
    }

    //初始化viewpager
    public void initViewPager() {
        mPagerAdapter = new KnowledgePagerAdapter(getFragmentManager());
        knowledgeViewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(knowledgeViewpager);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.e("打印偏移量", verticalOffset + "");
        if (verticalOffset < -ScreenUtil.dipTopx(mContext, 44)) {
            searTopLayout.setVisibility(View.VISIBLE);
        } else {
            searTopLayout.setVisibility(View.GONE);
//            ScreenUtil.setLinearLayoutParams(zhanweiView,ScreenUtil.getScreenWidth(mContext),ScreenUtil.dipTopx(mContext,94)+verticalOffset);
        }
        titleAndSearchLayout.setTranslationY(verticalOffset);


        //设置下拉刷新
//        if (null != swipeToLoadLayout) {
//            if (verticalOffset == 0) {
//                swipeToLoadLayout.setRefreshEnabled(true);
//            } else {
//                swipeToLoadLayout.setRefreshEnabled(false);
//            }
//        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.xuefen_zhushou_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xuefen_zhushou_layout:
                WebviewNormalActivity.launch(mContext, "学分查询");
                break;

        }
    }
}
