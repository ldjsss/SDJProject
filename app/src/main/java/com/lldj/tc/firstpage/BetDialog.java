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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;

public class BetDialog extends Dialog{

//    //View
//    private ExpandableListView expandableListView;
//
//    //Model：定义的数据
//    private String[] groups = {"A", "B", "C", "D", "E"};
//
//    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
//    private String[][] childs = {{"A1", "A2", "A3", "A4"}, {"A1", "A2", "A3", "B4"}, {"A1", "A2", "A3", "C4"}, {"A1", "A2", "A3", "C4"}, {"A1", "A2", "A3", "C4"}};
//


    public BetDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        //2、设置布局
        View view = View.inflate(context, R.layout.dialog_bet_layout,null);
        setContentView(view);

        Window window = this.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //核心代码是这个属性。
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);

//        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
//
//        expandableListView.setAdapter(new BetDialog.MyExpandableListView());
    }

//    //为ExpandableListView自定义适配器
//    class MyExpandableListView extends BaseExpandableListAdapter {
//
//        //返回一级列表的个数
//        @Override
//        public int getGroupCount() {
//            return groups.length;
//        }
//
//        //返回每个二级列表的个数
//        @Override
//        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
//            Log.d("smyhvae", "-->" + groupPosition);
//            return childs[groupPosition].length;
//        }
//
//        //返回一级列表的单个item（返回的是对象）
//        @Override
//        public Object getGroup(int groupPosition) {
//            return groups[groupPosition];
//        }
//
//        //返回二级列表中的单个item（返回的是对象）
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return childs[groupPosition][childPosition];  //不要误写成groups[groupPosition][childPosition]
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        //每个item的id是否是固定？一般为true
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        //【重要】填充一级列表
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//
//            if (convertView == null) {
//                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
//            } else {
//
//            }
//            TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group);
//            tv_group.setText(groups[groupPosition]);
//            return convertView;
//        }
//
//        //【重要】填充二级列表
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//
//            if (convertView == null) {
//                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
//            }
//
//            ImageView iv_child = (ImageView) convertView.findViewById(R.id.iv_child);
//            TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);
//
//            //iv_child.setImageResource(resId);
//            tv_child.setText(childs[groupPosition][childPosition]);
//
//            return convertView;
//        }
//
//        //二级列表中的item是否能够被选中？可以改为true
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return true;
//        }
//    }

}
