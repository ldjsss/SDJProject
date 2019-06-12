package com.lldj.tc.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.BetActivity;
import com.lldj.tc.MainUIActivity;
import com.lldj.tc.R;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginRecycleView extends BaseActivity implements HandlerInter.HandleMsgListener, LRecyclerView.LScrollListener {
    @BindView(R.id.loginrecycleview)
    LRecyclerView loginrecycleview;

    LinearLayoutManager layoutManager;
    @BindView(R.id.videoView)
    VideoView videoView;
    private LoginCellAdapter mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ArrayList<String> mlist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginrecycleview);
        ButterKnife.bind(this);

        mHandler.setHandleMsgListener(this);

        String path = "android.resource://" + mContext.getPackageName() + "/" + R.raw.main_movie;

        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );


    }

    @Override
    public void handleMsg(Message msg) {
        if (mAdapter != null) mAdapter.handleMsg(msg);
        switch (msg.what) {
            case HandlerType.GOTOMAIN:
                startActivity(new Intent(this, MainUIActivity.class));
//                startActivity(new Intent(this, BetActivity.class));
                finish();
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                break;
            case HandlerType.LOADING:
                AppUtils.showLoading(mContext);
                break;
        }
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        loginrecycleview.setLayoutManager(layoutManager);
        mAdapter = new LoginCellAdapter(mContext, getSupportFragmentManager());
        lAdapter = new LRecyclerViewAdapter(this, mAdapter);
        loginrecycleview.setAdapter(lAdapter);
        loginrecycleview.setLScrollListener(this);
        loginrecycleview.setPullRefreshEnabled(false);
        setFirstData();
    }

    private void setFirstData() {
        mlist.add("测试");
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(mContext, loginrecycleview, 10, LoadingFooter.State.Normal, null);
    }

    @Override
    public void onRefresh() {
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
