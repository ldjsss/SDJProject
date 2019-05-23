package com.lldj.tc.mine.info;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * description: 职称选择<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/7 11:20<p>
 * Modify Time: 2018/12/7 11:20<p>
 */


public class JobTitleFragement extends BaseLazyFragment implements JobTitleAdapter.OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.job_title_recycleview)
    RecyclerView jobTitleRecycleview;
    @BindView(R.id.ensure_tv)
    TextView ensureTv;
    @BindView(R.id.caree_tip_close_iv)
    ImageView careeTipCloseIv;
    private JobTitleAdapter mAdapter;
    private ArrayList<JobTitleBean> mDataList;
    private int mSelectPositon;

    @Override

    protected void lazyLoad() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragement_job_title;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        //一级科室
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        jobTitleRecycleview.setLayoutManager(linearLayoutManager);
        mAdapter = new JobTitleAdapter(mContext);
        jobTitleRecycleview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        setData();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.caree_tip_close_iv, R.id.ensure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.caree_tip_close_iv:
                ((InfoDetailActivity) getActivity()).setJobTitle();
                break;
            case R.id.ensure_tv:
                SharePreUtils.setJobTitle(mContext, mDataList.get(mSelectPositon).getJobtitle());
                ((InfoDetailActivity) getActivity()).setJobTitle();
                break;
        }
    }

    public void setData() {
        mDataList = new ArrayList<>();
        mDataList.add(new JobTitleBean("主任医师"));
        mDataList.add(new JobTitleBean("副主任医师"));
        mDataList.add(new JobTitleBean("主治医师"));
        mDataList.add(new JobTitleBean("住院医师"));
        mAdapter.changeData(mDataList);
    }

    @Override
    public void onItemClickListener(int position) {
        mSelectPositon = position;
        for (int i = 0; i < mDataList.size(); i++) {
            if (i == position) {
                mDataList.get(i).setSelect(true);
            } else {
                mDataList.get(i).setSelect(false);
            }
        }
        mAdapter.changeData(mDataList);

    }
}
