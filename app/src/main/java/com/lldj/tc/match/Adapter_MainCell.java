package com.lldj.tc.match;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
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
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.http.beans.FormatModel.matchModel.Team;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.utils.Utils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.http.HttpTool.bmpListener;
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


public class Adapter_MainCell extends RecyclerView.Adapter {

    private Context mContext;
    private List<ResultsModel> mlist = new ArrayList<>();
    private viewHolder mHolder = null;
    private int ViewType;
    private String[] statusText;
    private int[] winBmp;
    private List<ObData> groups = null;

    public Adapter_MainCell(Context mContext, int _viewType) {
        this.mContext = mContext;
        this.ViewType = _viewType;
        statusText = new String[]{"", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.matchCurrentTitle), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};
        winBmp = new int[]{R.mipmap.main_failure, R.mipmap.main_victory};
    }

    public void changeData(List<ResultsModel> plist) {
        mlist = plist;

        AppUtils.dispatchEvent(new ObData(EventType.UPDATEMATCHLIST, mlist));
        notifyDataSetChanged();
    }

    public void updateSelect(List<ObData> _groups){
        this.groups = _groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_recommend_item_layout, parent, false));
        holder.isRecyclable();
        return holder;
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
//            AppUtils.screenAdapterLoadView((ViewGroup)itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.playname0, R.id.gamebg, R.id.playname1, R.id.playcelllayout0, R.id.playcelllayout1})
        public void onViewClicked(View view) {
            if(getAdapterPosition() - 1 < 0) return;
            ResultsModel _data = mlist.get(getAdapterPosition() - 1);
            switch (view.getId()) {
                case R.id.gamebg:
                    Map<String, Integer> _map = new HashMap();
                    _map.put("ViewType", ViewType);
                    _map.put("id", _data.getId());
                    AppUtils.dispatchEvent(new ObData(EventType.BETDETAILUI, _map));
                    break;
                case R.id.playname0:
                    if(_data.getOdds().get(0).getStatus() != 1){
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.unbet), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppUtils.dispatchEvent(new ObData(EventType.BETLISTADD, _data, _data.getOdds().get(0).getId() + ""));
                    break;
                case R.id.playname1:
                    if(_data.getOdds().get(1).getStatus() != 1){
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.unbet), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppUtils.dispatchEvent(new ObData(EventType.BETLISTADD, _data, _data.getOdds().get(1).getId() + ""));
                    break;
                case R.id.playcelllayout0:
                    Toast.makeText(mContext, "ffffff", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.playcelllayout1:
                    Toast.makeText(mContext, "jjjjjjjj", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        public void bottomCommon(int _type) {

            ResultsModel _data = mlist.get(getAdapterPosition() - 1);
            Team team0    = _data.getTeam() != null ? _data.getTeam().get(0) : null;
            Team team1    = _data.getTeam() != null ? _data.getTeam().get(1) : null;
            List<Odds> odds = _data.getOdds() != null ? _data.getOdds() : null;
            int matchStatus = _data.getStatus();//1:未开始2:滚盘3:已结束4:已取消或异常

            gamename.setText(_data.getTournament_name());
            gamenamecount.setText("/ " + _data.getRound());
            gameplaycount.setText("+" + _data.getPlay_count());
            playnamecommon0.setText(team0.getTeam_name());
            playnamecommon1.setText(team1.getTeam_name());
            playovername0.setText(team0.getTeam_name());
            playovername1.setText(team1.getTeam_name());
            playname0.setText(team0.getTeam_name());
            playname1.setText(team1.getTeam_name());
            gametime.setText(AppUtils.getFormatTime5(_data.getStart_time_ms()));

            setSelect(odds);

            gamestatus.setText(statusText[matchStatus]);
            gamestatusicon.setImageResource(matchStatus == 2 ? R.mipmap.match_status_1 : R.mipmap.match_status_0);
            gameresult.setVisibility((matchStatus == 2 || matchStatus == 3) ? View.VISIBLE : View.GONE);
            gametime.setVisibility((matchStatus == 2 || matchStatus == 3) ? View.GONE : View.VISIBLE);

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

            gameresult.setText(team0.getScore().getTotal() + " - " + team1.getScore().getTotal());

            Odds odd0 = getOddData(odds, "final", team0.getTeam_id());
            Odds odd1 = getOddData(odds, "final", team1.getTeam_id());
            updateArrow(odd0, gamebet0, imggamearrow0);
            updateArrow(odd1, gamebet1, imggamearrow1);

            if(odd0!=null){
                int _status = odd0.getStatus();
                gamebet0.setText(odd0.getOdds());
                if(_status <= 2){

                    imggamelock0.setVisibility(_status == 2 ? View.VISIBLE: View.GONE);
                    gamebet0.setVisibility(_status == 2 ? View.GONE: View.VISIBLE);

                    gamebetlayout0.setVisibility(View.VISIBLE);
                    playnamecommon0.setVisibility(View.VISIBLE);
                    playname0.setText("");
                }
                else if(_status == 4){
                    gamebetlayout0.setVisibility(View.GONE);
                    playnamecommon0.setVisibility(View.GONE);
                    playname0.setVisibility(View.VISIBLE);
                }
            }
            else {
                gamebetlayout0.setVisibility(View.GONE);
                playnamecommon0.setVisibility(View.GONE);
            }

            if(odd1!=null){
                int _status = odd1.getStatus();
                gamebet1.setText(odd1.getOdds());

                if(_status <= 2){
                    imggamelock1.setVisibility(_status == 2 ? View.VISIBLE: View.GONE);
                    gamebet1.setVisibility(_status == 2 ? View.GONE: View.VISIBLE);

                    gamebetlayout1.setVisibility(View.VISIBLE);
                    playnamecommon1.setVisibility(View.VISIBLE);
                    playname1.setText("");
                }
                else if(_status == 4){
                    gamebetlayout1.setVisibility(View.GONE);
                    playnamecommon1.setVisibility(View.GONE);
                    playname1.setVisibility(View.VISIBLE);
                }
            }
            else {
                gamebetlayout1.setVisibility(View.GONE);
                playnamecommon1.setVisibility(View.GONE);
            }


            switch (_type) {
                 case 0:
                    bottomgamelayout.setVisibility(View.VISIBLE);
                    bottomoverlayout.setVisibility(View.GONE);

                    break;
                case 1:

                    bottomgamelayout.setVisibility(View.VISIBLE);
                    bottomoverlayout.setVisibility(View.GONE);
                    break;
                case 2:
                    bottomgamelayout.setVisibility(View.VISIBLE);
                    bottomoverlayout.setVisibility(View.GONE);
                    break;
                case 3:

                    bottomgamelayout.setVisibility(View.GONE);
                    bottomoverlayout.setVisibility(View.VISIBLE);

                    Odds _odd = odd0;
                    String win = "";
                    if(_odd != null)win = _odd.getWin();
                    if(!TextUtils.isEmpty(win) && Integer.parseInt(win)>=0) {
                        playvictoryicon0.setImageResource(winBmp[Integer.parseInt(win)]);
                        playvictoryicon0.setVisibility(View.VISIBLE);
                    }
                    else {
                        playvictoryicon0.setVisibility(View.GONE);
                    }

                    _odd = odd1;
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

        private void setSelect(List<Odds> odds){
            if (odds == null) return;

            boolean _select = false;
            boolean _select1 = false;
            if(groups != null) {
                for (int i = 0; i < groups.size(); i++) {
                    if (odds.get(0)!=null && (odds.get(0).getId() + "").equals(groups.get(i).getTag())) _select = true;
                    if (odds.get(1)!=null && (odds.get(1).getId() + "").equals(groups.get(i).getTag())) _select1 = true;
                }
            }
            if(_select){
                playname0.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetselectbg));
            }
            else{
                playname0.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetbg));
            }
            if(_select1){
                playname1.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetselectbg));
            }
            else{
                playname1.setBackground(mContext.getResources().getDrawable(R.drawable.mathbetbg));
            }
        }

        private void updateArrow(Odds odd, TextView betText, ImageView imggamearrow){
            if(odd == null || betText == null) return;
            int _status     = odd.getStatus();
            String _last    = (String)betText.getText();
            int _tag        = betText.getTag() == null ? -1 : Integer.parseInt((String)betText.getTag());
            String _current = odd.getOdds();

            if( _status == 1
                && _tag == odd.getMatch_id()
                && !TextUtils.isEmpty(_last)
                && !TextUtils.isEmpty(_current)
                && !_last.equalsIgnoreCase(_current)){

                float lastOdds = Float.parseFloat(_last);
                float curOdds  = Float.parseFloat(_current);
                if(curOdds > lastOdds)imggamearrow.setImageResource(R.mipmap.main_courage);
                else imggamearrow.setImageResource(R.mipmap.main_warning);
                Utils.setFlickerAnimation(imggamearrow, 5);
                betText.setTag(odd.getMatch_id());
            }
            else imggamearrow.setVisibility(View.GONE);
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
