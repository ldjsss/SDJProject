package com.lldj.tc.knowledge.SmallClass;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description: 微课推荐的<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */
public class SmallClassFragment extends BaseFragment {

    @BindView(R.id.subject_recycleview)
    RecyclerView subjectRecycleview;
    Unbinder unbinder;
    private SmallClassAdapter mAdapter;

    public static SmallClassFragment newInstance() {
        SmallClassFragment fragment = new SmallClassFragment();
        return fragment;
    }


    @Override
    public int getContentView() {
        return R.layout.knowledge_recycleview_layout;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        subjectRecycleview.setLayoutManager(manager);
        mAdapter = new SmallClassAdapter(mContext);
        subjectRecycleview.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
