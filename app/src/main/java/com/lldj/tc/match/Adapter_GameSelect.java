package com.lldj.tc.match;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.BetModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter_GameSelect extends RecyclerView.Adapter<Adapter_GameSelect.MyViewHolder> {
    Context context;
    private List<BetModel> datas = new ArrayList<>();

    public Adapter_GameSelect(Context context, List<BetModel> datas){
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.betresultcell,null);
        MyViewHolder holder = new MyViewHolder(v);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BetModel _data = datas.get(position);
        holder.tv_matchname.setText((TextUtils.isEmpty(_data.getName()) ? "unknow": _data.getName()));
        holder.tv_odds.setText(_data.getOdds_value());
        holder.tv_betmoney.setText(_data.getBet_money());
        holder.tv_willget.setText(_data.getBet_win_money());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_matchname;
        TextView tv_odds;
        TextView tv_betmoney;
        TextView tv_willget;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_matchname = itemView.findViewById(R.id.tv_matchname);
            tv_odds = itemView.findViewById(R.id.tv_odds);
            tv_betmoney = itemView.findViewById(R.id.tv_betmoney);
            tv_willget = itemView.findViewById(R.id.tv_willget);
        }
    }
}
