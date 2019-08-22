package com.lldj.tc.match;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BordBean;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.PageMatchBean;
import com.lldj.tc.info.Activity_Center;
import com.lldj.tc.login.Activity_Login;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.time.BasicTimer;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.CustomDialog;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.util.Collections;
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

    private BasicTimer disposable;

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
                        dialogBet = new DialogBet(Activity_MainUI.this, R.style.DialogTheme);
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
                else if (data.getKey().equalsIgnoreCase(EventType.STORTBOARD)) {
                    startBord();
                }
            }
        });

    }

    private void getBord(){
        final HttpMsg.Listener cal = new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                BordBean res = (BordBean) _res;
                if (res != null && res.getCode() == GlobalVariable.succ) {
                    AppUtils.dispatchEvent(new ObData(EventType.BORDLIST, res.getResult()));
                }
            }
        };

        HttpMsg.getInstance().sendGetBords(BordBean.class, cal);
    }

    private void startBord(){
        if(disposable != null) return;
        getBord();
        disposable = new BasicTimer(new BasicTimer.BasicTimerCallback() {
            @Override
            public void onTimer() {
                getBord();
            }
        });
        disposable.start(10000);
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case HandlerType.LEFTMENU:
                if(TextUtils.isEmpty(SharePreUtils.getInstance().getUserId())){
                    guestWarm();
                    return;
                }
                startActivity(new Intent(mContext, Activity_Center.class));
                break;
            case HandlerType.LEFTBACK:
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(Activity_MainUI.this, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                int _code = msg.getData().getInt("code", 0);
                if(_code == 401) goBackToLogin();
                break;
            case HandlerType.LOADING:
                AppUtils.showLoading(this);
                break;
            case HandlerType.GAMESELECT:
                startActivity(new Intent(mContext, ActivityGameSelect.class));
                break;
            case HandlerType.LEAVEGAME:
                goBackToLogin();
                break;
        }
    }

    private void goBackToLogin(){
        SharePreUtils.setToken(Activity_MainUI.this, "");
        SharePreUtils.getInstance().setUserId("");
        startActivity(new Intent(Activity_MainUI.this, Activity_Login.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(null);
        DialogManager.getInstance().removeAll();
        if(disposable != null){
            disposable.cancel();
            disposable = null;
        }
    }

    private void guestWarm() {

        CustomDialog customDialog = new CustomDialog(Activity_MainUI.this, R.style.MyDialogStyle);
        customDialog.setTitle(getResourcesString(R.string.app_name));
        customDialog.setMessage(getResourcesString(R.string.cannotbet));
        customDialog.setCancel(getResourcesString(R.string.btncancle), new CustomDialog.IOnCancelListener() {
            @Override
            public void onCancel(CustomDialog dialog) {
            }
        });
        customDialog.setConfirm(getResourcesString(R.string.sure), new CustomDialog.IOnConfirmListener(){
            @Override
            public void onConfirm(CustomDialog dialog) {
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEAVEGAME);
            }
        });
        customDialog.show();

    }

}
