package com.lldj.tc.mine.info;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lldj.tc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 职称Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */


public class JobTitleAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<JobTitleBean> mDataList = new ArrayList<>();


    public JobTitleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<JobTitleBean> plist) {
        mDataList = plist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragemet_jobtitle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        viewHolder mHolder = (viewHolder) holder;
        JobTitleBean mItem = mDataList.get(position);
        mHolder.jobTitleTv.setText(mItem.getJobtitle());
        if (mItem.isSelect()) {
            mHolder.jobTitleTv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            mHolder.jobTitleTv.setTextColor(mContext.getResources().getColor(R.color.color_969696));
        }

        mHolder.jobTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mClickListener) {
                    mClickListener.onItemClickListener(position);
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return null != mDataList ? mDataList.size() : 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.job_title_tv)
        TextView jobTitleTv;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener( int position);

    }

    private OnItemClickListener mClickListener;

    public void setOnItemClickListener(OnItemClickListener pListener) {
        this.mClickListener = pListener;
    }


}
