package com.lldj.tc.leye;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.behavior.AlipayRefreshBehavior;
import com.lldj.tc.toolslibrary.behavior.SmileView;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/11/28 14:34<p>
 * Modify Time: 2018/11/28 14:34<p>
 */


public class DemoFragement extends BaseLazyFragment implements AppBarLayout.OnOffsetChangedListener {

    Unbinder unbinder;
    @BindView(R.id.bg_toolbar_content)
    View bgToolbarContent;
    @BindView(R.id.layout_toolbar_content)
    RelativeLayout layoutToolbarContent;
    @BindView(R.id.smile_view)
    SmileView smileView;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_contact)
    ImageView ivContact;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.bg_toolbar_normal)
    View bgToolbarNormal;
    @BindView(R.id.layout_toolbar_normal)
    RelativeLayout layoutToolbarNormal;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_pay)
    ImageView ivPay;
    @BindView(R.id.iv_charge)
    ImageView ivCharge;
    @BindView(R.id.bg_toolbar_collapse)
    View bgToolbarCollapse;
    @BindView(R.id.layout_toolbar_collapse)
    RelativeLayout layoutToolbarCollapse;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.iv_zfb)
    ImageView ivZfb;
    @BindView(R.id.content_layout)
    NestedScrollView contentLayout;

    @Override
    protected void lazyLoad() {

    }

    public static DemoFragement newInstance() {
        DemoFragement fragment = new DemoFragement();
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragement_demo;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        appBar.addOnOffsetChangedListener(this);
        initListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 绑定事件
     */
    private void initListener() {
        final AlipayRefreshBehavior myAppBarLayoutBehavoir = (AlipayRefreshBehavior)
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        myAppBarLayoutBehavoir.setOnRefrehViewActionListener(new AlipayRefreshBehavior.onRefrehViewActionListener() {
            @Override
            public void onRefresh() {
                smileView.setDuration(2000);
                smileView.performAnim();
                RxTimerUtil.interval(2000, new RxTimerUtil.IRxNext() {
                    @Override
                    public void doNext(long number) {
                        myAppBarLayoutBehavoir.stopPin();
                        smileView.cancelAnim();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }
        });
//        myAppBarLayoutBehavoir.setOnRefrehViewActionListener(() -> {
//            smileView.setDuration(2000);
//            smileView.performAnim();
//            smileView.postDelayed(() -> Demo4Activity.this.runOnUiThread(() -> {
//                myAppBarLayoutBehavoir.stopPin();
//                smileView.cancelAnim();
//            }), 2000);
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
//        LogUtil.d("offset="+offset+"------"+scrollRange/2);
//        LogUtil.d("height="+layoutToolbarNormal.getHeight()+"------"+layoutToolbarContent.getHeight());


        /*本来应该取scrollRange/2  由于appbar包含三部分 Toolbar ToolbarContent 与middleContent
            此处用height代替  height为Toolbar和ToolbarContent的综合  由于ToolbarContent的layout_collapseMode为
            parallax，且layout_collapseParallaxMultiplier为0.7 所有此处做了0.7的处理
        **/
        int height = layoutToolbarNormal.getHeight() + layoutToolbarContent.getHeight() * 7 / 10;
        if (offset <= height / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            layoutToolbarNormal.setVisibility(View.VISIBLE);
            layoutToolbarCollapse.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (height / 2);
            int alpha2 = (int) (255 * scale2);
            bgToolbarNormal.setBackgroundColor(Color.argb(alpha2, 26, 128, 210));
        } else if (offset <= height) {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值

            layoutToolbarCollapse.setVisibility(View.VISIBLE);
            layoutToolbarNormal.setVisibility(View.GONE);
            float scale3 = (float) (height - offset) / (height / 2);
            int alpha3 = (int) (255 * scale3);
            bgToolbarCollapse.setBackgroundColor(Color.argb(alpha3, 26, 128, 210));
        } else {
            bgToolbarCollapse.setBackgroundColor(Color.argb(0, 26, 128, 210));
        }

        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / (scrollRange / 2);
        int alpha = (int) (255 * scale);
        bgToolbarContent.setBackgroundColor(Color.argb(alpha, 26, 128, 210));
    }
}
