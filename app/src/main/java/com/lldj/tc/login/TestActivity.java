package com.lldj.tc.login;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.lldj.tc.firstpage.subjectrecommend.GameCellAdapter;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;

import butterknife.ButterKnife;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/3 12:11<p>
 * Modify Time: 2018/12/3 12:11<p>
 */


public class TestActivity extends Activity  implements LRecyclerView.LScrollListener{
//    @BindView(R.id.subject_lrecycleview)
//    LRecyclerView subjectLrecycleview;
    private GameCellAdapter mAdapter;
    LRecyclerViewAdapter lAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subject_recommend);
        ButterKnife.bind(this);
//        subjectLrecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mAdapter = new GameCellAdapter(this);
//        lAdapter = new LRecyclerViewAdapter(this, mAdapter);
//        subjectLrecycleview.setAdapter(lAdapter);
//        subjectLrecycleview.setLScrollListener(this);
//        subjectLrecycleview.setPullRefreshEnabled(false);
////        subjectLrecycleview.scrollToPosition(0);
//        RecyclerViewStateUtils.setFooterViewState(this, subjectLrecycleview, 10, LoadingFooter.State.Loading, null);
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
//        RecyclerViewStateUtils.setFooterViewState(this, subjectLrecycleview, 10, LoadingFooter.State.Loading, null);
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {

    }
}
