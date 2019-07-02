package com.lldj.tc.match;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.MatchBean;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.mainUtil.GlobalVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * main ui
 */
public class Fragment_Main extends BaseFragment implements LRecyclerView.LScrollListener {

    private Adapter_MainCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ResultsModel[] alist; //展示数据列表
    private ArrayList<ResultsModel> mlist = new ArrayList<>(); //全部数据列表
    private int pageSize = 10;
    private int ViewType;
    private BaseFragment middleFragment;
    private Disposable disposable;
    private int disTime = 4000;
    private Observer<ObData> observer;

    @BindView(R.id.subject_lrecycleview)
    LRecyclerView subjectLrecycleview;
    @BindView(R.id.layout_board)
    FrameLayout layoutBoard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ViewType = getArguments().getInt("ARG");

        observer = new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.SELECTGROUPS)) {
                    if (mAdapter != null){
                        mAdapter.updateSelect((List<ObData>)data.getValue());
                    }
                }
            }
        };
        AppUtils.registEvent(observer);
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
            mAdapter = new Adapter_MainCell(mContext, ViewType);
            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
            subjectLrecycleview.setAdapter(lAdapter);
            subjectLrecycleview.setLScrollListener(this);

            if (ViewType >1) {
                middleFragment = new Fragment_Calendar();
            }
            else{
                middleFragment = new Fragment_Banner();
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
        getMatchData();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            Clog.e("onFragmentVisibleChange", "isVisible = " + ViewType);
            onRefresh();
            if(ViewType == 1) startUpdate();
        } else {
            Clog.e("onFragmentVisibleChange", "ishide = " + ViewType);
            stopUpdate();
        }
    }

    @Override
    public void onScrollUp() { }

    @Override
    public void onScrollDown() { }

    @Override
    public void onBottom() {
        Log.e("打印", "滚动到底部");
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(subjectLrecycleview);
        if (state == LoadingFooter.State.Loading) {
            return;
        }
        if (mlist.size() < alist.length) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.Loading, null);
            loadData();
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) { }

    public void loadData() {
        int mLen = mlist.size();
        int t = pageSize;
        if (mLen + t > alist.length) t = alist.length - mLen;
        for (int i = 0; i < t; i++) {
            mlist.add(alist[mLen + i]);
        }
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(subjectLrecycleview, LoadingFooter.State.Normal);
//        Log.e("数组长度", mlist.size() + "==");
        if (mlist.size() >= alist.length) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void selectView(int position) {
        super.onDestroyView();
//        Log.e("currentPosition", "selectView currentPosition===" + position);
    }

    private void getMatchData(){
        HttpMsg.getInstance().sendGetMatchList(ViewType + 1, MatchBean.class, new HttpMsg.Listener(){
            @Override
            public void onFinish(Object _res) {
                MatchBean res = (MatchBean) _res;
                if(res.getCode() == GlobalVariable.succ){
                    mlist.clear();
                    List<ResultsModel> _list = (List<ResultsModel>)res.getResult();

                    Collections.sort(_list, (Comparator<Object>) (o1, o2) -> {
                        return ((ResultsModel) o1).getStart_time().compareTo(((ResultsModel) o2).getStart_time());
                    });

                    alist = new ResultsModel[_list.size()];

                    for (int i = 0; i < _list.size(); i++) {
                        alist[i] = _list.get(i);
                    }

                    int t = alist.length > pageSize ? pageSize : alist.length;
                    for (int i = 0; i < t; i++) {
                        mlist.add(alist[i]);
                    }

                    mAdapter.changeData(mlist);
                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.Normal, null);
                    subjectLrecycleview.refreshComplete(); //刷新完成
                    Toast.makeText(mContext, getResources().getString(R.string.getGameListSucc),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startUpdate(){
        if (disposable != null) return;

        disposable = RxTimerUtilPro.interval(disTime, new RxTimerUtilPro.IRxNext() {
            @Override
            public void doNext(long number) {
                getMatchData();
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
    public void onDestroy() {
        super.onDestroy();
        AppUtils.unregisterEvent(observer);
        observer = null;
    }
}