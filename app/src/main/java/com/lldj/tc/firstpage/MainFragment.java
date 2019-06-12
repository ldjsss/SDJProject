package com.lldj.tc.firstpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lldj.tc.R;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * main ui
 */
public class MainFragment extends BaseFragment implements LRecyclerView.LScrollListener {

    private MainCellAdapter mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ArrayList<String> mlist = new ArrayList<>(); //展示数据列表
    private ArrayList<String> alist = new ArrayList<>(); //全部数据列表
    private int pageSize = 10;
    private int ViewType;
    BaseFragment middleFragment;

    @BindView(R.id.subject_lrecycleview)
    LRecyclerView subjectLrecycleview;
    @BindView(R.id.layout_board)
    FrameLayout layoutBoard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ViewType = getArguments().getInt("ARG");
//        Clog.e("sssssss", " -----------xcl onCreate = " + ViewType);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_subject_recommend;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);

        layoutBoard.setId( 1000 + ViewType); //为解决复用后id重复，动态添加控件时加跑偏

        if(lAdapter == null){
            subjectLrecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mAdapter = new MainCellAdapter(mContext, ViewType);
            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
            subjectLrecycleview.setAdapter(lAdapter);
            subjectLrecycleview.setLScrollListener(this);

            if (ViewType >1) {
                middleFragment = new CalendarFragment();
            }
            else{
                middleFragment = new BannerFragment();
            }

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add( 1000 + ViewType, middleFragment);
            transaction.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onRefresh() {//请求全部数据
        Clog.e("onRefresh", "onRefresh = " + ViewType);
        HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
        RxTimerUtil.timer(100, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                AppUtils.hideLoading();
                mlist.clear();
                alist.clear();

                for (int i = 0; i < 16; i++) {
                    alist.add("测试" + i);
                }

                int t = pageSize;
                if (alist.size() < t) t = alist.size();
                for (int i = 0; i < t; i++) {
                    mlist.add(alist.get(i));
                }

                mAdapter.changeData(mlist);
                RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.Normal, null);
                //刷新完成
                subjectLrecycleview.refreshComplete();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            Clog.e("onFragmentVisibleChange", "isVisible = " + ViewType);
                onRefresh();
        } else {
            Clog.e("onFragmentVisibleChange", "ishide = " + ViewType);
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
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(subjectLrecycleview);
        if (state == LoadingFooter.State.Loading) {
            return;
        }
        if (mlist.size() < alist.size()) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.Loading, null);
            loadData();
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) { }

    public void loadData() {

        RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                int mLen = mlist.size();
                int t = pageSize;
                if (mLen + t > alist.size()) t = alist.size() - mLen;
                for (int i = 0; i < t; i++) {
                    mlist.add(alist.get(mLen + i));
                }
                mAdapter.changeData(mlist);
                RecyclerViewStateUtils.setFooterViewState(subjectLrecycleview, LoadingFooter.State.Normal);
                Log.e("数组长度", mlist.size() + "==");
                if (mlist.size() >= alist.size()) {
                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
                }
            }

            @Override
            public void onComplete() {
            }
        });

    }

    @Override
    public void selectView(int position) {
        super.onDestroyView();
        Log.e("currentPosition", "selectView currentPosition===" + position);

    }

}
