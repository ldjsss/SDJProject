package com.lldj.tc.firstpage;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.match.Odds;

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


public class MatchCellAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder  = null;
    private int ViewType;

    Map<String, List<Odds>> mlist = new HashMap<>();

    public MatchCellAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(Map<String, List<Odds>> plist, int ViewType) {

        mlist = plist;
        ViewType = ViewType;

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

//                myposition.setText(key);

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
                        ((TextView) view.findViewById(R.id.playovername0)).setText(odd1.getName());

                        if(_odds.size() >= (i+1)*2){
                            Odds odd2 = _odds.get(i*2 + 1);
                            ((TextView) view.findViewById(R.id.playovername1)).setText(odd2.getName());
                        }
                        else {
                            view.findViewById(R.id.betvisible1).setVisibility(View.GONE);
                        }

                    }

                }

            }

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
