package com.lldj.tc.match;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
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
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.Team;
import com.lldj.tc.httpMgr.beans.JsonBean;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.GlobalVariable;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.disposables.Disposable;

/**
 * description: <p>
 */
public class Frament_MatchDetail extends BaseFragment implements LRecyclerView.LScrollListener {
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
    @BindView(R.id.looklayout)
    RelativeLayout looklayout;
    @BindView(R.id.imglayout0)
    RelativeLayout imglayout0;
    @BindView(R.id.imglayout1)
    RelativeLayout imglayout1;
    @BindView(R.id.vidiolayout)
    RelativeLayout vidiolayout;
    @BindView(R.id.lookmatch)
    TextView lookmatch;
    @BindView(R.id.arrowplay)
    TextView arrowplay;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;

    private ResultsModel _matchData;
    private int ViewType;
    private int matchId;
    private Adapter_MatchCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private int mTotal = 0;
    private LinearLayoutManager layoutManager;
    private DrawerLayout drawerLayout;

    private String[] statusText;
    private int disTime = 4000;
    private Disposable disposable;
    private Observer<ObData> observer;

    @Override
    public int getContentView() {
        return R.layout.frament_match_detail;
    }

    @Override
    public void initView(View view) {

        ButterKnife.bind(this, view);

        if (statusText == null)
            statusText = new String[]{"unknown", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.gameing), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};

        ImmersionBar.with((Activity) mContext).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mContext.getString(R.string.matchDetialTitle));

        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        fullScreenImmersive(mContext.getWindow().getDecorView());
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        observer = new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.SELECTGROUPS)) {
                    if (mAdapter != null) {
                        mAdapter.updateSelect((List<ObData>) data.getValue());
                    }
                }
            }
        };
        AppUtils.registEvent(observer);
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

    public void showView(int ViewType, int matchId) {
        this.ViewType = ViewType;
        this.matchId = matchId;

//        gamestatus.setVisibility(View.GONE);

        onRefresh();

    }

    @Override
    public void onRefresh() {
        HttpMsg.getInstance().sendGetMatchDetial(matchId, JsonBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    _matchData = (ResultsModel) res.getResult();

                    ResultsModel _data = _matchData;
                    if (_data == null) return;

                    Team team0 = _data.getTeam() != null ? _data.getTeam().get(0) : null;
                    Team team1 = _data.getTeam() != null ? _data.getTeam().get(1) : null;
                    List<Odds> odds = _data.getOdds() != null ? (List<Odds>) _data.getOdds() : null;
                    int status = _data.getStatus();

                    gamename.setText(_data.getTournament_name());
                    gamenamecount.setText("/ " + _data.getRound());
                    gameplaycount.setText("+" + _data.getPlay_count());
                    playnamecommon0.setText(team0.getTeam_short_name());
                    playnamecommon1.setText(team1.getTeam_short_name());
                    matchtime.setText(_data.getStart_time());

                    if (status == 2) startUpdate();
                    else stopUpdate();

                    gamestatus1.setText(status == 2 ? getResources().getString(R.string.gameing) : "21:00:00");
                    matchwin0.setVisibility((status == 2 || status == 3) ? View.VISIBLE : View.GONE);
                    matchwin1.setVisibility((status == 2 || status == 3) ? View.VISIBLE : View.GONE);

                    gamestatus.setText(statusText[status]);
                    gamestatus.setVisibility(status == 2 ? View.GONE : View.VISIBLE);
                    looklayout.setVisibility(status == 2 ? View.VISIBLE : View.GONE);


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
                    if (odds != null) {
                        ArrayList<String> keys = new ArrayList<>();
                        mTotal = 0;

                        for (int i = 0; i < odds.size(); i++) {
                            Odds _odd = odds.get(i);
                            String _key = _odd.getMatch_stage();
                            List<Odds> itemList = oddMap.get(_key);
                            if (itemList == null) {
                                itemList = new ArrayList<>();
                                keys.add(_key);
                            }
                            itemList.add(_odd);

                            oddMap.put(_key, itemList);
                        }

                        mTotal = keys.size();
                        initList(oddMap, keys);
                    }

                    if (mAdapter != null) {
                        mAdapter.changeData(oddMap, _data);
                        RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
                        jingcairecycleview.refreshComplete();
                    }
                } else {
                    RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
                    jingcairecycleview.refreshComplete();
                }

            }
        });
    }

    private void initList(Map<String, List<Odds>> oddMap, ArrayList<String> keys) {

        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new Adapter_MatchCell(getContext());
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
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.removeAllTabs();
        for (int i = 0; i < keys.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(keys.get(i)));
        }
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
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        Log.e("firstVisible", firstVisible + "");
        if (tabLayout != null) tabLayout.setScrollPosition(firstVisible - 1, 0, false);
    }

    private void startUpdate() {
        if (disposable != null) return;

        disposable = RxTimerUtilPro.interval(disTime, new RxTimerUtilPro.IRxNext() {
            @Override
            public void doNext(long number) {
                onRefresh();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void stopUpdate() {
        RxTimerUtilPro.cancel(disposable);
        disposable = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopUpdate();

        AppUtils.unregisterEvent(observer);
        observer = null;
    }

    private void playMatch() {
        if (_matchData == null) return;
        if (TextUtils.isEmpty(_matchData.getLive_url())) {
            ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, getResources().getString(R.string.nogamelooking), ToastUtils.LENGTH_SHORT);
            return;
        }
        System.out.println("url:" + _matchData.getLive_url());
//        String s3 = "http://v.yongjiujiexi.com/20180304/B0cYHQvY/index.m3u8";
//        videoplayer.setUp(s3, JZVideoPlayerStandard.NORMAL_ORIENTATION, _matchData.getTournament_name() + "/" + _matchData.getMatch_short_name());
        videoplayer.setUp(_matchData.getLive_url(), JZVideoPlayerStandard.NORMAL_ORIENTATION, _matchData.getTournament_name() + "/" + _matchData.getMatch_short_name());
//        videoplayer.fullscreenButton.setVisibility(View.GONE);
        videoplayer.progressBar.setVisibility(View.GONE);
        videoplayer.currentTimeTextView.setVisibility(View.GONE);
        videoplayer.totalTimeTextView.setVisibility(View.GONE);
        videoplayer.tinyBackImageView.setVisibility(View.GONE);
        videoplayer.batteryLevel.setVisibility(View.GONE);
        videoplayer.startButton.setVisibility(View.GONE);
        videoplayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        videoplayer.TOOL_BAR_EXIST = false;
        videoplayer.ACTION_BAR_EXIST = false;
        videoplayer.startVideo();

        //        videocontroller1.ivThumb.setThumbInCustomProject("视频/MP3缩略图地址");


        vidiolayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onPause() {
        super.onPause();
        videoplayer.releaseAllVideos();
    }

    @OnClick({R.id.medioclose, R.id.lookmatch, R.id.toolbar_back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.medioclose:
                videoplayer.releaseAllVideos();
                vidiolayout.setVisibility(View.GONE);
                break;
            case R.id.lookmatch:
                playMatch();
                break;
            case R.id.toolbar_back_iv:
                AppUtils.dispatchEvent(new ObData(EventType.DETIALHIDE, null));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUpdate();
        videoplayer.releaseAllVideos();
        vidiolayout.setVisibility(View.GONE);
    }
}
