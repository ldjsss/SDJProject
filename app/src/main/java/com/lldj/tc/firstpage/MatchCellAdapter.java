package com.lldj.tc.firstpage;

import android.content.Context;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 */


public class MatchCellAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mlist = new ArrayList<>();
    private viewHolder mHolder = null;

    public MatchCellAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<String> plist) {
        mlist = plist;
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
          mHolder.myposition.setText("我是第"+position+"行");

    }

    @Override
    public int getItemCount() {
        return null != mlist ? mlist.size() : 0;
    }



    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.myposition)
        TextView myposition;
        @BindView(R.id.gameicon)
        ImageView gameicon;
        @BindView(R.id.gamename)
        TextView gamename;
        @BindView(R.id.gamenamecount)
        TextView gamenamecount;
        @BindView(R.id.gameplaycount)
        TextView gameplaycount;
        @BindView(R.id.gametime)
        TextView gametime;
        @BindView(R.id.img_layout)
        RelativeLayout imgLayout;
        @BindView(R.id.playname0)
        TextView playname0;
        @BindView(R.id.gamebet0)
        TextView gamebet0;
        @BindView(R.id.gamebetlayout0)
        RelativeLayout gamebetlayout0;
        @BindView(R.id.gamestatus)
        TextView gamestatus;
        @BindView(R.id.playname1)
        TextView playname1;
        @BindView(R.id.gamebet1)
        TextView gamebet1;
        @BindView(R.id.gamebetlayout1)
        RelativeLayout gamebetlayout1;
        @BindView(R.id.gamebg)
        LinearLayout gamebg;
        @BindView(R.id.playnamecommon0)
        TextView playnamecommon0;
        @BindView(R.id.playnamecommon1)
        TextView playnamecommon1;
        @BindView(R.id.imggamearrow0)
        ImageView imggamearrow0;
        @BindView(R.id.imggamelock0)
        ImageView imggamelock0;
        @BindView(R.id.imggamearrow1)
        ImageView imggamearrow1;
        @BindView(R.id.imggamelock1)
        ImageView imggamelock1;
        @BindView(R.id.imgplayicon0)
        ImageView imgplayicon0;
        @BindView(R.id.imgplayicon1)
        ImageView imgplayicon1;
        @BindView(R.id.playovername0)
        TextView playovername0;
        @BindView(R.id.playvictoryicon0)
        ImageView playvictoryicon0;
        @BindView(R.id.playovername1)
        TextView playovername1;
        @BindView(R.id.playvictoryicon1)
        ImageView playvictoryicon1;
        @BindView(R.id.bottomgamelayout)
        RelativeLayout bottomgamelayout;
        @BindView(R.id.bottomoverlayout)
        LinearLayout bottomoverlayout;
//        @BindView(R.id.gameresult)
//        RelativeLayout gameresult;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.gamebetlayout0, R.id.gamebetlayout1, R.id.gamebg})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.gamebetlayout0:
                    System.out.print("sssssssss");
                    break;
                case R.id.gamebetlayout1:
                    System.out.print("sssssssss");
                    break;
                case R.id.gamebg:
                    MatchDetailActivity.launch(mContext, 0);
                    break;
            }
        }
    }

    //刷新底部显示状态 0 只显示战队，无倍注显示，无法押获胜 1 显示战队和倍注，未开始状态 2 显示战队和倍注，滚盘状态 / 显示战队，锁盘，滚盘状态 3 已结束
    public void bottomCommon(int _type) {
        if (mHolder == null) return;
        switch (_type) {
            case 0:
                mHolder.gamebetlayout0.setVisibility(View.GONE);
                mHolder.gamebetlayout1.setVisibility(View.GONE);
                mHolder.playname0.setVisibility(View.VISIBLE);
                mHolder.playname1.setVisibility(View.VISIBLE);
                mHolder.playnamecommon0.setVisibility(View.GONE);
                mHolder.playnamecommon1.setVisibility(View.GONE);
                mHolder.bottomgamelayout.setVisibility(View.VISIBLE);
                mHolder.bottomoverlayout.setVisibility(View.GONE);
                break;
            case 1:
                mHolder.gamebetlayout0.setVisibility(View.VISIBLE);
                mHolder.gamebetlayout1.setVisibility(View.VISIBLE);
                mHolder.playname0.setText("");
                mHolder.playname1.setText("");
                mHolder.playnamecommon0.setVisibility(View.VISIBLE);
                mHolder.playnamecommon1.setVisibility(View.VISIBLE);
                mHolder.imggamelock0.setVisibility(View.GONE);
                mHolder.imggamelock1.setVisibility(View.GONE);
                mHolder.bottomgamelayout.setVisibility(View.VISIBLE);
                mHolder.bottomoverlayout.setVisibility(View.GONE);
                break;
            case 2:
                mHolder.gamebetlayout0.setVisibility(View.VISIBLE);
                mHolder.gamebetlayout1.setVisibility(View.VISIBLE);
                mHolder.playname0.setText("");
                mHolder.playname1.setText("");
                mHolder.imggamearrow0.setVisibility(View.GONE);
                mHolder.imggamearrow1.setVisibility(View.GONE);
                mHolder.playnamecommon0.setVisibility(View.VISIBLE);
                mHolder.playnamecommon1.setVisibility(View.VISIBLE);
                mHolder.imggamelock0.setVisibility(View.GONE);
                mHolder.imggamelock1.setVisibility(View.GONE);

                mHolder.bottomgamelayout.setVisibility(View.VISIBLE);
                mHolder.bottomoverlayout.setVisibility(View.GONE);

                mHolder.gametime.setVisibility(View.VISIBLE);
//                mHolder.gameresult.setVisibility(View.GONE);
                break;
            case 3:
                mHolder.gamebetlayout0.setVisibility(View.GONE);
                mHolder.gamebetlayout1.setVisibility(View.GONE);
                mHolder.playname0.setVisibility(View.GONE);
                mHolder.playname1.setVisibility(View.GONE);
                mHolder.playnamecommon0.setVisibility(View.GONE);
                mHolder.playnamecommon1.setVisibility(View.GONE);
                mHolder.gamestatus.setVisibility(View.GONE);

                mHolder.bottomgamelayout.setVisibility(View.GONE);
                mHolder.bottomoverlayout.setVisibility(View.VISIBLE);

                mHolder.gametime.setVisibility(View.GONE);
//                mHolder.gameresult.setVisibility(View.VISIBLE);
                break;
        }
    }

}
