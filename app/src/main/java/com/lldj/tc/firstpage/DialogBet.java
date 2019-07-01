package com.lldj.tc.firstpage;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.Odds;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.FOCUS_BEFORE_DESCENDANTS;

public class DialogBet extends Dialog {

    @BindView(R.id.gamebettotalcount)
    TextView gamebettotalcount;
    @BindView(R.id.moneyhave)
    TextView moneyhave;
    private ExpandableListView expandableListView;
    private Map<String, BetModel> betList = new HashMap();

    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    private String[][] childs = {{"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}};
    private Observer<ObData> observer;
    private List<ObData> groups = new ArrayList<>();
    private MyExpandableListView _myExpandableListView;

    int screenHeight;
    int maxHeight = 0;
    int _oneHeight = 0;

    public DialogBet(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        screenHeight = AppUtils.getDisplayMetrics(getContext()).heightPixels;

        View view = View.inflate(context, R.layout.dialog_bet_layout, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_bet_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //核心代码是这个属性。
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);

        _myExpandableListView = new MyExpandableListView();
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(_myExpandableListView);
        expandableListView.setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
        expandableListView.setDivider(null);

        View countview = LayoutInflater.from(context).inflate(R.layout.item_child, null);
        int _height = expandableListView.getLayoutParams().height;
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = expandableListView.getExpandableListAdapter().getGroupCount();
                for(int j = 0; j < count; j++){
                    if(j != groupPosition){
                        expandableListView.collapseGroup(j);
                    }
                }

            }
        });

        observer = new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.BETLISTADD)) {
                    if(groups.size()>childs.length){
                        ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, getContext().getResources().getString(R.string.maxSelect), ToastUtils.LENGTH_SHORT);
                        return;
                    }
                    if(! addDataToGroups(data)){
                        Log.d("addDataToGroups", "remove have add cell");
                    }
                    update();
                    if(expandableListView != null) expandableListView.expandGroup(0);
                }
            }
        };
        AppUtils.registEvent(observer);

        moneyhave.setText(context.getResources().getString(R.string.moneyHave) + SharePreUtils.getMoney(context));

    }

    private Boolean addDataToGroups(ObData data){
        Log.d("addDataToGroups", data.toString());
        for (int i = 0; i < groups.size(); i++) {
            if(groups.get(i).getTag().equals(data.getTag())){
                groups.remove(i);
                return false;
            }
        }

        groups.add(0, data);
        return true;
    }

    private void update(){
        ObData _data = new ObData(EventType.BETCHANGE, betList);
        _data.setTag(groups.size()+"");
        AppUtils.dispatchEvent(_data);

        AppUtils.dispatchEvent(new ObData(EventType.SELECTGROUPS, groups));

        if(groups.size()<=0){
            if(groups.size()<=0) {
                AppUtils.dispatchEvent(new ObData(EventType.SELECTGROUPS, null));
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.DELETEBETDIA);
            }
            return;
        }

        gamebettotalcount.setText(groups.size() + "");

        if(maxHeight <= 0) maxHeight = screenHeight - findViewById(R.id.viewlayout).getLayoutParams().height - findViewById(R.id.bettitlelayout).getLayoutParams().height;
        if(_oneHeight <= 0){
            View countview = LayoutInflater.from(getContext()).inflate(R.layout.item_group, null);
            _oneHeight = countview.findViewById(R.id.groupLayout).getLayoutParams().height;
        }
        int newHeight =(int)( _oneHeight*(groups.size()+1.2));
        if(newHeight > maxHeight)newHeight = maxHeight;
        if(expandableListView != null)expandableListView.getLayoutParams().height = newHeight;

        _myExpandableListView.notifyDataSetChanged();
    }

    public List<ObData> getGroups(){
        return groups;
    }

    @OnClick({R.id.deleteall, R.id.closelayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deleteall:
                AppUtils.dispatchEvent(new ObData(EventType.SELECTGROUPS, null));
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.DELETEBETDIA);
                break;
            case R.id.closelayout:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.HIDEBETDIA);
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        AppUtils.unregisterEvent(observer);
        observer = null;
    }

    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            Log.d("smyhvae", "-->" + groupPosition);
            return childs[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs[groupPosition][childPosition];  //不要误写成groups[groupPosition][childPosition]
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ObData data = groups.get(groupPosition);
            ResultsModel _data = (ResultsModel) data.getValue();
            String ID = data.getTag();

            Odds odd = getTeamOddsByID(_data, ID);
            BetModel betinfo = betList.get(ID);

            if (odd == null) return convertView;

//            Clog.e("---- " + groupPosition, data.toString());

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
            }
            TextView tv_group = (TextView) convertView.findViewById(R.id.betinput_et);
            tv_group.setText(betinfo==null?"":betinfo.getAmount()+"");

            TextView tv_groupName = (TextView) convertView.findViewById(R.id.tv_groupgame);
            tv_groupName.setText(TextUtils.isEmpty(odd.getName()) ? "unkonw" : odd.getName());

            TextView tv_groupplays = (TextView) convertView.findViewById(R.id.tv_groupplays);
            tv_groupplays.setText(odd.getGroup_name());

            TextView tv_groupteams = (TextView) convertView.findViewById(R.id.tv_groupteams);
            tv_groupteams.setText(_data.getMatch_name());

            TextView betpercent = (TextView) convertView.findViewById(R.id.betpercent);
            betpercent.setText("@" + odd.getOdds());

            TextView betwildgettv = (TextView) convertView.findViewById(R.id.betwildgettv);
            betwildgettv.setText(betinfo==null?"0":betinfo.getWillget()+"");

