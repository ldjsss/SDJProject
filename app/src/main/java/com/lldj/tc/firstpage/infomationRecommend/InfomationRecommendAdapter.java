package com.lldj.tc.firstpage.infomationRecommend;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lldj.tc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 课程推荐的Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */


public class InfomationRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private InfoRecommendItemAdapter mAdapter;

    public InfomationRecommendAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.infomation_recommend_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder mHolder = (viewHolder) holder;
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mHolder.infoItemRecycleview.setLayoutManager(manager);
        mAdapter = new InfoRecommendItemAdapter(mContext);
        mHolder.infoItemRecycleview.setAdapter(mAdapter);
        if (position % 2 == 0) {
            mHolder.summaryInfoTv.setVisibility(View.GONE);
            mHolder.infoItemRecycleview.setVisibility(View.VISIBLE);
        } else {
            mHolder.summaryInfoTv.setVisibility(View.VISIBLE);
            mHolder.infoItemRecycleview.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.info_item_recycleview)
        RecyclerView infoItemRecycleview;
        @BindView(R.id.label_layout)
        LinearLayout labelLayout;
        @BindView(R.id.summary_info_tv)
        TextView summaryInfoTv;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
