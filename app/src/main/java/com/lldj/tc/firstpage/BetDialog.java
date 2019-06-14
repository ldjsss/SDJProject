package com.lldj.tc.firstpage;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.Clog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.ViewGroup.FOCUS_BEFORE_DESCENDANTS;

public class BetDialog extends Dialog {

    //View
    private ExpandableListView expandableListView;

    //Model：定义的数据
    private String[] groups = {"A", "B", "C", "D", "E"};

    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    private String[][] childs = {{"A1"}, {"A1"}, {"A1"}, {"A1"}, {"A1"}};
    private Map editTexts = new HashMap();

    public BetDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        //2、设置布局
        View view = View.inflate(context, R.layout.dialog_bet_layout, null);
        setContentView(view);

        Window window = this.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //核心代码是这个属性。
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new BetDialog.MyExpandableListView());
        expandableListView.setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
        expandableListView.setDivider(null);

    }

    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return groups.length;
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            Log.d("smyhvae", "-->" + groupPosition);
            return childs[groupPosition].length;
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        //返回二级列表中的单个item（返回的是对象）
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

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
            } else {

            }

            EditText tv_group = (EditText) convertView.findViewById(R.id.betinput_et);

            String text = editTexts.get(groupPosition) != null ? (String)editTexts.get(groupPosition) : "";
            tv_group.setText(text);

            Clog.e("ggggggg" + groupPosition, " hhhhhhh" + isExpanded);

            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
            }

            for (int i = 0; i <13; i++){
                TextView tv_child = (TextView)convertView.findViewWithTag("tv" + i);
                tv_child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Clog.e("test", "groupPosition" + groupPosition + "ddd" + childPosition + " i = " + tv_child.getTag());
                        btnClick(groupPosition, (String)tv_child.getTag());
                    }
                });
            }

            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            Clog.e("sssssssssss", "ffffffffffff" + groupPosition + "ddd" + childPosition);
            return true;
        }

        private void btnClick(int groupPosition, String tag){
            if (groupPosition < 0 || tag == null) return;

            String _tag = tag.substring(2, tag.length());
            Clog.e("btnClick", "groupPosition" + groupPosition + "tag" + _tag);
            switch (_tag){
                case "10":

                    break;
                case "11":

                    break;
                case "12":

                    break;
                default:
                    String text = editTexts.get(groupPosition) != null ? (String)editTexts.get(groupPosition) : "";
                    editTexts.put(groupPosition, text + _tag);
                    notifyDataSetChanged();
                    break;
            }
        }
    }

}
