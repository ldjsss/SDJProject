package com.lldj.tc.match;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BetMatchBean;
import com.lldj.tc.http.beans.MapBean;
import com.lldj.tc.info.Dialog_Set;
import com.lldj.tc.login.Activity_Login;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.match.DialogBet;
import com.lldj.tc.match.DialogGameSelect;
import com.lldj.tc.match.Fragment_ViewPager;
import com.lldj.tc.match.Frament_MatchDetail;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Activity_MainUI extends BaseActivity implements HandlerInter.HandleMsgListener {

    @BindView(R.id.mainflayout)
    FrameLayout mainflayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.mainright)
    RelativeLayout mainright;

    private DialogBet dialogBet;
    private Frament_MatchDetail detailDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler.setHandleMsgListener(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);        // gesture sliding is forbidden
        getSupportFragmentManager().beginTransaction().add(R.id.mainflayout, new Fragment_ViewPager()).commit();

        registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.BETDETAILUI)) {
                    Map<String, Integer> _map = (Map) data.getValue();

                    detailDialog = new Frament_MatchDetail();
                    getSupportFragmentManager().beginTransaction().add(R.id.mainright, detailDialog).commit();
                    detailDialog.showView(_map.get("ViewType"), _map.get("id"));
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
                else if (data.getKey().equalsIgnoreCase(EventType.DETIALHIDE)) {
                    if(detailDialog == null) return;
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    getSupportFragmentManager().beginTransaction().remove(detailDialog).commit();
                    detailDialog = null;
                }
                else if (data.getKey().equalsIgnoreCase(EventType.BETLISTADD)) {
                    if(TextUtils.isEmpty(SharePreUtils.getInstance().getUserId())){
                        guestWarm();
                        return;
                    }

                    if(dialogBet == null) {
                        dialogBet = new DialogBet(mContext, R.style.DialogTheme);
                    }
                    DialogManager.getInstance().show(dialogBet);
                    dialogBet.betListAdd(data);
                }
                else if (data.getKey().equalsIgnoreCase(EventType.SELECTGROUPS)) {
                    List<ObData> groups = (List<ObData>)data.getValue();
                    if(groups == null || groups.size() <=0 ) {
                        DialogManager.getInstance().removeDialog("dialogBet");
                        DialogManager.getInstance().removeDialog("dialogBetBottom");
                        dialogBet = null;
                    }
                }
            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case HandlerType.LEFTMENU:
                if(TextUtils.isEmpty(SharePreUtils.getInstance().getUserId())){
                    guestWarm();
                    return;
                }
                DialogManager.getInstance().show(new Dialog_Set(this, R.style.DialogTheme));
                break;
            case HandlerType.LEFTBACK:
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                break;
            case HandlerType.LOADING:
//                AppUtils.showLoading(bActivity);
                break;
            case HandlerType.GAMESELECT:
                DialogManager.getInstance().show(new DialogGameSelect(mContext, R.style.DialogTheme));
                break;
            case HandlerType.LEAVEGAME:
                SharePreUtils.setToken(this, "");
                SharePreUtils.getInstance().setUserId("");
                startActivity(new Intent(mContext, Activity_Login.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(null);
        DialogManager.getInstance().removeAll();
    }

    private void guestWarm(){

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialogStyle)
//                .setTitle("我是Title")
                .setMessage(getResourcesString(R.string.cannotbet))
                .setPositiveButton(getResourcesString(R.string.sure), null)
                .setNegativeButton(getResourcesString(R.string.btncancle), null)
                .create();
        dialog.show();
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mController = mAlert.get(dialog);
            Field mMessage = mController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mController);
            mMessageView.setTextColor(Color.WHITE);//message样式修改成红色
            Field mTitle = mController.getClass().getDeclaredField("mTitle");
            mMessage.setAccessible(true);
            TextView mTitleView = (TextView) mMessage.get(mController);
            mMessageView.setTextColor(Color.WHITE);//title样式修改成``色
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        final Button pButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);//确认按键
        pButton.setTextColor(Color.WHITE);
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEAVEGAME);
            }
        });
        Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);//取消
        nButton.setTextColor(Color.WHITE);
    }

}
