package com.lldj.tc.knowledge.SmallClass;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lldj.tc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 微课推荐的Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */


public class SmallClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    public SmallClassAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.knowledge_small_class_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder mHolder = (viewHolder) holder;
        //设置宽高
//        int mWidth = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dipTopx(mContext, 20);
//        int height = mWidth * 560 / 1000;
//        ScreenUtil.setLinearLayoutParams(mHolder.imgLayout, mWidth, height);

    }

    @Override
    public int getItemCount() {

        return 20;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.summary_info_tv)
        TextView summaryInfoTv;
        @BindView(R.id.label_layout)
        LinearLayout labelLayout;
        @BindView(R.id.conver_iv)
        ImageView converIv;;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
