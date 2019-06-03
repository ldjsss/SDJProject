package com.lldj.tc.firstpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.firstpage.subjectrecommend.GameCellAdapter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 */
public class MatchDetailActivity extends BaseActivity implements LRecyclerView.LScrollListener{
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
    @BindView(R.id.jingcairecycleview)
    LRecyclerView jingcairecycleview;
    private MatchCellAdapter mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ArrayList<String> mlist = new ArrayList<>();
    private int mTotal = 30;
    LinearLayoutManager layoutManager;


    public static void launch(Context pContext, int pType) {
        Intent mIntent = new Intent(pContext, MatchDetailActivity.class);
        mIntent.putExtra(TYPE, pType);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mResources.getString(R.string.matchDetialTitle));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition  = tab.getPosition();
                switch (tabPosition){
                    case 0:
                        jingcairecycleview.scrollToPosition(0);

                        break;
                    case 1:
                        jingcairecycleview.scrollToPosition(5);
                        layoutManager.scrollToPositionWithOffset(5, 0);
                        break;
                    case 2:
                        jingcairecycleview.scrollToPosition(10);
                        layoutManager.scrollToPositionWithOffset(10, 0);
                        break;
                    case 3:
                        jingcairecycleview.scrollToPosition(15);
                        layoutManager.scrollToPositionWithOffset(15, 0);
                        break;

                }



            }



            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("全场"));
        tabLayout.addTab(tabLayout.newTab().setText("第一局"));
        tabLayout.addTab(tabLayout.newTab().setText("第二局"));
        tabLayout.addTab(tabLayout.newTab().setText("第三局"));
        layoutManager  = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        jingcairecycleview.setLayoutManager(layoutManager);
        mAdapter = new MatchCellAdapter(mContext);
        lAdapter = new LRecyclerViewAdapter(this, mAdapter);
        jingcairecycleview.setAdapter(lAdapter);
        jingcairecycleview.setLScrollListener(this);
        //禁止加载更多
        jingcairecycleview.setNoMore(true);
//        jingcairecycleview.set
        setFirstData();


    }


    private void setFirstData() {
        for (int i = 0; i < 20; i++) {
            mlist.add("测试");
        }
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, 10, LoadingFooter.State.Normal, null);
    }


    public void exitActivity() {
        finish();
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onViewClicked() {
        exitActivity();
    }

    @Override
    public void onRefresh() {
        mlist.clear();
        setFirstData();
        RxTimerUtil.timer(500, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                //刷新完成
                jingcairecycleview.refreshComplete();
            }

            @Override
            public void onComplete() {
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
        Log.e("打印", "滚动到底部");
//        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(jingcairecycleview);
//        if (state == LoadingFooter.State.Loading) {
//            return;
//        }
//        if (mlist.size() < mTotal) {
//            RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, 10, LoadingFooter.State.Loading, null);
//            loadData();
//        } else {
//            RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, 10, LoadingFooter.State.TheEnd, null);
//        }

    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
        int firstVisible = layoutManager.findFirstVisibleItemPosition();

        if(firstVisible<5){
            tabLayout.getTabAt(0).select();
        }else if(firstVisible>=5 && firstVisible<10){
            tabLayout.getTabAt(1).select();

        }else if(firstVisible>=10 && firstVisible<15){
            tabLayout.getTabAt(2).select();

        }else if(firstVisible>=15 ){
            tabLayout.getTabAt(3).select();

        }


    }


//    public void loadData() {
//        RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
//            @Override
//            public void doNext(long number) {
//                for (int i = 0; i < 10; i++) {
//                    mlist.add("测试");
//                }
//                mAdapter.changeData(mlist);
//                RecyclerViewStateUtils.setFooterViewState(jingcairecycleview, LoadingFooter.State.Normal);
//                Log.e("数组长度", mlist.size() + "==");
//                if (mlist.size() >= mTotal) {
//                    RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, 10, LoadingFooter.State.TheEnd, null);
//                }
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
//
//    }
}
