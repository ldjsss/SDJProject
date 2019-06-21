package com.lldj.tc.firstpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.FormatModel.JsonBean;
import com.lldj.tc.httpMgr.beans.FormatModel.Results;
import com.lldj.tc.httpMgr.beans.FormatModel.match.Odds;
import com.lldj.tc.httpMgr.beans.FormatModel.match.Team;
import com.lldj.tc.mainUtil.GlobalVariable;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 */
public class MatchDetailActivity extends BaseActivity implements LRecyclerView.LScrollListener{
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
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
    @BindView(R.id.gameplaycount)
    TextView gameplaycount;
    @BindView(R.id.gamestatus1)
    TextView gamestatus1;
    @BindView(R.id.gamestatusicon)
    ImageView gamestatusicon;

    private Results _matchData;
    private int ViewType;
    private int matchId;
    private MatchCellAdapter mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;

    private int mTotal = 0;
    LinearLayoutManager layoutManager;

    private String[] statusText;
    private int[] winBmp;


    public static void launch(Context pContext, int ViewType, int matchId) {
        Intent mIntent = new Intent(pContext, MatchDetailActivity.class);
        mIntent.putExtra("ViewType", ViewType);
        mIntent.putExtra("matchId", matchId);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.ViewType = bundle.getInt("ViewType");
        this.matchId = bundle.getInt("matchId");

        statusText = new String[]{"", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.matchCurrentTitle), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};
        winBmp = new int[]{R.mipmap.main_failure, R.mipmap.main_victory};

        onRefresh();
//        registEvent(new Observer<ObData>() {
//            @Override
//            public void onUpdate(Observable<ObData> observable, ObData data) {
//                if (data.getKey().equalsIgnoreCase(EventType.UPDATEMATCHLIST)) {
//                    ArrayList<Results> list = (ArrayList<Results>) data.getValue();
//                    _matchData = list.get(position);
//
//                    onRefresh();
//                }
//            }
//        });
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mResources.getString(R.string.matchDetialTitle));
    }

    @Override
    public void onRefresh() {
        HttpMsg.sendGetMatchDetial(matchId, new HttpMsg.Listener(){
            @Override
            public void onFinish(JsonBean res) {
                if(res.getCode() == GlobalVariable.succ){
                    _matchData = (Results)res.getResult();

                    Results _data = _matchData;
                    if (_data == null) return;

                    Team team0 = _data.getTeam() != null ? _data.getTeam().get(0) : null;
                    Team team1 = _data.getTeam() != null ? _data.getTeam().get(1) : null;
                    List<Odds> odds = _data.getOdds() != null ? _data.getOdds() : null;
                    int status = _data.getStatus();

                    gamename.setText(_data.getTournament_name());
                    gamenamecount.setText("/ " + _data.getRound());
                    gameplaycount.setText("+" + _data.getPlay_count());
                    playnamecommon0.setText(team0.getTeam_short_name());
                    playnamecommon1.setText(team1.getTeam_short_name());
                    matchtime.setText(_data.getStart_time());

                    if (status == 2) {
                        gamestatusicon.setImageResource(R.mipmap.match_status_1);
                    } else {
                        gamestatusicon.setImageResource(R.mipmap.match_status_0);
                    }
                    gamestatus.setText(statusText[status]);

                    HttpTool.getBitmapUrl(team0.getTeam_logo(), new HttpTool.bmpListener() {
                        @Override
                        public void onFinish(Bitmap bitmap) {
                            if (bitmap != null) imgplayicon0.setImageBitmap(bitmap);
                        }
                    });

                    HttpTool.getBitmapUrl(team1.getTeam_logo(), new HttpTool.bmpListener() {
                        @Override
                        public void onFinish(Bitmap bitmap) {
                            if (bitmap != null) imgplayicon1.setImageBitmap(bitmap);
                        }
                    });

//                    Log.w("-----detail data = ", _data.toString());

                    Map<String, List<Odds>> oddMap = new HashMap<>();
                    if(odds != null) {
                        ArrayList<String> keys = new ArrayList<>();
                        mTotal = 0;

                        for (int i = 0; i < odds.size(); i++) {
                            Odds _odd   = odds.get(i);
                            String _key = _odd.getMatch_stage();
                            List<Odds> itemList = oddMap.get(_key);
                            if(itemList == null){
                                itemList = new ArrayList<>();
                                keys.add(_key);
                            }
                            itemList.add(_odd);

                            oddMap.put(_key, itemList);
                        }

                        mTotal = keys.size();
                        initList(oddMap, keys);
                    }

                    if (mAdapter != null){
                        mAdapter.changeData(oddMap);
                        RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
                        jingcairecycleview.refreshComplete();
                    }
                }
            }
        });
    }

    private void initList(Map<String, List<Odds>> oddMap, ArrayList<String> keys){

        if(layoutManager == null) {
            layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new MatchCellAdapter(mContext);
            lAdapter = new LRecyclerViewAdapter(this, mAdapter);
            jingcairecycleview.setAdapter(lAdapter);
            jingcairecycleview.setLScrollListener(this);
            jingcairecycleview.setNoMore(true);//禁止加载更多
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                layoutManager.scrollToPositionWithOffset(tabPosition + 1, 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        tabLayout.removeAllTabs();
        for (int i = 0; i < keys.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(keys.get(i)));
        }
    }

    @Override
    public void onScrollUp() { }

    @Override
    public void onScrollDown() { }

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
        Log.e("firstVisible", firstVisible + "");
       if(tabLayout != null) tabLayout.setScrollPosition(firstVisible - 1, 0 ,false);
    }

    public void exitActivity() {
        finish();
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onViewClicked() {
        exitActivity();
    }

}
