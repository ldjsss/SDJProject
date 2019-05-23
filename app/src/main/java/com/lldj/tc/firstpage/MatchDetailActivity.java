package com.lldj.tc.firstpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ViewPagerSlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 */
public class MatchDetailActivity extends BaseActivity {
    public static final String TYPE = "type";
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.gameicon)
    ImageView gameicon;
    @BindView(R.id.gamename)
    TextView gamename;
    @BindView(R.id.gamenamecount)
    TextView gamenamecount;
    @BindView(R.id.gamestatus)
    TextView gamestatus;
    @BindView(R.id.matchwin0)
    TextView matchwin0;
    @BindView(R.id.matchwin1)
    TextView matchwin1;
    @BindView(R.id.matchtime)
    TextView matchtime;
    @BindView(R.id.imgplayicon0)
    ImageView imgplayicon0;
    @BindView(R.id.playnamecommon0)
    TextView playnamecommon0;
    @BindView(R.id.imgplayicon1)
    ImageView imgplayicon1;
    @BindView(R.id.playnamecommon1)
    TextView playnamecommon1;
    @BindView(R.id.img_layout)
    RelativeLayout imgLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.page_viewpager)
    ViewPagerSlide pageViewpager;


    public static void launch(Context pContext, int pType) {
        Intent mIntent = new Intent(pContext, MatchDetailActivity.class);
        mIntent.putExtra(TYPE, pType);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);

        initData();
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mResources.getString(R.string.matchDetialTitle));

    }

    public void exitActivity() {
        RxTimerUtil.cancel();
        finish();
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onViewClicked() {
        exitActivity();
    }
}
