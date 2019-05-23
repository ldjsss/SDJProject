package com.lldj.tc.leye.drugInfo;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.lldj.tc.R;
import com.lldj.tc.firstpage.infomationRecommend.InfomationRecommendAdapter;
import com.lldj.tc.toolslibrary.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: 药品资讯<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */
public class DrugInfoFragment extends BaseFragment {

    @BindView(R.id.subject_recycleview)
    RecyclerView subjectRecycleview;
    Unbinder unbinder;
    private InfomationRecommendAdapter mAdapter;

    public static DrugInfoFragment newInstance() {
        DrugInfoFragment fragment = new DrugInfoFragment();
        return fragment;
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_infomation_recommend;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        subjectRecycleview.setLayoutManager(manager);
        mAdapter = new InfomationRecommendAdapter(mContext);
        subjectRecycleview.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
