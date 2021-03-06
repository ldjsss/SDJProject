package com.lldj.tc.match;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.util.ImageLoader;
import com.lldj.tc.toolslibrary.util.StringUtil;
import com.lldj.tc.toolslibrary.view.ToastUtils;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;


public class Adapter_MainCell extends RecyclerView.Adapter {

    private Context mContext;
    private List<ResultsModel> mlist = new ArrayList<>();
    private viewHolder mHolder = null;
    private int ViewType;
    private String[] statusText;
    private int[] winBmp;
    private Map<String, ObData> groups;
    private int total = 0;

    private Map<Integer, String> oMap = new HashMap<>();
    private LayoutInflater inflater;
    private Map<String, String> mapNames = SharePreUtils.getInstance().getMapNames();
    private ImageLoader imageLoader = new ImageLoader(bActivity, R.mipmap.game_arena, R.mipmap.game_arena);


    public Adapter_MainCell(Context mContext, int _viewType) {
        this.mContext = mContext;
        this.ViewType = _viewType;
        statusText = new String[]{"", mContext.getString(R.string.matchStatusFront), mContext.getString(R.string.matchCurrentTitle), mContext.getString(R.string.matchStatusOver), mContext.getString(R.string.matchStatusError)};
        winBmp = new int[]{R.mipmap.main_failure, R.mipmap.main_victory};
        inflater = LayoutInflater.from(mContext);
        groups = Fragment_Main.selectGroups;
    }

    public void changeData(List<ResultsModel> plist, int total) {
        this.mlist = plist;
        this.total = total;

        AppUtils.dispatchEvent(new ObData(EventType.UPDATEMATCHLIST, mlist));
        notifyDataSetChanged();
    }

