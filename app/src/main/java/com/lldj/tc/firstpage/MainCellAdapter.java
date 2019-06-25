package com.lldj.tc.firstpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
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
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.httpMgr.beans.FormatModel.Results;
import com.lldj.tc.httpMgr.beans.FormatModel.match.Odds;
import com.lldj.tc.httpMgr.beans.FormatModel.match.Team;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.http.HttpTool.bmpListener;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 */


public class MainCellAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Results> mlist = new ArrayList<>();
    private viewHolder mHolder = null;
    private int ViewType;
    private String[] statusText;
    private int[] winBmp;

    public MainCellAdapter(Context mContext, int _viewType) {
        this.mContext = mContext;
        this.ViewType = _viewType;
        statusText = new String[]{"", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.matchCurrentTitle), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};
        winBmp = new int[]{R.mipmap.main_failure, R.mipmap.main_victory};
    }

    public void changeData(List<Results> plist) {
        mlist = plist;

        AppUtils.dispatchEvent(new ObData(EventType.UPDATEMATCHLIST, mlist));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_recommend_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mHolder = (viewHolder) holder;
        this.mHolder.bottomCommon(ViewType);
    }

    @Override
    public int getItemCount() {
        return null != mlist ? mlist.size() : 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.gamebetlayout0)
        RelativeLayout gamebetlayout0;
        @BindView(R.id.gamestatus)
        TextView gamestatus;
        @BindView(R.id.gamestatusicon)
        ImageView gamestatusicon;
        @BindView(R.id.playname1)
        TextView playname1;
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
        LinearLayout bottomgamelayout;
        @BindView(R.id.bottomoverlayout)
        LinearLayout bottomoverlayout;
        @BindView(R.id.gameresult)
        TextView gameresult;

        @BindView(R.id.gamebet0)
        TextView gamebet0;
        @BindView(R.id.gamebet1)
        TextView gamebet1;


        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.playname0, R.id.gamebg, R.id.playname1, R.id.playcelllayout0, R.id.playcelllayout1})
        public void onViewClicked(View view) {
            Results _data = mlist.get(getAdapterPosition() - 1);
            switch (view.getId()) {
                case R.id.gamebg:
                    Map<String, Integer> _map = new HashMap();
                    _map.put("ViewType", ViewType);
                    _map.put("id", _data.getId());
                    AppUtils.dispatchEvent(new ObData(EventType.BETDETAILUI, _map));
                    break;
                case R.id.playname0:
                    if(_data.getOdds().get(0).getStatus() == 2){
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.hadlock), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                    betClick(_data, _data.getOdds().get(0).getId() + "");
                    break;
                case R.id.playname1:
                    if(_data.getOdds().get(1).getStatus() == 2){
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.hadlock), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                    betClick(_data, _data.getOdds().get(1).getId() + "");
                    break;
                case R.id.playcelllayout0:
                    Toast.makeText(mContext, "ffffff", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.playcelllayout1:
                    Toast.makeText(mContext, "jjjjjjjj", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        private void betClick(Results data, String tag){
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

        //刷新底部显示状态 0 只显示战队，无倍注显示，无法押获胜  显示战队和倍注，未开始状态 1 显示战队和倍注，滚盘状态 / 显示战队，锁盘，滚盘状态 3 已结束
        public void bottomCommon(int _type) {

            Results _data = mlist.get(getAdapterPosition() - 1);
            Team team0    = _data.getTeam() != null ? _data.getTeam().get(0) : null;
            Team team1    = _data.getTeam() != null ? _data.getTeam().get(1) : null;
            List<Odds> odds = _data.getOdds() != null ? _data.getOdds() : null;
            int status = _data.getStatus();

            gamename.setText(_data.getTournament_name());
            gamenamecount.setText("/ " + _data.getRound());
            gameplaycount.setText("+" + _data.getPlay_count());
            playnamecommon0.setText(team0.getTeam_short_name());
            playnamecommon1.setText(team1.getTeam_short_name());
            playovername0.setText(team0.getTeam_short_name());
            playovername1.setText(team1.getTeam_short_name());
            playname0.setText(team0.getTeam_short_name());
            playname1.setText(team1.getTeam_short_name());
            gametime.setText(_data.getStart_time());

            if (status == 2) {
                gamestatusicon.setImageResource(R.mipmap.match_status_1);
            } else {
                gamestatusicon.setImageResource(R.mipmap.match_status_0);
            }
            gamestatus.setText(statusText[status]);

            HttpTool.getBitmapUrl(team0.getTeam_logo(), new bmpListener() {
                @Override
                public void onFinish(Bitmap bitmap) {
                    if (bitmap != null) imgplayicon0.setImageBitmap(bitmap);
                }
            });

            HttpTool.getBitmapUrl(team1.getTeam_logo(), new bmpListener() {
                @Override
                public void onFinish(Bitmap bitmap) {
                    if (bitmap != null) imgplayicon1.setImageBitmap(bitmap);
                }
            });

            switch (_type) {
                case 0:
                    gamebetlayout0.setVisibility(View.GONE);
                    gamebetlayout1.setVisibility(View.GONE);
                    playname0.setVisibility(View.VISIBLE);
                    playname1.setVisibility(View.VISIBLE);
                    playnamecommon0.setVisibility(View.GONE);
                    playnamecommon1.setVisibility(View.GONE);
                    bottomgamelayout.setVisibility(View.VISIBLE);
                    bottomoverlayout.setVisibility(View.GONE);
                    gameresult.setVisibility(View.GONE);
                    gametime.setVisibility(View.VISIBLE);

                    //show final bet
                    Odds odd = getOddData(odds, "final", team0.getTeam_id());
                    if (odd != null) {
                        int _status = odd.getStatus();
                        if(_status == 2){ //lock bet
                            gamebet0.setVisibility(View.GONE);
                            imggamearrow0.setVisibility(View.GONE);
                        }
                        else if (!TextUtils.isEmpty(odd.getOdds())) {
                            gamebet0.setVisibility(View.VISIBLE);
                            imggamearrow0.setVisibility(View.VISIBLE);
                            imggamelock0.setVisibility(View.GONE);
                            gamebet0.setText(odd.getOdds());

//                            Utils.setFlickerAnimation(imggamearrow0, 13);
                        }
                        gamebetlayout0.setVisibility(View.VISIBLE);
                        playnamecommon0.setVisibility(View.VISIBLE);
                        playname0.setText("");
                    }
                    odd = getOddData(odds, "final", team1.getTeam_id());
                    if (odd != null) {
                        int _status = odd.getStatus();
                        if(_status == 2){ //lock bet
                            gamebet1.setVisibility(View.GONE);
                            imggamearrow1.setVisibility(View.GONE);
                        }
                        else if (!TextUtils.isEmpty(odd.getOdds())) {
                            gamebet1.setVisibility(View.VISIBLE);
                            imggamearrow1.setVisibility(View.VISIBLE);
                            imggamelock1.setVisibility(View.GONE);
                            gamebet1.setText(odd.getOdds());
//                            Utils.setFlickerAnimation(imggamearrow1, 5);
                        }
                        gamebetlayout1.setVisibility(View.VISIBLE);
                        playnamecommon1.setVisibility(View.VISIBLE);
                        playname1.setText("");
                    }
                    break;
                case 1:
                    gamebetlayout0.setVisibility(View.VISIBLE);
                    gamebetlayout1.setVisibility(View.VISIBLE);
                    playname0.setText("");
                    playname1.setText("");
                    playnamecommon0.setVisibility(View.VISIBLE);
                    playnamecommon1.setVisibility(View.VISIBLE);
                    imggamelock0.setVisibility(View.GONE);
                    imggamelock1.setVisibility(View.GONE);
                    bottomgamelayout.setVisibility(View.VISIBLE);
                    bottomoverlayout.setVisibility(View.GONE);
                    gametime.setVisibility(View.GONE);
                    gameresult.setVisibility(View.VISIBLE);


                    gameresult.setText(team0.getScore().getTotal() + " - " + team1.getScore().getTotal());

                    gamestatusicon.setBackgroundResource(R.mipmap.match_status_1);
                    break;
                case 2:
                    gamebetlayout0.setVisibility(View.VISIBLE);
                    gamebetlayout1.setVisibility(View.VISIBLE);
                    playname0.setText("");
                    playname1.setText("");
                    imggamearrow0.setVisibility(View.GONE);
                    imggamearrow1.setVisibility(View.GONE);
                    playnamecommon0.setVisibility(View.VISIBLE);
                    playnamecommon1.setVisibility(View.VISIBLE);
                    imggamelock0.setVisibility(View.GONE);
                    imggamelock1.setVisibility(View.GONE);

                    bottomgamelayout.setVisibility(View.VISIBLE);
                    bottomoverlayout.setVisibility(View.GONE);

                    gametime.setVisibility(View.VISIBLE);
                    gameresult.setVisibility(View.GONE);
                    break;
                case 3:
                    gamebetlayout0.setVisibility(View.GONE);
                    gamebetlayout1.setVisibility(View.GONE);
                    playname0.setVisibility(View.GONE);
                    playname1.setVisibility(View.GONE);
                    playnamecommon0.setVisibility(View.GONE);
                    playnamecommon1.setVisibility(View.GONE);

                    bottomgamelayout.setVisibility(View.GONE);
                    bottomoverlayout.setVisibility(View.VISIBLE);

                    gametime.setVisibility(View.GONE);
                    gameresult.setVisibility(View.VISIBLE);

                    Odds _odd = getOddData(odds, "final", team0.getTeam_id());
                    String win = "";
                    if(_odd != null)win = _odd.getWin();
                    if(!TextUtils.isEmpty(win) && Integer.parseInt(win)>=0) {
                        playvictoryicon0.setImageResource(winBmp[Integer.parseInt(win)]);
                        playvictoryicon0.setVisibility(View.VISIBLE);
                    }
                    else {
                        playvictoryicon0.setVisibility(View.GONE);
                    }

                    _odd = getOddData(odds, "final", team1.getTeam_id());
                    if(_odd != null)win = _odd.getWin();
                    if(!TextUtils.isEmpty(win) && Integer.parseInt(win)>=0) {
                        playvictoryicon1.setImageResource(winBmp[Integer.parseInt(win)]);
                        playvictoryicon1.setVisibility(View.VISIBLE);
                    }
                    else {
                        playvictoryicon1.setVisibility(View.GONE);
                    }

                    break;
            }
        }

        private Odds getOddData(List<Odds> odds, String key, int team_id) {
            if (odds == null) return null;
            for (int i = 0; i < odds.size(); i++) {
                String _match_stage = odds.get(i).getMatch_stage();
                if (_match_stage.equalsIgnoreCase("final") && team_id == odds.get(i).getTeam_id()) {
                    return odds.get(i);
                }
            }
            return null;
        }
    }

}
