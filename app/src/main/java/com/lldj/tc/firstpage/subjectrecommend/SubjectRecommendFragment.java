package com.lldj.tc.firstpage.subjectrecommend;

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
import com.lldj.tc.firstpage.BannerFragment;
import com.lldj.tc.firstpage.CalendarFragment;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.Clog;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: <p>
 */
public class SubjectRecommendFragment extends BaseFragment implements LRecyclerView.LScrollListener {

    private GameCellAdapter mAdapter = null;
    private LRecyclerViewAdapter lAdapter = null;
    private ArrayList<String> mlist = new ArrayList<>();
    private int mTotal = 30;
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

        //第一次加载数据
        if(lAdapter == null){
            subjectLrecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            mAdapter = new GameCellAdapter(mContext, ViewType);
            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
            subjectLrecycleview.setAdapter(lAdapter);
            subjectLrecycleview.setLScrollListener(this);

            setFirstData();

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

    private void setFirstData() {
        for (int i = 0; i < 10; i++) {
            mlist.add("测试");
        }
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.Normal, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onRefresh() {
        Clog.e("sssssss", " -----------xcl onRefresh = ");
        mlist.clear();
        setFirstData();
        RxTimerUtil.timer(500, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                //刷新完成
                subjectLrecycleview.refreshComplete();
            }

            @Override
            public void onComplete() {
            }
        });
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
        if (mlist.size() < mTotal) {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.Loading, null);
            loadData();
        } else {
            RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.TheEnd, null);
        }
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
    }

    public void loadData() {
        RxTimerUtil.timer(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                for (int i = 0; i < 10; i++) {
                    mlist.add("测试");
                }
                mAdapter.changeData(mlist);
                RecyclerViewStateUtils.setFooterViewState(subjectLrecycleview, LoadingFooter.State.Normal);
                Log.e("数组长度", mlist.size() + "==");
                if (mlist.size() >= mTotal) {
                    RecyclerViewStateUtils.setFooterViewState(mContext, subjectLrecycleview, 10, LoadingFooter.State.TheEnd, null);
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
        Log.e("currentPosition", "--zzzzzz----- selectView currentPosition===" + position);
//        ViewType = position;

//        if(mAdapter == null){
//            subjectLrecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//            mAdapter = new GameCellAdapter(mContext, ViewType);
//            lAdapter = new LRecyclerViewAdapter(getActivity(), mAdapter);
//            subjectLrecycleview.setAdapter(lAdapter);
//            subjectLrecycleview.setLScrollListener(this);

        //第一次加载数据
//            setFirstData();

//            Log.e("ttttttttttt","--zzzz==="+position);
//            BaseFragment fragment;
//            if(position <= 1){
//                fragment = new BannerFragment();
//                Log.e("ttttttttttt","--11111111==="+position);
//            }else{
//                fragment = new CalendarFragment();
//                Log.e("ttttttttttt","--2222222==="+position);
//            }

//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.layout_board, fragment);
//            transaction.commit();
//
//        }

    }
}
