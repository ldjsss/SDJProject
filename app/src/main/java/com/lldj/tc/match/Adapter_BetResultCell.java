package com.lldj.tc.match;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.BetMatchBean;
import com.lldj.tc.http.beans.FormatModel.RecordModel;
import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Adapter_BetResultCell extends RecyclerView.Adapter<Adapter_BetResultCell.MyViewHolder> {
    private Context context;
    private List<BetModel> datas = new ArrayList<>();
    private List<BetMatchBean.betResult> datas2 = new ArrayList<>();
    private boolean result = false;
    Map<String, String> mapNames = SharePreUtils.getInstance().getMapNames();
    private ImageLoader imageLoader = new ImageLoader(bActivity, R.mipmap.game_arena, R.mipmap.game_arena);

    public Adapter_BetResultCell(Context context, List<BetModel> datas){
        this.context = context;
        this.datas = datas;
    }

    public Adapter_BetResultCell(Context context, boolean result, List<BetMatchBean.betResult> datas2 ){
        this.context = context;
        this.datas2 = datas2;
        this.result = result;
    }

    public void changeData(List<BetModel> datas){
        this.datas = datas;
        notifyDataSetChanged();

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
//        BetModel _data = result ? datas.get(position) : null;
        BetMatchBean.betResult _data = datas2.size()> position ? datas2.get(position) : null;
        BetModel record = _data != null ? _data.getRecord() : datas.get(position);

        holder.tv_matchname.setText(record == null ? "unknow": record.getOdds_name());
        holder.tv_odds.setText(record == null ? "unknow": context.getString(R.string.betodds) + record.getBet_odds());
        holder.tv_betmoney.setText(record == null ? "unknow": record.getBet_money());
        holder.tv_willget.setText(record == null ? "unknow": record.getBet_win_money());

        holder.betnumlayout.setVisibility(datas2.size()>0 ? View.GONE : View.VISIBLE);
        holder.tv_warm.setVisibility(datas2.size()>0 ? View.VISIBLE : View.GONE);
        holder.tv_warm.setText(record == null ? context.getString(R.string.Failureoforders) : context.getString(R.string.betsurewarm));
        holder.tv_betsureing.setText(record == null ? context.getString(R.string.Failureoforders) : context.getString(R.string.betsureing));
        holder.tv_matchvsname.setText(record == null ? "unknow" : record.getMatch_short_name());
        holder.tv_starttime.setText(context.getString(R.string.starttime) + (record == null ? "unknow" : AppUtils.getFormatTime6(record.getStart_time())));
        holder.tv_matchvsname.setText((record == null ? "unknow" : record.getMatch_short_name()));
        holder.tv_matchtround.setText(record == null ? "unknow" : mapNames.get(record.getMatch_stage()));
        holder.tv_matchtstatus.setText(record == null ? "unknow" : (record.getBet_site() == 0 ? context.getString(R.string.matchFrontTitle) : context.getString(R.string.matchCurrentTitle)));

        if(datas2.size() > 0){
            holder.betvictoryicon.setVisibility(View.GONE);
            holder.tv_havepay.setVisibility(View.GONE);
            holder.tv_betsureing.setVisibility(View.VISIBLE);
        }
        else if(datas.size() > 0){
            holder.tv_havepay.setVisibility(View.GONE);
            holder.tv_betsureing.setVisibility(View.GONE);
            holder.tv_betnum.setText(record.getOrder_num());
            holder.tv_bettime.setText(AppUtils.getFormatTime6(record.getBet_created_time()));
            holder.tv_betsureing.setVisibility(View.GONE);

            holder.betvictoryicon.setImageResource(record.getWin() == 0 ? R.mipmap.main_failure : R.mipmap.main_victory);
            holder.betvictoryicon.setVisibility(record.getWin() < 2 ? View.VISIBLE : View.GONE);

        }

        if(record == null) return;

        imageLoader.getAndSetImage(record.getGame_logo(), holder.tv_logoicon);
    }

    @Override
    public int getItemCount() {
        return datas2.size()>0 ? datas2.size() : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_matchname;
        private TextView tv_odds;
        private TextView tv_betmoney;
        private TextView tv_willget;
        private TextView tv_bettime;
        private TextView tv_betnum;
        private TextView tv_warm;
        private RelativeLayout betnumlayout;
        private ImageView betvictoryicon;
        private TextView tv_havepay;
        private TextView tv_betsureing;
        private TextView tv_matchvsname;
        private TextView tv_starttime;
        private TextView tv_matchtround;
        private TextView tv_matchtstatus;
        private ImageView tv_logoicon;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_matchname = itemView.findViewById(R.id.tv_matchname);
            tv_odds = itemView.findViewById(R.id.tv_odds);
            tv_betmoney = itemView.findViewById(R.id.tv_betmoney);
            tv_willget = itemView.findViewById(R.id.tv_willget);
            tv_bettime = itemView.findViewById(R.id.tv_bettime);
            tv_betnum = itemView.findViewById(R.id.tv_betnum);
            tv_warm = itemView.findViewById(R.id.tv_warm);
            betnumlayout = itemView.findViewById(R.id.betnumlayout);
            betvictoryicon = itemView.findViewById(R.id.betvictoryicon);
            tv_havepay = itemView.findViewById(R.id.tv_havepay);
            tv_betsureing = itemView.findViewById(R.id.tv_betsureing);
            tv_matchvsname = itemView.findViewById(R.id.tv_matchvsname);
            tv_starttime = itemView.findViewById(R.id.tv_starttime);
            tv_matchtround = itemView.findViewById(R.id.tv_matchtround);
            tv_matchtstatus = itemView.findViewById(R.id.tv_matchtstatus);
            tv_logoicon = itemView.findViewById(R.id.tv_logoicon);
        }
    }
}
