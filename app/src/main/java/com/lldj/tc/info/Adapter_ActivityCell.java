package com.lldj.tc.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.ActivityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Adapter_ActivityCell extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder = null;
    private List<ActivityBean.ActivityMode> messList = new ArrayList<>();


    public Adapter_ActivityCell(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(List<ActivityBean.ActivityMode> _messList) {
        this.messList = _messList;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.activitycelllayout, parent, false));
        holder.isRecyclable();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mHolder = (viewHolder) holder;

        this.mHolder.bottomCommon();
    }

    @Override
    public int getItemCount() {
        return messList.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.messtitle)
        TextView messtitle;
        @BindView(R.id.complete)
        TextView complete;
        @BindView(R.id.des)
        TextView des;
        @BindView(R.id.tbcomplete)
        TextView tbcomplete;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bottomCommon() {

            int pos = getAdapterPosition() - 1;

            ActivityBean.ActivityMode _data = messList.get(pos);

            messtitle.setText(_data.getTask_name());
            des.setText(_data.getTask_desc());

            complete.setText(String.format("(%s/%s)", _data.getUser_set_num(), _data.getTask_set_num()));

            if (_data.getStatus() == 1) {//0:未完成1:已完成未领取2:已领取
                tbcomplete.setText(mContext.getString(R.string.get));
            } else if (_data.getStatus() == 2) {
                tbcomplete.setText(mContext.getString(R.string.finished));
            }

        }

        @OnClick(R.id.tbcomplete)
        public void onClick() {
            int pos = getAdapterPosition() - 1;

            ActivityBean.ActivityMode _data = messList.get(pos);
            if (_data.getStatus() == 1) {//0:未完成1:已完成未领取2:已领取
                Toast.makeText(mContext, "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
