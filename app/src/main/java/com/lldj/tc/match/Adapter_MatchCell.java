package com.lldj.tc.match;

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

/**
 * description: <p>
 */


public class Adapter_MatchCell extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder  = null;
    private int[] winBmp;
    private ResultsModel tData;
    private List<ObData> groups = null;


    Map<String, List<Odds>> mlist = new HashMap<>();

    public Adapter_MatchCell(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(Map<String, List<Odds>> plist, ResultsModel _data) {
        this.mlist = plist;
        this.tData = _data;
        if(winBmp == null) winBmp = new int[]{R.mipmap.main_failure, R.mipmap.main_victory};

        notifyDataSetChanged();
    }

    public void updateSelect(List<ObData> _groups){
        this.groups = _groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.match_detail_item_layout, parent, false));
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
        @BindView(R.id.detailitembg)
        LinearLayout detailitembg;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bottomCommon() {

            Map.Entry<String, List<Odds>> entry;
            int pos = getAdapterPosition() - 1;
            if(pos == 0){
                entry = getEntry("final");
            }
            else entry = getEntry(pos + "");

            if(entry != null){
                String key      = entry.getKey();
                List<Odds> odds = entry.getValue();

//                Log.w("-----detail odds = ", odds.toString());

                Map<String, List<Odds>> oddMap = new HashMap<>();
                for (int i = 0; i < odds.size(); i++) {
                    Odds _odd   = odds.get(i);
                    String _key = _odd.getGroup_name();
                    List<Odds> itemList = oddMap.get(_key);
                    if(itemList == null){
                        itemList = new ArrayList<>();
                    }
                    itemList.add(_odd);

                    oddMap.put(_key, itemList);
                }

                Iterator<Map.Entry<String, List<Odds>>> entries = oddMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, List<Odds>> _nentry = entries.next();
                    String _key = _nentry.getKey();
                    List<Odds> _odds = _nentry.getValue();

                    detailitembg.removeAllViews();

                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    View view = inflater.inflate(R.layout.matchdetialonetitle, null);
                    view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    detailitembg.addView(view);
                    ((TextView)view.findViewById(R.id.myposition)).setText(key);

                    view = inflater.inflate(R.layout.matchdetailonebet, null);
                    view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    detailitembg.addView(view);
                    ((TextView)view.findViewById(R.id.matchplayname)).setText(_key);

                    int len = (int)Math.ceil(_odds.size()/2.0);
                    for (int i = 0; i < len; i++) {
                        view = inflater.inflate(R.layout.gamedetialitem, null);
                        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        detailitembg.addView(view);

                        Odds odd1 = _odds.get(i*2);
                        int _statue = odd1.getStatus();

                        ((TextView) view.findViewById(R.id.playovername0)).setText(TextUtils.isEmpty(odd1.getName())? "unknown" : odd1.getName());
                        String _oddstring = odd1.getOdds();

                        setSelect((TextView)view.findViewById(R.id.playcellselect0), odd1.getId()+"");

                        TextView tv_odds = (TextView) view.findViewById(R.id.playbetnum0);
                        ImageView im_arrows = (ImageView) view.findViewById(R.id.playdetailarrowicon0);
                        ImageView im_lock = (ImageView) view.findViewById(R.id.playlockicon0);
                        if(_statue == 1) { //normal
                            im_lock.setVisibility(View.GONE);
                            if (_oddstring.equals("")) {
                                tv_odds.setVisibility(View.GONE);
                                im_arrows.setVisibility(View.GONE);
                            } else {
                                tv_odds.setText(_oddstring);
                                tv_odds.setVisibility(View.VISIBLE);
                            }

                            ((RelativeLayout) view.findViewById(R.id.playcelllayout0)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                                    betClick(tData, odd1.getId() + "");
                                }
                            });
                        }
                        else if(_statue == 2){ //lock
                            tv_odds.setVisibility(View.GONE);
                            im_arrows.setVisibility(View.GONE);
                            im_lock.setVisibility(View.VISIBLE);
                        }
                        else{
                            im_lock.setVisibility(View.GONE);
                            tv_odds.setVisibility(View.GONE);
                            im_arrows.setVisibility(View.GONE);
                        }

                        //set player 1 win icon
                        String _win = odd1.getWin();
                        ImageView iv_win = (ImageView) view.findViewById(R.id.playvictoryicon0);
                        if(_win == null || _win.equals("") || Integer.parseInt(_win)<0){
                            iv_win.setVisibility(View.GONE);
                        }
                        else {
                            iv_win.setImageResource(winBmp[Integer.parseInt(_win)]);
                            iv_win.setVisibility(View.VISIBLE);
                        }

                        if(_odds.size() >= (i+1)*2){
                            Odds odd2 = _odds.get(i*2 + 1);
                            _oddstring = odd2.getOdds();
                            _statue = odd2.getStatus();

                            ((TextView) view.findViewById(R.id.playovername1)).setText(TextUtils.isEmpty(odd2.getName())? "unknown" : odd2.getName());
                            setSelect((TextView)view.findViewById(R.id.playcellselect1), odd2.getId()+"");

                            tv_odds = (TextView) view.findViewById(R.id.playbetnum1);
                            im_arrows = (ImageView) view.findViewById(R.id.playdetailarrowicon1);
                            im_lock = (ImageView) view.findViewById(R.id.playlockicon1);

                            if(_statue == 1) { //normal
                                im_lock.setVisibility(View.GONE);
                                if (_oddstring.equals("")) {
                                    tv_odds.setVisibility(View.GONE);
                                    im_arrows.setVisibility(View.GONE);
                                } else {
                                    tv_odds.setText(_oddstring);
                                    tv_odds.setVisibility(View.VISIBLE);
                                }

                                ((RelativeLayout) view.findViewById(R.id.playcelllayout1)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                                        betClick(tData, odd2.getId() + "");
                                    }
                                });
                            }
                            else if(_statue == 2) { //lock
                                tv_odds.setVisibility(View.GONE);
                                im_arrows.setVisibility(View.GONE);
                                im_lock.setVisibility(View.VISIBLE);
                            }
                            else{
                                im_lock.setVisibility(View.GONE);
                                tv_odds.setVisibility(View.GONE);
                                im_arrows.setVisibility(View.GONE);
                            }

                            //set player 2 win icon
                            _win = odd2.getWin();
                            iv_win = (ImageView) view.findViewById(R.id.playvictoryicon1);
                            if(_win == null || _win.equals("") || Integer.parseInt(_win)<0){
                                iv_win.setVisibility(View.GONE);
                            }
                            else {
                                iv_win.setImageResource(winBmp[Integer.parseInt(_win)]);
                                iv_win.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            view.findViewById(R.id.betvisible1).setVisibility(View.GONE);
                        }

                    }

                }

            }

        }

        private void setSelect(TextView _text, String matchID){
            boolean _select = false;
            if(groups != null) {
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getTag().equals(matchID)) _select = true;
                }
            }
            if(_select){
                _text.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetselectbg));
            }
            else{
                _text.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetbg));
            }
        }

        private void betClick(ResultsModel data, String tag){
            RxTimerUtil.timer(50, new RxTimerUtil.IRxNext() {
                @Override
                public void doNext(long number) {
                    ObData obj = new ObData(EventType.BETLISTADD, data);
                    obj.setTag(tag);
                    AppUtils.dispatchEvent(obj);
                }
                @Override
                public void onComplete() { }
            });
        }

        private Map.Entry<String, List<Odds>> getEntry(String key){
            Iterator<Map.Entry<String, List<Odds>>> entries = mlist.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, List<Odds>> entry = entries.next();
                if(entry.getKey().indexOf(key) != - 1) return entry;
            }
            return null;
        }


    }
}
