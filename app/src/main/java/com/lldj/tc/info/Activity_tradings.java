package com.lldj.tc.info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.http.beans.TradingBean;
import com.lldj.tc.match.Adapter_MatchCell;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.utils.GlobalVariable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_tradings extends BaseActivity implements LRecyclerView.LScrollListener {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.imservice)
    ImageView imservice;
    @BindView(R.id.tvservices)
    TextView tvservices;
    @BindView(R.id.connectservice)
    RelativeLayout connectservice;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.jingcairecycleview)
    LRecyclerView jingcairecycleview;
    private LinearLayoutManager layoutManager;
    private Adapter_tradingCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int Anim_fade = intent.getIntExtra("Anim_fade", R.style.Anim_fade);

        setContentView(R.layout.activity_tradings);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = Anim_fade;
        getWindow().setAttributes(params);


        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.tradingsTitle));

        initRecycleview();

        onRefresh();

    }

    @Override
    protected void initData() {

    }

    private void initRecycleview() {

        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new Adapter_tradingCell(this);
            lAdapter = new LRecyclerViewAdapter(this, mAdapter);
            jingcairecycleview.setAdapter(lAdapter);
            jingcairecycleview.setLScrollListener(this);
            jingcairecycleview.setNoMore(true);
            jingcairecycleview.setPullRefreshEnabled(false);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabPosition = tab.getPosition();
                    Log.e("onTabSelected", tabPosition + "");
                    layoutManager.scrollToPosition(tabPosition + 1);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            String[] tabs = {getResourcesString(R.string.tradingsAll), getResourcesString(R.string.tradingsIn), getResourcesString(R.string.tradingsOut), getResourcesString(R.string.tradingsPre)};
            for (int i = 0; i < tabs.length; i++) {
                tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            }
        }
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onClick() {

        Intent _intent = new Intent(this, Activity_Shop.class);
        _intent.putExtra("Anim_fade", R.style.Anim_left);
        startActivity(_intent);
        finish();
        overridePendingTransition(0, R.anim.out_to_right);
    }

    @Override
    public void onRefresh() {

        HttpMsg.getInstance().sendTradings(SharePreUtils.getInstance().getToken(mContext), 0+"", 1+"", TradingBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                TradingBean res = (TradingBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                }
            }
        });
    }

    @Override
    public void onScrollUp() {

    }

    @Override
    public void onScrollDown() {

    }

    @Override
    public void onBottom() {

    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {

    }
}
