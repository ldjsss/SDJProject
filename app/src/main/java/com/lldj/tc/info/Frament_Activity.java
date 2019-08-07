package com.lldj.tc.info;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.ActivityBean;
import com.lldj.tc.http.beans.TaskBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frament_Activity extends BaseFragment implements LRecyclerView.LScrollListener {
    @BindView(R.id.subject_lrecycleview)
    LRecyclerView subjectLrecycleview;
    @BindView(R.id.layout_board)
    FrameLayout layoutBoard;

    private Adapter_ActivityCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private int ViewType = 0;
    private int pageSize = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ViewType = getArguments().getInt("ARG");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_subject_recommend;
    }

    @Override
    public void initView(View rootView) {

        ButterKnife.bind(this, rootView);

        if (lAdapter == null) {
            subjectLrecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mAdapter = new Adapter_ActivityCell(mContext, this.ViewType);
            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
            subjectLrecycleview.setAdapter(lAdapter);
            subjectLrecycleview.setLScrollListener(this);
        }

        layoutBoard.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
        getMatchData();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
//            Clog.e("onFragmentVisibleChange", "isVisible = " + ViewType);
            onRefresh();
        } else {
//            Clog.e("onFragmentVisibleChange", "ishide = " + ViewType);
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
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
    }

    @Override
    public void selectView(int position) {
        super.onDestroyView();
//        Log.e("currentPosition", "selectView currentPosition===" + position);
    }

    private void getMatchData() {

        if(this.ViewType == 1) {
            HttpMsg.getInstance().sendTastList(SharePreUtils.getToken(getContext()), TaskBean.class, new HttpMsg.Listener() {
                @Override
                public void onFinish(Object _res) {
                    TaskBean res = (TaskBean) _res;
                    if (res.getCode() == GlobalVariable.succ) {
                        mAdapter.changeData(res.getResult());
                    }

                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.Normal, null);
                    subjectLrecycleview.refreshComplete();
                }
            });
        }
        else{
            HttpMsg.getInstance().sendGetActivity(SharePreUtils.getToken(getContext()), ActivityBean.class, new HttpMsg.Listener() {
                @Override
                public void onFinish(Object _res) {
                    ActivityBean res = (ActivityBean) _res;
                    if (res.getCode() == GlobalVariable.succ) {
                        mAdapter.changeData1(res.getResult());
                    }

                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.Normal, null);
                    subjectLrecycleview.refreshComplete();
                }
            });
        }
    }

}