//            Clog.e("ggggggg " + groupPosition, " hhhhhhh " + isExpanded);

            ((ImageView) convertView.findViewById(R.id.iv_groupdelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObData data = groups.get(groupPosition);
                    String ID = data.getTag();
                    groups.remove(groupPosition);
                    betList.remove(ID);
                    update();
                }
            });

            return convertView;
        }

        private Odds getTeamOddsByID(ResultsModel _data, String ID) {
            for (int i = 0; i < ((List<Odds>)_data.getOdds()).size(); i++) {
                if (((List<Odds>)_data.getOdds()).get(i).getId() == Integer.parseInt(ID))
                    return ((List<Odds>)_data.getOdds()).get(i);
            }

            return null;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
            }

            for (int i = 0; i < 13; i++) {
                TextView tv_child = (TextView) convertView.findViewWithTag("tv" + i);

                tv_child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Clog.e("test", "groupPosition" + groupPosition + "ddd" + childPosition + " i = " + tv_child.getTag());
                        btnClick(groupPosition, (String) tv_child.getTag());
                    }
                });
            }

            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private void btnClick(int groupPosition, String tag) {
            if (groupPosition < 0 || tag == null) return;

            ObData data = groups.get(groupPosition);
            ResultsModel _data = (ResultsModel) data.getValue();
            String ID = data.getTag();
            BetModel betinfo = betList.get(ID);
            Odds odd = getTeamOddsByID(_data, ID);

            String _tag = tag.substring(2, tag.length());
            String text = betinfo != null ? betinfo.getAmount()+"" : "";
            Clog.e("btnClick", "groupPosition" + groupPosition + "tag" + _tag);

            switch (_tag) {
                case "10":

                    break;
                case "11":
                    if (TextUtils.isEmpty(text)) return;
                    text = text.substring(0, text.length() - 1);
                    break;
                case "12":

                    break;
                default:
                    if (TextUtils.isEmpty(text) && _tag.equalsIgnoreCase("0")) return;
                    text = text + _tag;
                    break;
            }

            if(!_tag.equals("12")){
                int willGet = 0;
                if(!TextUtils.isEmpty(text) && Float.parseFloat(text) > 0) {
                    willGet = (int)(Float.parseFloat(text) * Float.parseFloat(odd.getOdds()));
                    betList.put(ID, new BetModel((int)Float.parseFloat(text), Integer.parseInt(ID), willGet, odd.getBet_max(), odd.getBet_min(), odd.getName()));
                }else
                {
                    betList.put(ID, null);
                }
            }
            update();
        }
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void show() {
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
    }

}
