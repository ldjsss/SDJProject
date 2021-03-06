package com.lldj.tc.match;

import android.content.Context;
import android.graphics.Color;
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
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.ImageLoader;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Adapter_MatchCell extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder = null;
    private int[] winBmp;
    private ResultsModel tData;
    private ArrayList<List<Odds>> mlist = new ArrayList<>();
    private Map<String, String> mapNames = SharePreUtils.getInstance().getMapNames();
    private Map<Integer, String> oMap = new HashMap<>();
    private Map<String, ObData> groups;

    public Adapter_MatchCell(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<List<Odds>>  plist, ResultsModel _data) {
        this.mlist = plist;
        this.tData = _data;
        if (winBmp == null) winBmp = new int[]{R.mipmap.main_failure, R.mipmap.main_victory};
        groups = Fragment_Main.selectGroups;

        notifyDataSetChanged();
    }

    public void updateSelect(List<ObData> _groups) {
        groups.clear();
        if(_groups == null) return;

        ObData _ObData;
        for (int i = 0; i < _groups.size(); i++) {
            _ObData = _groups.get(i);
            groups.put(_ObData.getTag(), _ObData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.match_detail_item_layout, parent, false));
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
        return mlist.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detailitembg)
        LinearLayout detailitembg;
        @BindView(R.id.addlayout)
        LinearLayout addlayout;

        private LayoutInflater inflater;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            inflater = LayoutInflater.from(mContext);
        }

        public void bottomCommon() {

            int pos = getAdapterPosition() - 1;
            List<Odds> oddList = mlist.get(pos);

            View view1 = addlayout.findViewById(R.id.dtitlelayout);
            View view2 = addlayout.findViewById(R.id.detaionebetbg);
            View view3 = addlayout.findViewById(R.id.bottomoverlayout);

//            if(view1 != null) view1.setVisibility(View.GONE);
//            if(view2 != null) view2.setVisibility(View.GONE);
//            if(view3 != null) view3.setVisibility(View.GONE);

            int _size = oddList.size();
            if(_size <= 0) return;

            Odds odd1 = oddList.get(0);
            int  id1  = odd1.getId();
            int _status = odd1.getStatus();
            String mStage1 = odd1.getMatch_stage();

            if(id1 <= 0){
                if(id1 == -1){
                    if(view1 == null) {
                        view1 = inflater.inflate(R.layout.matchdetialonetitle, null);
                        view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        addlayout.addView(view1);
                    }
                    String _name = mapNames.get(mStage1);
                    ((TextView)view1.findViewById(R.id.myposition)).setText(TextUtils.isEmpty(_name) ? mStage1 : _name);
                    view1.setVisibility(View.VISIBLE);
                    if(view2 != null) view2.setVisibility(View.GONE);
                    if(view3 != null) view3.setVisibility(View.GONE);
                }
                else if(id1 == -2){
                    if(view2 == null) {
                        view2 = inflater.inflate(R.layout.matchdetailonebet, null);
                        view2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        addlayout.addView(view2);
                    }

                    ((TextView)view2.findViewById(R.id.matchplayname)).setText(String.format("| %s", mStage1));
                    view2.setVisibility(View.VISIBLE);

                    if(view1 != null) view1.setVisibility(View.GONE);
                    if(view3 != null) view3.setVisibility(View.GONE);
                }

                else if(id1 == -3){
                    if(view3 == null) {
                        view3 = inflater.inflate(R.layout.gamedetialitem, null);
                        view3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        addlayout.addView(view3);
                    }

                    view3.findViewById(R.id.betvisible1).setVisibility(View.GONE);
                    view3.findViewById(R.id.betvisible0).setVisibility(View.GONE);
                    view3.setVisibility(View.VISIBLE);

                    if(view1 != null) view1.setVisibility(View.GONE);
                    if(view2 != null) view2.setVisibility(View.GONE);
                }
            }
            else{
                if(view3 == null) {
                    view3 = inflater.inflate(R.layout.gamedetialitem, null);
                    view3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    addlayout.addView(view3);
                }
                else{
                    view3.findViewById(R.id.betvisible0).setVisibility(View.VISIBLE);
                    view3.findViewById(R.id.betvisible1).setVisibility(View.VISIBLE);
                }

                if(view1 != null) view1.setVisibility(View.GONE);
                if(view2 != null) view2.setVisibility(View.GONE);

                ((TextView) view3.findViewById(R.id.playovername0)).setText(TextUtils.isEmpty(odd1.getName()) ? "unknown" : odd1.getName());
                String _oddstring = odd1.getOdds();

                TextView tv_odds = view3.findViewById(R.id.playbetnum0);
                ImageView im_lock = view3.findViewById(R.id.playlockicon0);
                TextView playcellselect0 = view3.findViewById(R.id.playcellselect0);
                updateArrow(odd1, oMap.get(id1), tv_odds, view3.findViewById(R.id.playdetailarrowicon0));
                oMap.put(id1, _oddstring);
                setSelect(playcellselect0, String.valueOf(id1));
                if (_status == 1) { //normal
                    im_lock.setVisibility(View.GONE);
                    if (_oddstring.equals("")) {
                        tv_odds.setVisibility(View.GONE);
                    } else {
                        tv_odds.setText(_oddstring);
                        tv_odds.setVisibility(View.VISIBLE);
                    }

                    view3.findViewById(R.id.playcelllayout0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                            betClick(tData, String.valueOf(odd1.getId()));
                        }
                    });
                } else if (_status == 2) { //lock
                    tv_odds.setVisibility(View.GONE);
                    im_lock.setVisibility(View.VISIBLE);
                    playcellselect0.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetbg));
                } else {
                    tv_odds.setVisibility(View.GONE);
                    im_lock.setVisibility(View.GONE);
                    playcellselect0.setBackground(mContext.getResources().getDrawable(R.drawable.mathtitle_bg));
                }

                //set player 1 win icon
                String _win = odd1.getWin();
                ImageView iv_win = view3.findViewById(R.id.playvictoryicon0);
                if (_win == null || _win.equals("") || Integer.parseInt(_win) < 0) {
                    iv_win.setVisibility(View.GONE);
                } else {
                    iv_win.setImageResource(winBmp[Integer.parseInt(_win)]);
                    iv_win.setVisibility(View.VISIBLE);
                }

                if(_size >= 2) {
                    Odds odd2 = oddList.get(1);
                    _oddstring = odd2.getOdds();
                    _status = odd2.getStatus();
                    int _id2 = odd2.getId();

                    ((TextView) view3.findViewById(R.id.playovername1)).setText(TextUtils.isEmpty(odd2.getName()) ? "unknown" : odd2.getName());

                    tv_odds = view3.findViewById(R.id.playbetnum1);
                    im_lock = view3.findViewById(R.id.playlockicon1);
                    TextView playcellselect1 = view3.findViewById(R.id.playcellselect1);
                    updateArrow(odd2, oMap.get(_id2), tv_odds, view3.findViewById(R.id.playdetailarrowicon1));
                    oMap.put(_id2, _oddstring);
                    setSelect(playcellselect1, String.valueOf(_id2));
                    if (_status == 1) { //normal
                        im_lock.setVisibility(View.GONE);
                        if (_oddstring.equals("")) {
                            tv_odds.setVisibility(View.GONE);
                        } else {
                            tv_odds.setText(_oddstring);
                            tv_odds.setVisibility(View.VISIBLE);
                        }

                        view3.findViewById(R.id.playcelllayout1).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                                betClick(tData, String.valueOf(odd2.getId()));
                            }
                        });
                    } else if (_status == 2) { //lock
                        tv_odds.setVisibility(View.GONE);
                        im_lock.setVisibility(View.VISIBLE);
                        playcellselect1.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetbg));
                    } else {
                        im_lock.setVisibility(View.GONE);
                        tv_odds.setVisibility(View.GONE);
                        playcellselect1.setBackground(mContext.getResources().getDrawable(R.drawable.mathtitle_bg));
                    }

                    //set player 2 win icon
                    _win = odd2.getWin();
                    iv_win = (ImageView) view3.findViewById(R.id.playvictoryicon1);
                    if (_win == null || _win.equals("") || Integer.parseInt(_win) < 0) {
                        iv_win.setVisibility(View.GONE);
                    } else {
                        iv_win.setImageResource(winBmp[Integer.parseInt(_win)]);
                        iv_win.setVisibility(View.VISIBLE);
                    }
                } else {
                    view3.findViewById(R.id.betvisible1).setVisibility(View.GONE);
                }


                view3.setVisibility(View.VISIBLE);
            }

        }

        private void updateArrow(Odds odd, String _last, TextView betText, ImageView imggamearrow){
            if(odd == null || betText == null) return;
            int _status     = odd.getStatus();
            String _current = odd.getOdds();

            if( _status == 1
                    && !TextUtils.isEmpty(_last)
                    && !TextUtils.isEmpty(_current)
                    && !_last.equalsIgnoreCase(_current)){

                float lastOdds = Float.parseFloat(_last);
                float curOdds  = Float.parseFloat(_current);
                if(curOdds > lastOdds){
                    imggamearrow.setImageResource(R.mipmap.main_courage);
                    betText.setTextColor(Color.GREEN);
                }
                else if(curOdds < lastOdds) {
                    imggamearrow.setImageResource(R.mipmap.main_warning);
                    betText.setTextColor(Color.RED);
                }
                else {
                    betText.setTextColor(mContext.getResources().getColor(R.color.color_cdc3b3));
                }
                Utils.setFlickerAnimation(imggamearrow, 8, new Utils.Listener() {
                    @Override
                    public void onFinish() {
                        betText.setTextColor(mContext.getResources().getColor(R.color.color_cdc3b3));
                    }
                });
                betText.setTag(odd.getMatch_id());
            }
            else imggamearrow.setImageAlpha(0);
        }

        private void setSelect(TextView _text, String matchID) {
            boolean _select = false;
            if(groups.get(matchID) != null)_select = true;
            if (_select) {
                _text.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetselectbg));
            } else {
                _text.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetbg));
            }
        }

        private void betClick(ResultsModel data, String tag) {
            AppUtils.dispatchEvent(new ObData(EventType.BETLISTADD, data, tag));
        }

    }
}
