package com.lldj.tc.firstpage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BetResultCellAdapter extends RecyclerView.Adapter<BetResultCellAdapter.MyViewHolder> {
    //当前上下文对象
    Context context;
    //RecyclerView填充Item数据的List对象
    private List<BetModel> datas = new ArrayList<>();

    public BetResultCellAdapter(Context context, List<BetModel> datas){
        this.context = context;
        this.datas = datas;
    }

    //创建ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.betresultcell,null);
        MyViewHolder holder = new MyViewHolder(v);
        holder.setIsRecyclable(false);
        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.textView.setText(datas.get(position));
    }

    //返回Item的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //继承RecyclerView.ViewHolder抽象类的自定义ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
//            textView = itemView.findViewById(R.id.text);
        }
    }
}
