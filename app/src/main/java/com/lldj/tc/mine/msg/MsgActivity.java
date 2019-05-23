package com.lldj.tc.mine.msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
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
 * description: 消息页面<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/6 9:19<p>
 * Modify Time: 2018/12/6 9:19<p>
 */


public class MsgActivity extends BaseActivity implements LRecyclerView.LScrollListener {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.normal_lrecycleview)
    LRecyclerView normalLrecycleview;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    private MsgAdapter mAdapter;
    LRecyclerViewAdapter lAdapter;
    private ArrayList<MsgBean> mlist = new ArrayList<>();
    private int mTotal = 30;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, MsgActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);


    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResourcesString(R.string.system_notify));
        toolbarRightTv.setText(getResourcesString(R.string.all_read));
        normalLrecycleview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MsgAdapter(mContext);
        lAdapter = new LRecyclerViewAdapter(mContext, mAdapter);
        normalLrecycleview.setAdapter(lAdapter);
        normalLrecycleview.setLScrollListener(this);
        setFirstData();
    }

    private void setFirstData() {
        for (int i = 0; i < 10; i++) {
            MsgBean msgBean = new MsgBean();
            if (i % 2 == 0) {
                msgBean.setRead(true);
            }
            mlist.add(msgBean);

        }
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(mContext, normalLrecycleview, 10, LoadingFooter.State.Normal, null);
    }

    @OnClick({R.id.toolbar_back_iv, R.id.toolbar_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.toolbar_right_tv:
                for (int i = 0; i < mlist.size(); i++) {
                    mlist.get(i).setRead(true);
                }
                mAdapter.changeData(mlist);
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);

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
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(normalLrecycleview);
        if (state == LoadingFooter.State.Loading) {
            return;
        }
        if (mlist.size() < mTotal) {
            RecyclerViewStateUtils.setFooterViewState(mContext, normalLrecycleview, 10, LoadingFooter.State.Loading, null);
            loadData(false);
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, normalLrecycleview, 10, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {

    }

    public void loadData(final boolean pIsRefresh) {
        RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                if (pIsRefresh) {
                    mlist.clear();
                }
                for (int i = 0; i < 10; i++) {
                    MsgBean msgBean = new MsgBean();
                    if (i % 2 == 0) {
                        msgBean.setRead(true);
                    }
                    mlist.add(msgBean);
                }
                mAdapter.changeData(mlist);
                if (pIsRefresh) {

                    normalLrecycleview.refreshComplete();
                } else {
                    RecyclerViewStateUtils.setFooterViewState(normalLrecycleview, LoadingFooter.State.Normal);
                }
                if (mlist.size() >= mTotal) {
                    RecyclerViewStateUtils.setFooterViewState(mContext, normalLrecycleview, 10, LoadingFooter.State.TheEnd, null);
                }
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
