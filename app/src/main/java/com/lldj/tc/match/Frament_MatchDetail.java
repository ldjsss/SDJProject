package com.lldj.tc.match;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.http.beans.FormatModel.matchModel.Team;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.time.BasicTimer;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.ImageLoader;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.util.StringUtil;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.disposables.Disposable;

import static com.lldj.tc.toolslibrary.util.AppUtils.DEBUG;
import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Frament_MatchDetail extends BaseFragment implements LRecyclerView.LScrollListener {
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
    LinearLayout gamestatus;
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
    ImageView arrowplay;
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    @BindView(R.id.tvlayout)
    LinearLayout tvlayout;
    @BindView(R.id.imgvidoicon1)
    ImageView imgvidoicon1;
    @BindView(R.id.playvidoname0)
    TextView playvidoname0;
    @BindView(R.id.vidowin0)
    TextView vidowin0;
    @BindView(R.id.vidowin1)
    TextView vidowin1;
    @BindView(R.id.imgvidoicon2)
    ImageView imgvidoicon2;
    @BindView(R.id.playvidoname2)
    TextView playvidoname2;
    @BindView(R.id.gamecountheng)
    TextView gamecountheng;
    @BindView(R.id.bottomstatus)
    TextView bottomstatus;

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
    private BasicTimer disposable;
    private boolean select = false;
    private Map<String, String> mapNames = SharePreUtils.getInstance().getMapNames();
    private ImageLoader imageLoader = new ImageLoader(bActivity, R.mipmap.game_arena, R.mipmap.game_arena);

    @Override
    public int getContentView() {
        return R.layout.frament_match_detail;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppUtils.fullScreenImmersive(mContext.getWindow().getDecorView());
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        } else {
//            mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
//    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);

        if (statusText == null) statusText = new String[]{"unknown", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.gameing), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};

        ImmersionBar.with((Activity) mContext).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mContext.getString(R.string.matchDetialTitle));

        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.SELECTGROUPS)) {
                    if (mAdapter != null) {
                        mAdapter.updateSelect((List<ObData>) data.getValue());
                    }
                }
            }
        });

        matchtime.setText("");
        gamestatus.setVisibility(View.GONE);
        looklayout.setVisibility(View.GONE);
        gamecountheng.setVisibility(View.GONE);
        gamestatus1.setVisibility(View.GONE);

        initRecycleview();
    }

    public void showView(int ViewType, int matchId) {
        this.ViewType = ViewType;
        this.matchId = matchId;

        onRefresh();

    }

    @Override
    public void onRefresh() {

        final HttpMsg.Listener cal = new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    _matchData = res.getResult();

                    ResultsModel _data = _matchData;
                    if (_data == null || _data.getTeam().size() < 2) {
                        Toast.makeText(mContext, "services data error !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<Team> teams = _data.getTeam();
                    Collections.sort(teams, (o1, o2) -> { return (int)(o1.getPos() - o2.getPos()); });

                    Team team0 = teams.get(0);
                    Team team1 = teams.get(1);
                    List<Odds> odds = _data.getOdds() != null ? _data.getOdds() : null;
                    int status = _data.getStatus();

                    gamename.setText(_data.getTournament_name());
                    gamenamecount.setText(String.format("/ %s", StringUtil.convertUp(_data.getRound())));
                    gameplaycount.setText(String.format("+%s", _data.getPlay_count()));
                    playnamecommon0.setText(team0.getTeam_short_name());
                    playnamecommon1.setText(team1.getTeam_short_name());
                    playvidoname0.setText(team0.getTeam_short_name());
                    playvidoname2.setText(team1.getTeam_short_name());
                    bottomstatus.setText(statusText[status]);
                    matchtime.setText((status == 2) ? statusText[status] : String.format("%s %s", AppUtils.getFormatTime2(_data.getStart_time_ms()), AppUtils.getWhatDay(_data.getStart_time_ms())));
                    if (status == 2) startUpdate();else stopUpdate();

                    gamestatus1.setText(AppUtils.getFormatTime4(_data.getStart_time_ms()));

                    int oneWin = team0.getScore() == null ? 0 : team0.getScore().getTotal();
                    int twoWin = team1.getScore() == null ? 0 : team1.getScore().getTotal();
                    gamecountheng.setText(String.format("%s - %s", oneWin, twoWin));
                    vidowin0.setText(String.valueOf(oneWin));
                    vidowin1.setText(String.valueOf(twoWin));

                    gamestatus1.setVisibility((status == 2 || status == 3) ? View.GONE : View.VISIBLE);

                    gamestatus.setVisibility((status == 2) ? View.GONE : View.VISIBLE);
                    gamecountheng.setVisibility((status == 2 || status == 3) ? View.VISIBLE : View.GONE);
                    looklayout.setVisibility(status == 2 ? View.VISIBLE : View.GONE);

                    imageLoader.getAndSetImage(_data.getGame_logo(), gameicon);

                    imageLoader.getAndSetImage(team0.getTeam_logo(), imgplayicon0);
                    imageLoader.getAndSetImage(team0.getTeam_logo(), imgvidoicon1);

                    imageLoader.getAndSetImage(team1.getTeam_logo(), imgplayicon1);
                    imageLoader.getAndSetImage(team1.getTeam_logo(), imgvidoicon2);

//                    Log.w("-----detail data = ", _data.toString());

                    Map<String, Map<String, List<Odds>>> oddMap = new HashMap<>();
                    List<String> keys = new ArrayList<>();
                    if (odds != null) {
//                        Collections.sort(odds, (o1, o2) -> {
//                            return (int)(o2.getSort_index() - o1.getSort_index());
//                        });
                        mTotal = 0;
                        String _match_stage = "";
                        for (int i = 0; i < odds.size(); i++) {
                            Odds _odd = odds.get(i);
                            String _key = _odd.getMatch_stage();
                            if (!_key.equalsIgnoreCase(_match_stage)) {
                                keys.add("-1");
                                _match_stage = _key;
                            }

                            Map<String, List<Odds>> itemList = oddMap.get(_key);
                            if (itemList == null) {
                                itemList = new HashMap<>();
                                oddMap.put(_key, itemList);
                                keys.add(_key);
                            }
                            String _nextKey = _odd.getGroup_name();
                            List<Odds> ceilList = itemList.get(_nextKey);
                            if (ceilList == null) {
                                ceilList = new ArrayList<>();
                                itemList.put(_nextKey, ceilList);
                            }
                            ceilList.add(_odd);
                        }

                        mTotal = keys.size();
                        if (tabLayout != null && tabLayout.getTabCount() != keys.size() / 2) {
                            tabLayout.removeAllTabs();

                            String str, _title;
                            for (int i = 0; i < keys.size(); i++) {
                                str = keys.get(i);
                                if (!str.equalsIgnoreCase("-1")) {
                                    _title = mapNames.get(str);
                                    tabLayout.addTab(tabLayout.newTab().setText(TextUtils.isEmpty(_title) ? str : _title));
                                }
                            }
                            AppUtils.reduceMarginsInTabs(tabLayout, 10);
                        }
                    }

                    if (mAdapter != null) {
                        mAdapter.changeData(oddMap, _data, keys);
                    }
                }
                RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
                jingcairecycleview.refreshComplete();
            }
        };

        HttpMsg.getInstance().sendGetMatchDetial(matchId, JsonBean.class, cal);
    }

    private void initRecycleview() {

        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new Adapter_MatchCell(getContext());
            lAdapter = new LRecyclerViewAdapter(getContext(), mAdapter);
            jingcairecycleview.setAdapter(lAdapter);
            jingcairecycleview.setLScrollListener(this);
            jingcairecycleview.setNoMore(true);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabPosition = tab.getPosition();
                    if (DEBUG) Log.e("onTabSelected", String.valueOf(tabPosition));
                    select = true;
                    if (tabPosition == 0) layoutManager.scrollToPosition(0);
                    else layoutManager.scrollToPosition(tabPosition * 2 + 1);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }
    }

    @Override
    public void onScrollUp() {
        if (DEBUG) Log.e("onScrollUp", "onScrollUp");
    }

    @Override
    public void onScrollDown() {
        if (DEBUG) Log.e("onScrollDown", "onScrollDown");
    }

    @Override
    public void onBottom() {
//        Log.e("打印", "滚动到底部");
        if (tabLayout != null && select == false) tabLayout.setScrollPosition(mTotal - 1, 0, false);
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
        int pos = layoutManager.findFirstVisibleItemPosition();
        if (DEBUG) Log.e("pos" + pos, "distanceY = " + distanceY);
        if (distanceY <= 0) pos = 0;
        else if (tabLayout != null && select == false)
            tabLayout.setScrollPosition((pos) / 2, 0, false);
        select = false;
    }

    private void startUpdate() {
        if (disposable != null) return;

        disposable = new BasicTimer(new BasicTimer.BasicTimerCallback() {
            @Override
            public void onTimer() {
                onRefresh();
            }
        });
        disposable.start(disTime);
    }

    private void stopUpdate() {
        if(disposable != null){
            disposable.cancel();
            disposable = null;
        }
    }

    private void playMatch() {
        if (_matchData == null) return;
        if (TextUtils.isEmpty(_matchData.getLive_url())) {
            ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, getResources().getString(R.string.nogamelooking), ToastUtils.LENGTH_SHORT);
            return;
        }
        if(DEBUG)System.out.println("url:" + _matchData.getLive_url());
        String s3 = "http://v.yongjiujiexi.com/20180304/B0cYHQvY/index.m3u8";
        videoplayer.setUp(s3, JZVideoPlayerStandard.NORMAL_ORIENTATION, _matchData.getTournament_name() + "/" + _matchData.getMatch_short_name());
//        videoplayer.setUp(_matchData.getLive_url(), JZVideoPlayerStandard.NORMAL_ORIENTATION, String.format("%s/%s", _matchData.getTournament_name(), _matchData.getMatch_short_name()));
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


        tvlayout.setVisibility(View.VISIBLE);
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
                tvlayout.setVisibility(View.GONE);
                break;
            case R.id.lookmatch:
                playMatch();
                break;
            case R.id.toolbar_back_iv:
                close();
                AppUtils.dispatchEvent(new ObData(EventType.DETIALHIDE, null));
                break;
        }
    }

    private void close() {
        stopUpdate();
        videoplayer.releaseAllVideos();
        tvlayout.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close();
    }
}
