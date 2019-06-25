package com.lldj.tc.firstpage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
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
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseActivity;
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
public class MatchDetailDialog extends Dialog implements LRecyclerView.LScrollListener{
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
    private int disTime = 4000;
    private Disposable disposable;

    public MatchDetailDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        //2、设置布局
        View view = View.inflate(context, R.layout.activity_match_detail, null);
        setContentView(view);

        ButterKnife.bind(this, view);

        Window window = this.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.RIGHT);
        window.setWindowAnimations(R.style.Anim_fade);  //设置弹出动画
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//设置对话框大小

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //核心代码是这个属性。
        window.setAttributes(layoutParams);

        if (statusText == null)statusText = new String[]{"", context.getString(R.string.matchStatusFront), context.getString(R.string.matchCurrentTitle), context.getString(R.string.matchStatusOver), context.getString(R.string.matchStatusError)};

        ImmersionBar.with((Activity)context).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(context.getString(R.string.matchDetialTitle));

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

    public void showView(int ViewType, int matchId){
        this.ViewType = ViewType;
        this.matchId  = matchId;

        onRefresh();

        show();
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
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
                        RecyclerViewStateUtils.setFooterViewState(getOwnerActivity(), jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
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
//        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(jingcairecycleview);
//        if (state == LoadingFooter.State.Loading) {
//            return;
//        }
//        if (mlist.size() < mTotal) {
//            RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Loading, null);
//        } else {
//            RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.TheEnd, null);
//        }

    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        Log.e("firstVisible", firstVisible + "");
       if(tabLayout != null) tabLayout.setScrollPosition(firstVisible - 1, 0 ,false);
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onViewClicked() {
       this.dismiss();
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
    public void dismiss() {
        super.dismiss();
        stopUpdate();
    }
}
