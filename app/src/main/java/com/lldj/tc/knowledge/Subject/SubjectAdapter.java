package com.lldj.tc.knowledge.Subject;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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


public class SubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public SubjectAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.knowledge_sub_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder mHolder = (viewHolder) holder;
        //设置宽高
        int mWidth = ScreenUtil.getScreenWidth(mContext);
        int height = mWidth*210/375;
        ScreenUtil.setLinearLayoutParams(mHolder.imgLayout,mWidth,height);

    }

    @Override
    public int getItemCount() {

        return 20;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.scan_tv)
        TextView scanTv;
        @BindView(R.id.img_layout)
        RelativeLayout imgLayout;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
