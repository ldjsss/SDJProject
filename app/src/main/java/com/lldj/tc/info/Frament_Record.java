package com.lldj.tc.info;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.RecordModel;
import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.http.beans.RecordBean;
import com.lldj.tc.match.Adapter_BetResultCell;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frament_Record extends BaseFragment implements LRecyclerView.LScrollListener {
    @BindView(R.id.subject_lrecycleview)
    LRecyclerView subjectLrecycleview;
    @BindView(R.id.layout_board)
    FrameLayout layoutBoard;

    private Adapter_BetResultCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private int ViewType = 0;

    private ArrayList<BetModel> alist = new ArrayList<>();
    private int pageSize = 10;
    private int curPage = 1;
    private int totalPage = 1;
    private int total = 1;

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
            mAdapter = new Adapter_BetResultCell(mContext, alist);
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
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(subjectLrecycleview);
        if (state == LoadingFooter.State.Loading) {
            return;
        }
        if (curPage < totalPage) {
            curPage++;
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.Loading, null);
            getMatchData();
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
        }
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
        HttpMsg.getInstance().sendBetRecords(SharePreUtils.getToken(getContext()), curPage + "", ViewType + "", RecordBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                RecordBean res = (RecordBean) _res;
                if (res.getCode() == GlobalVariable.succ) {

                    RecordModel _ret = (RecordModel) res.getResult();
                    List<BetModel> _list = (List<BetModel>) _ret.getRecords();
                    pageSize = _ret.getPage_size();
                    curPage = _ret.getPage_num();
                    totalPage = _ret.getPages();

                    alist.addAll(_list);

                    mAdapter.changeData(alist);
                }

                RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.Normal, null);
                subjectLrecycleview.refreshComplete();
            }
        });
    }

}
