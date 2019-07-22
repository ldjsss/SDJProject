package com.lldj.tc.info;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.TradingBean;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Adapter_tradingCell extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder = null;
    private List<TradingBean.TradeRecordDTO> mlist = null;
    private String[]tradingsName;
    private String[]tradingsStatus;
    private String[]rechStatus;


    public Adapter_tradingCell(Context mContext) {
        this.mContext = mContext;
        tradingsName = new String[]{"", "", "", mContext.getString(R.string.tradingsIn), mContext.getString(R.string.tradingsOut), mContext.getString(R.string.tradingsPre)};
        tradingsStatus = new String[]{mContext.getString(R.string.tradingsreview), mContext.getString(R.string.tradingsWithdrawal), mContext.getString(R.string.tradingsSucc), mContext.getString(R.string.tradingsLost)};
        rechStatus = new String[]{mContext.getString(R.string.tradingsreview), mContext.getString(R.string.tradingsSucc), mContext.getString(R.string.tradingsLost)};
    }

    public void changeData(ArrayList<TradingBean.TradeRecordDTO> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.trading_addlayout, parent, false));
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
        return null != mlist ? mlist.size() : 0;
    }


    class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tradingtitle)
        TextView tradingtitle;
        @BindView(R.id.tradingmoney)
        TextView tradingmoney;
        @BindView(R.id.tradingdata)
        TextView tradingdata;
        @BindView(R.id.tradingstatus)
        TextView tradingstatus;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bottomCommon() {
            int pos = getAdapterPosition() - 1;
            TradingBean.TradeRecordDTO _data = mlist.get(pos);
            if(_data == null) return;
            tradingtitle.setText(tradingsName[_data.getType()]);
            tradingmoney.setText(_data.getMoney());
            tradingdata.setText(AppUtils.getFormatTime6(_data.getTime()));

            int type = _data.getType();
            if(type == 3){
                tradingstatus.setText(rechStatus[_data.getRecharge_status()]);
            }
            else{
                tradingstatus.setText(tradingsStatus[_data.getCash_status()]);
            }
            tradingstatus.setTextColor(Color.parseColor((_data.getRecharge_status() == 2 || _data.getCash_status() == 3) ? "#DC143C": "#4285F4"));
        }


    }
}
