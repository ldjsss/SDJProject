package com.lldj.tc.match;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.MatchBean;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.sharepre.SharePreUtils;
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

    @BindView(R.id.tv_noMatch)
    TextView tvNoMatch;
    @BindView(R.id.subject_lrecycleview)
    LRecyclerView subjectLrecycleview;
    @BindView(R.id.layout_board)
    FrameLayout layoutBoard;

    private Adapter_MainCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ArrayList<ResultsModel> alist= new ArrayList<>();
    private ArrayList<ResultsModel> mlist = new ArrayList<>();
    private int pageSize = 10;
    private int ViewType;
    private BaseFragment middleFragment;
    private Disposable disposable;
    private int disTime = 10000;
    private Observer<ObData> observer;
    public int selectID = 0;
    private List<ResultsModel> gameList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ViewType = getArguments().getInt("ARG");

        observer = new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                String _key = data.getKey();
                if (_key.equalsIgnoreCase(EventType.SELECTGROUPS)) {
                    if (mAdapter != null) {
                        mAdapter.updateSelect((List<ObData>) data.getValue());
                    }
                } else if (_key.equalsIgnoreCase(EventType.SELECTGAMEID) || _key.equalsIgnoreCase(EventType.DETIALHIDE)) {
                    if(_key.equalsIgnoreCase(EventType.SELECTGAMEID)) gameList = (List<ResultsModel>)data.getValue();
                    onRefresh();
                }
                else if (_key.equalsIgnoreCase(EventType.BETDETAILUI)) {
                    stopUpdate();
                }
            }
        };
        AppUtils.registEvent(observer);

//        Log.e(AppUtils.getScreenWidth(getContext()) + "--------", AppUtils.getScreenHeight(getContext()) + "------");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_subject_recommend;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);

        layoutBoard.setId(1000 + ViewType); //In order to solve id duplication after reuse, add deviation when adding control dynamically

        if (lAdapter == null) {
            subjectLrecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mAdapter = new Adapter_MainCell(mContext, ViewType);
            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
            subjectLrecycleview.setAdapter(lAdapter);
            subjectLrecycleview.setLScrollListener(this);

            if (ViewType > 1) {
                middleFragment = new Fragment_Calendar();
            } else {
                middleFragment = new Fragment_Banner();
            }

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(1000 + ViewType, middleFragment);
            transaction.commit();
        }
    }

    @Override
    public void onRefresh() {
        Clog.e("onRefresh", "onRefresh = " + ViewType);
        HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
        getMatchData();

        if (ViewType <= 1) startUpdate();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
//            Clog.e("onFragmentVisibleChange", "isVisible = " + ViewType);
            onRefresh();
        } else {
//            Clog.e("onFragmentVisibleChange", "ishide = " + ViewType);
            stopUpdate();
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
        if (mlist.size() < alist.size()) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.Loading, null);
            loadData();
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
    }

    public void loadData() {
        int mLen = mlist.size();
        int t = pageSize;
        if (mLen + t > alist.size()) t = alist.size() - mLen;
        for (int i = 0; i < t; i++) {
            mlist.add(alist.get(mLen + i));
        }
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(subjectLrecycleview, LoadingFooter.State.Normal);
        if (mlist.size() >= alist.size()) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, pageSize, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void selectView(int position) {
        super.onDestroyView();
//        Log.e("currentPosition", "selectView currentPosition===" + position);
    }

    private void getMatchData() {
        HttpMsg.getInstance().sendGetMatchList(ViewType + 1, MatchBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                MatchBean res = (MatchBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    mlist.clear();
                    List<ResultsModel> _list = (List<ResultsModel>) res.getResult();

                    selectID = SharePreUtils.getInstance().getSelectGame(getContext());
                    if (selectID != 0) {
                        for (int i = _list.size() - 1; i >= 0; i--) {
                            if (_list.get(i).getGame_id() != selectID) _list.remove(i);
                        }
                    }

                    Collections.sort(_list, (Comparator<ResultsModel>) (o1, o2) -> {
                        return (int)(o1.getStart_time_ms() - o2.getStart_time_ms());
                    });

                    alist.clear();
                    alist.addAll(_list);

                    int t = alist.size() > pageSize ? pageSize : alist.size();
                    for (int i = 0; i < t; i++) {
                        mlist.add(alist.get(i));
                    }

                    mAdapter.changeData(mlist);
                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.Normal, null);
                    subjectLrecycleview.refreshComplete();
                }

                tvNoMatch.setVisibility(mlist.size()>0?View.GONE:View.VISIBLE);
                if(tvNoMatch.getVisibility() == View.VISIBLE && gameList != null) {
                    int _select = SharePreUtils.getInstance().getSelectGame(getContext());
                    for (int i = 0; i < gameList.size(); i++) {
                        if(gameList.get(i).getId() == _select){
                            tvNoMatch.setText(String.format(getString(R.string.noGames), gameList.get(i).getName()));
                        }
                    }
                }

                ObData _data = new ObData(EventType.MATCHCOUNT, mlist.size());
                _data.setTag(ViewType + "");
                AppUtils.dispatchEvent(_data);
            }
        });
    }

    private void startUpdate() {
        if (disposable != null) return;

        disposable = RxTimerUtilPro.interval(disTime, new RxTimerUtilPro.IRxNext() {
            @Override
            public void doNext(long number) {
                getMatchData();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void stopUpdate() {
        RxTimerUtilPro.cancel(disposable);
        disposable = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppUtils.unregisterEvent(observer);
        observer = null;
        stopUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopUpdate();
    }

}
