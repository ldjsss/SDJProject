package com.lldj.tc.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.BordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Adapter_MessCell extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder = null;
    private List<BordBean.BordMode> messList = new ArrayList<>();


    public Adapter_MessCell(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(List<BordBean.BordMode> _messList) {
        this.messList = _messList;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.messcelllayout, parent, false));
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
        @BindView(R.id.messcontent)
        TextView messcontent;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bottomCommon() {

            int pos = getAdapterPosition() - 1;

            BordBean.BordMode _data = messList.get(pos);

            messtitle.setText(_data.getTitle());
            messcontent.setText(_data.getBody());

        }

    }
}
