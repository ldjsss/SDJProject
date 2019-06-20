package com.lldj.tc.firstpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.Results;
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
    private List<List<Odds>> mlist = new ArrayList<>();
    private List<String> klist = new ArrayList<>();
    private viewHolder mHolder = null;

    public MatchCellAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(Map<String, List<Odds>> plist) {
        Map<String, List<Odds>> _list = plist;

        mlist.clear();
        klist.clear();

        Iterator<Map.Entry<String, List<Odds>>> entries = _list.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, List<Odds>> entry = entries.next();
            mlist.add(entry.getValue());
            klist.add(entry.getKey());
//            System.out.println("///\nKey = " + entry.getKey() + ", Value = " + entry.getValue().toString());
        }

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

        @BindView(R.id.myposition)
        TextView myposition;
        @BindView(R.id.matchplayname)
        TextView matchplayname;
        @BindView(R.id.playvictoryicon0)
        ImageView playvictoryicon0;
        @BindView(R.id.playovername0)
        TextView playovername0;
        @BindView(R.id.playcelllayout0)
        RelativeLayout playcelllayout0;
        @BindView(R.id.playvictoryicon1)
        ImageView playvictoryicon1;
        @BindView(R.id.playovername1)
        TextView playovername1;
        @BindView(R.id.playcelllayout1)
        RelativeLayout playcelllayout1;
        @BindView(R.id.bottomoverlayout)
        LinearLayout bottomoverlayout;
        @BindView(R.id.gamebg)
        LinearLayout gamebg;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bottomCommon() {
            int position = getAdapterPosition() - 1;

            myposition.setText(klist.get(position));

            List<Odds> clist = mlist.get(getAdapterPosition() - 1);
//            Toast.makeText(mContext,"---------------test1" + clist.size(), Toast.LENGTH_SHORT).show();
            System.out.println("/\n\n\n////////////////////////////////////////////////////");
            for (int i = 0; i < clist.size(); i++) {
                System.out.println( i +"//////////\n"+ clist.get(i).toString());
            }
            System.out.println("/\n\n\n////////////////////////////////////////////////////");
        }


    }

}