    public void updateSelect(List<ObData> _groups){
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

        @BindView(R.id.matchstage)
        TextView matchstage;
        @BindView(R.id.groupname)
        TextView groupname;


        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.playname0, R.id.gamebg, R.id.playname1})
        public void onViewClicked(View view) {
            if(getAdapterPosition() - 1 < 0) return;
            ResultsModel _data = mlist.get(getAdapterPosition() - 1);
            switch (view.getId()) {
                case R.id.gamebg:
                    goDetial(_data.getId());
                    break;
                case R.id.playname0:
                    Object _tag = playname0.getTag();
                    if(_tag == null) return;
                    betAdd(_data, (int)_tag);
                    break;
                case R.id.playname1:
                    Object _tag1 = playname1.getTag();
                    if(_tag1 == null) return;
                    betAdd(_data, (int)_tag1);
                    break;
            }
        }

        private void betAdd(ResultsModel _data, int _tag){
            if(_data == null) return;
            Odds odds = getOddData(_data.getOdds(), _tag);
            if( _data.getOdds() == null || _data.getOdds().size() < 1 || odds == null || odds.getStatus() != 1){
                goDetial(_data.getId());
                return;
            }

            AppUtils.dispatchEvent(new ObData(EventType.BETLISTADD, _data, String.valueOf(odds.getId())));
        }

        private void goDetial(int id){
            if(id <= 0) return;
            Map<String, Integer> _map = new HashMap();
            _map.put("ViewType", ViewType);
            _map.put("id", id);
            AppUtils.dispatchEvent(new ObData(EventType.BETDETAILUI, _map));
        }

        public void bottomCommon(int _type) {
            int _dataPos = getAdapterPosition() - 1;

            ResultsModel _data = mlist.get(_dataPos);
            if(_data == null || _data.getId() <= 0) {
                gamebg.setAlpha(0.0f);
                return;
            }
            gamebg.setAlpha(1.0f);

            gamename.setText(_data.getTournament_name());
            gamenamecount.setText(String.format("/ %s", StringUtil.convertUp(_data.getRound())));
            gameplaycount.setText(String.format("+%s", _data.getPlay_count()));
            gametime.setText(AppUtils.getFormatTime5(_data.getStart_time_ms()));

            imageLoader.getAndSetImage(_data.getGame_logo(), gameicon);

            if(_data.getTeam() == null || _data.getTeam().size() < 2) {
                Toast.makeText(mContext, "--------service Team data error ", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Team> teams = _data.getTeam();
            Collections.sort(teams, (o1, o2) -> { return (int)(o1.getPos() - o2.getPos()); });

            Team team0 = teams.get(0);
            Team team1 = teams.get(1);

            List<Odds> odds = _data.getOdds() != null ? _data.getOdds() : null;
            int matchStatus = _data.getStatus();//1:未开始2:滚盘3:已结束4:已取消或异常

            playnamecommon0.setText(team0.getTeam_name());
            playnamecommon1.setText(team1.getTeam_name());
            playovername0.setText(team0.getTeam_name());
            playovername1.setText(team1.getTeam_name());
            playname0.setText(team0.getTeam_name());
            playname1.setText(team1.getTeam_name());

            playname0.setTag(team0.getTeam_id());
            playname1.setTag(team1.getTeam_id());

            gamestatus.setText(statusText[matchStatus]);
            gamestatusicon.setImageResource(matchStatus == 2 ? R.mipmap.match_status_1 : R.mipmap.match_status_0);
            gameresult.setVisibility((matchStatus == 2 || matchStatus == 3) ? View.VISIBLE : View.GONE);
            gametime.setVisibility((matchStatus == 2 || matchStatus == 3) ? View.GONE : View.VISIBLE);

            imageLoader.getAndSetImage(team0.getTeam_logo(), imgplayicon0);
            imageLoader.getAndSetImage(team1.getTeam_logo(), imgplayicon1);
            gameresult.setText(String.format("%s - %s", team0.getScore().getTotal(), team1.getScore().getTotal()));

            Odds odd0 = getOddData(odds, team0.getTeam_id());
            Odds odd1 = getOddData(odds, team1.getTeam_id());

            if(odd0!=null){
                int _status = odd0.getStatus();
                int _id = odd0.getId();
                String _stage = odd0.getMatch_stage();
                String _oddstring = odd0.getOdds();

                groupname.setText(odd0.getGroup_name());
                String _name = mapNames.get(_stage);
                matchstage.setText(String.format("| %s", TextUtils.isEmpty(_name) ? _stage : _name));

                updateArrow(odd0, oMap.get(_id), gamebet0, imggamearrow0);
                oMap.put(_id, _oddstring);

                gamebet0.setText(_oddstring);
                playname0.setTag(R.id.tag_first, _id);
                if(_status <= 2){
                    imggamelock0.setVisibility(_status == 2 ? View.VISIBLE: View.GONE);
                    gamebet0.setVisibility(_status == 2 ? View.GONE: View.VISIBLE);

                    gamebetlayout0.setVisibility(View.VISIBLE);
                    playnamecommon0.setVisibility(View.VISIBLE);
                    playname0.setText("");
                }
                else if(_status >= 4){
                    gamebetlayout0.setVisibility(View.GONE);
                    playnamecommon0.setVisibility(View.GONE);
                    playname0.setVisibility(View.VISIBLE);
                    playname0.setBackground(mContext.getResources().getDrawable(R.drawable.mathtitle_bg));
                }
            }
            else {
                gamebetlayout0.setVisibility(View.GONE);
                playnamecommon0.setVisibility(View.GONE);
                playname0.setBackground(mContext.getResources().getDrawable(R.drawable.mathtitle_bg));
            }

            if(odd1!=null){
                int _status = odd1.getStatus();
                int _id = odd1.getId();
                String _oddstring = odd1.getOdds();

                updateArrow(odd1, oMap.get(_id), gamebet1, imggamearrow1);
                oMap.put(_id, _oddstring);

                gamebet1.setText(_oddstring);
                playname1.setTag(R.id.tag_first, _id);
                if(_status <= 2){
                    imggamelock1.setVisibility(_status == 2 ? View.VISIBLE: View.GONE);
                    gamebet1.setVisibility(_status == 2 ? View.GONE: View.VISIBLE);

                    gamebetlayout1.setVisibility(View.VISIBLE);
                    playnamecommon1.setVisibility(View.VISIBLE);
                    playname1.setText("");
                }
                else if(_status >= 4){
                    gamebetlayout1.setVisibility(View.GONE);
                    playnamecommon1.setVisibility(View.GONE);
                    playname1.setVisibility(View.VISIBLE);
                    playname1.setBackground(mContext.getResources().getDrawable(R.drawable.mathtitle_bg));
                }
            }
            else {
                gamebetlayout1.setVisibility(View.GONE);
                playnamecommon1.setVisibility(View.GONE);
                playname1.setBackground(mContext.getResources().getDrawable(R.drawable.mathtitle_bg));
            }

            setSelect();

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

        private void setSelect(){
            boolean _select = false;
            boolean _select1 = false;
            if(groups != null) {
                Object _tag = playname0.getTag(R.id.tag_first);
                Object _tag1 = playname1.getTag(R.id.tag_first);
                if(_tag != null && groups.get(String.valueOf(_tag)) != null) _select = true;
                if(_tag1 != null && groups.get(String.valueOf(_tag1)) != null) _select1 = true;
            }
            playname0.setBackground(mContext.getResources().getDrawable(_select ? R.drawable.mathbetselectbg: R.drawable.mathbetbg));
            playname1.setBackground(mContext.getResources().getDrawable(_select1 ? R.drawable.mathbetselectbg: R.drawable.mathbetbg));

        }

        private void updateArrow(Odds odd, String _last, TextView betText, ImageView imggamearrow){
            if(odd == null || betText == null) return;
            int _status     = odd.getStatus();
            String _current = odd.getOdds();

            if( _status <= 1
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
            else imggamearrow.setAlpha(0.0f);
        }

        private Odds getOddData(List<Odds> odds, int team_id) {
            if (odds == null) return null;
            for (int i = 0; i < odds.size(); i++) {
                if (team_id == odds.get(i).getTeam_id()) {
                    return odds.get(i);
                }
            }
            return null;
        }
    }

}
