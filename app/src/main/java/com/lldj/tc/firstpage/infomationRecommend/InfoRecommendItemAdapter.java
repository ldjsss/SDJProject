package com.lldj.tc.firstpage.infomationRecommend;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 课程推荐的Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */


public class InfoRecommendItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;


    public InfoRecommendItemAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.inf_recommend_item_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder mHolder = (viewHolder) holder;
        //设置宽高
        int mWidth = ScreenUtil.getScreenWidth(mContext) / 3 - ScreenUtil.dipTopx(mContext, 10);
        ScreenUtil.setLinearLayoutParams(mHolder.rootLayout, mWidth, mWidth);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_conver)
        ImageView tagConver;
        @BindView(R.id.root_layout)
        LinearLayout rootLayout;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
