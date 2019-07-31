package com.lldj.tc.match;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.MatchBean;
import com.lldj.tc.http.beans.PageMatchBean;
import com.lldj.tc.toolslibrary.view.ToastUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Adapter_MainCell mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ArrayList<ResultsModel> alist= new ArrayList<>();
    public int page_size = 10;
    public int page_num = 1;
    public int pages = 0;
    public int total = 0;
    private int ViewType;
    private Fragment_Banner fragment_Banner;
    private Fragment_Calendar fragment_Calendar;
    private Disposable disposable;
    private int disTime = 10000;
    private String selects = "";
    private boolean _visible = false;
    private LinearLayoutManager layoutManager;

    public static Map<String, ObData> selectGroups = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ViewType = getArguments().getInt("ARG");

        registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                String _key = data.getKey();
                if (_key.equalsIgnoreCase(EventType.SELECTGROUPS)) {
                    if (mAdapter != null) {
                        mAdapter.updateSelect((List<ObData>) data.getValue());
                    }
                } else if ( _key.equalsIgnoreCase(EventType.DETIALHIDE)) {
                    startUpdate();
                }
                else if (_key.equalsIgnoreCase(EventType.SELECTGAMEID)) {
                    alist.clear();
                    page_num = 1;
                    selects = SharePreUtils.getInstance().getSelectGame(getContext());
                    onRefresh();
                }
                else if (_key.equalsIgnoreCase(EventType.BETDETAILUI)) {
                    stopUpdate();
                }
            }
        });

        selects = SharePreUtils.getInstance().getSelectGame(getContext());

//        Log.e(AppUtils.getScreenWidth(getContext()) + "--------", AppUtils.getScreenHeight(getContext()) + "------");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_subject_recommend;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);

        rootView.findViewById(R.id.layout_board).setId(1000 + ViewType); //In order to solve id duplication after reuse, add deviation when adding control dynamically

        if (lAdapter == null) {
            layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            subjectLrecycleview.setLayoutManager(layoutManager);
            mAdapter = new Adapter_MainCell(mContext, ViewType);
            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
            subjectLrecycleview.setAdapter(lAdapter);
            subjectLrecycleview.setLScrollListener(this);

            if (ViewType > 1) {
                fragment_Calendar = new Fragment_Calendar();
            } else {
                fragment_Banner = new Fragment_Banner();
                Bundle bundle = new Bundle();
                bundle.putInt("ARG", ViewType);
                fragment_Banner.setArguments(bundle);
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(1000 + ViewType, fragment_Calendar != null ? fragment_Calendar : fragment_Banner);
            transaction.commit();
        }

    }

    @Override
    public void onRefresh() {
        Clog.e("onRefresh", "onRefresh = " + ViewType);
        HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
        getMatchData();

        startUpdate();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        this._visible = isVisible;
       if(fragment_Banner != null){
           if(isVisible )fragment_Banner.startViewAnimator();
           else fragment_Banner.stopViewAnimator();
       }
        if (isVisible) {
            Clog.e("onFragmentVisibleChange", "isVisible = " + ViewType);
            onRefresh();
        } else {
            Clog.e("onFragmentVisibleChange", "ishide = " + ViewType);
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
    public void onScrolled(int distanceX, int distanceY) {
        int pos = layoutManager.findFirstVisibleItemPosition();
        page_num = (int)Math.ceil(pos/(page_size*1.0));
        if(page_num <= 0) page_num = 1;
        Log.e("page_num", page_num + "  page_num = " + page_num) ;

    }

    @Override
    public void onBottom() {
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(subjectLrecycleview);
        if (state == LoadingFooter.State.Loading) {
            return;
        }
        if (page_num < pages) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, page_size, LoadingFooter.State.Loading, null);
            page_num ++;
            getMatchData();
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, page_size, LoadingFooter.State.TheEnd, null);
        }
    }

    private void getMatchData() {//"&game_ids=" + selectID
        HttpMsg.getInstance().sendGetMatchList(ViewType + 1, page_num, selects, PageMatchBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                PageMatchBean res = (PageMatchBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    PageMatchBean.matchModel _result = res.getResult();
                    if(_result == null) {
                        Toast.makeText(mContext, "--------service data error ", Toast.LENGTH_SHORT).show();
                        page_size = 10;
                        pages = 0;
                        total = 0;
                    }
                    else {
                        page_size = _result.getPage_size();
                        pages = _result.getPages();
                        total = _result.getTotal();
                    }

                    if(page_num <= 1) {
                        alist.clear();
                    }
                    else{
                        for (int i = alist.size() - 1; i > 0; i--) {
//                            Clog.e("-------i = " + i, "alist size = " + alist.size());
                            if (alist.get(i).getPage_num() == page_num) alist.remove(i);
                        }
                    }

                    if(_result != null && _result.getDatas() != null){
                        List<ResultsModel> _datas = _result.getDatas();
                        for (int i = 0; i < _datas.size(); i++) {
                            _datas.get(i).setPage_num(page_num);
                            alist.add(_datas.get(i));
                        }
                    }

                    Collections.sort(alist, (o1, o2) -> {
                        return (int)(o1.getStart_time_ms() - o2.getStart_time_ms());
                    });

                    tvNoMatch.setVisibility(alist.size()>0?View.GONE:View.VISIBLE);

                    mAdapter.changeData(alist, total);
                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, page_size, LoadingFooter.State.Normal, null);
                }

                AppUtils.dispatchEvent(new ObData(EventType.MATCHCOUNT, total, ViewType + ""));

                subjectLrecycleview.refreshComplete();
            }
        });
    }

    private void startUpdate() {
        if (disposable != null || ViewType > 1 || !_visible) return;

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
        stopUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        Clog.e("onPause", "onPause = " + ViewType);
        stopUpdate();

        if(fragment_Banner != null){
          fragment_Banner.stopViewAnimator();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Clog.e("onResume", "onResume = " + ViewType);
        startUpdate();

        if(fragment_Banner != null && _visible){
            fragment_Banner.startViewAnimator();
        }
    }


}
