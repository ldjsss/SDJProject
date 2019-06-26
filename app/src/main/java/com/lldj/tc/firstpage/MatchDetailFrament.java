package com.lldj.tc.firstpage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.JsonBean;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.Team;
import com.lldj.tc.mainUtil.GlobalVariable;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * description: <p>
 */
public class MatchDetailFrament extends BaseFragment implements LRecyclerView.LScrollListener{
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

    private ResultsModel _matchData;
    private int ViewType;
    private int matchId;
    private MatchCellAdapter mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private int mTotal = 0;
    private LinearLayoutManager layoutManager;
    private DrawerLayout drawerLayout;

    private String[] statusText;
    private int disTime = 4000;
    private Disposable disposable;

    @Override
    public int getContentView() {
        return R.layout.frament_match_detail;
    }

    @Override
    public void initView(View view) {

        ButterKnife.bind(this, view);

        if (statusText == null)statusText = new String[]{"", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.matchCurrentTitle), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};

        ImmersionBar.with((Activity)mContext).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mContext.getString(R.string.matchDetialTitle));

        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        fullScreenImmersive(mContext.getWindow().getDecorView());
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    public void showView(int ViewType, int matchId, DrawerLayout drawerLayout){
        this.ViewType = ViewType;
        this.matchId  = matchId;
        onRefresh();

        this.drawerLayout = drawerLayout;
        drawerLayout.openDrawer(Gravity.RIGHT);

    }

    @Override
    public void onRefresh() {
        HttpMsg.getInstance().sendGetMatchDetial(matchId, JsonBean.class, new HttpMsg.Listener(){
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if(res.getCode() == GlobalVariable.succ){
                    _matchData = (ResultsModel)res.getResult();

                    ResultsModel _data = _matchData;
                    if (_data == null) return;

                    Team team0 = _data.getTeam() != null ? _data.getTeam().get(0) : null;
                    Team team1 = _data.getTeam() != null ? _data.getTeam().get(1) : null;
                    List<Odds> odds = _data.getOdds() != null ? (List<Odds>)_data.getOdds() : null;
                    int status = _data.getStatus();

                    gamename.setText(_data.getTournament_name());
                    gamenamecount.setText("/ " + _data.getRound());
                    gameplaycount.setText("+" + _data.getPlay_count());
                    playnamecommon0.setText(team0.getTeam_short_name());
                    playnamecommon1.setText(team1.getTeam_short_name());
                    matchtime.setText(_data.getStart_time());

                    if (status == 2) { //gameing
                        gamestatusicon.setImageResource(R.mipmap.match_status_1);
                        startUpdate();
                    } else { //other
                        gamestatusicon.setImageResource(R.mipmap.match_status_0);
                        stopUpdate();
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
                        mAdapter.changeData(oddMap, ViewType, _data);
                        RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
                        jingcairecycleview.refreshComplete();
                    }
                }
            }
        });
    }

    private void initList(Map<String, List<Odds>> oddMap, ArrayList<String> keys){

        if(layoutManager == null) {
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new MatchCellAdapter(getContext());
            lAdapter = new LRecyclerViewAdapter(getContext(), mAdapter);
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
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        Log.e("firstVisible", firstVisible + "");
       if(tabLayout != null) tabLayout.setScrollPosition(firstVisible - 1, 0 ,false);
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onViewClicked() {
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }

    private void startUpdate(){
        if (disposable != null) return;

        disposable = RxTimerUtilPro.interval(disTime, new RxTimerUtilPro.IRxNext() {
            @Override
            public void doNext(long number) {
                onRefresh();
            }

            @Override
            public void onComplete() { }
        });
    }

    private void stopUpdate(){
        RxTimerUtilPro.cancel(disposable);
        disposable = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopUpdate();
    }


}
