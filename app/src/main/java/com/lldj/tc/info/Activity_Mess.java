package com.lldj.tc.info;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.BordBean;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.util.AppUtils.DEBUG;

public class Activity_Mess extends BaseActivity implements LRecyclerView.LScrollListener {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.jingcairecycleview)
    LRecyclerView jingcairecycleview;


    private Adapter_MessCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private int mTotal = 0;
    private LinearLayoutManager layoutManager;

    private List<BordBean.BordMode> messList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mess);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);

        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        ButterKnife.bind(this);

        ImmersionBar.with(mContext).titleBar(toolbarRootLayout).init();

        toolbarTitleTv.setText(mContext.getString(R.string.messtitle));

        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        mTotal = 3;
        for (int i = 0; i < mTotal; i++) {
            messList.add(new BordBean.BordMode("test" + i, "dfsfsfsdfdsfsdfsdfsfsdf", "121212"));
        }

        initRecycleview();
    }


    @Override
    public void onRefresh() {
//        HttpMsg.getInstance().sendGetMatchDetial(matchId, JsonBean.class, new HttpMsg.Listener() {
//            @Override
//            public void onFinish(Object _res) {
//                JsonBean res = (JsonBean) _res;
//                if (res.getCode() == GlobalVariable.succ) {
//
//                    if (mAdapter != null) {
////                        mAdapter.changeData(oddMap, _data, keys);
//                    }
//                }
//                RecyclerViewStateUtils.setFooterViewState(mContext, jingcairecycleview, mTotal, LoadingFooter.State.Normal, null);
//                jingcairecycleview.refreshComplete();
//            }
//        });
    }

    private void initRecycleview() {

        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            jingcairecycleview.setLayoutManager(layoutManager);
            mAdapter = new Adapter_MessCell(this);
            lAdapter = new LRecyclerViewAdapter(this, mAdapter);
            jingcairecycleview.setAdapter(lAdapter);
            jingcairecycleview.setLScrollListener(this);
            jingcairecycleview.setNoMore(true);
            lAdapter.setPullRefreshEnabled(false);
        }

        mAdapter.changeData(messList);
    }

    @Override
    public void onScrollUp() {
        if(DEBUG)Log.e("onScrollUp", "onScrollUp");
    }

    @Override
    public void onScrollDown() {
        if(DEBUG) Log.e("onScrollDown", "onScrollDown");
    }

    @Override
    public void onBottom() {
//        Log.e("打印", "滚动到底部");
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
    }

    @OnClick(R.id.toolbar_back_iv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                overridePendingTransition(0,R.anim.out_to_right);
                break;
        }
    }

}
