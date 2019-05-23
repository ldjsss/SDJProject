package com.lldj.tc.mine.msg;

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
import com.lldj.tc.webview.WebviewNormalActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 消息Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/12/6 15:57<p>
 * Modify Time: 2018/12/6 15:57<p>
 */


public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<MsgBean> mlist;

    public MsgAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<MsgBean> plist) {
        this.mlist = plist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder mHolder = (viewHolder) holder;
        final MsgBean mItem = mlist.get(position);
        if (mItem.isRead()) {
            mHolder.leftBg.setBackgroundResource(R.drawable.rec_solid_leftround10_dcdcdc);
        } else {
            mHolder.leftBg.setBackgroundResource(R.drawable.rec_solid_leftround10_ea5822);
        }

        mHolder.msgRootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItem.setRead(true);
                notifyDataSetChanged();
                WebviewNormalActivity.launch(mContext, "消息详情");
            }
        });


    }

    @Override
    public int getItemCount() {

        return null != mlist ? mlist.size() : 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_bg)
        View leftBg;
        @BindView(R.id.msg_title_tv)
        TextView msgTitleTv;
        @BindView(R.id.msg_content_tv)
        TextView msgContentTv;
        @BindView(R.id.arrow_iv)
        ImageView arrowIv;
        @BindView(R.id.msg_root_layout)
        LinearLayout msgRootLayout;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
