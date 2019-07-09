package com.lldj.tc;

import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import com.lldj.tc.match.Adapter_Set;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.match.DialogBet;
import com.lldj.tc.match.DialogBetBottom;
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
    private DialogBetBottom dialogBetBottom;
    private DialogGameSelect dialogGameSelect;
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

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);        //禁止手势滑动
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
                } else if (data.getKey().equalsIgnoreCase(EventType.DETIALHIDE)) {
                    if(detailDialog == null) return;
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    getSupportFragmentManager().beginTransaction().remove(detailDialog).commit();
                    detailDialog = null;
                }
            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case HandlerType.LEFTMENU:
//                new DialogSet(this, R.style.DialogTheme).show();
                Adapter_Set.showView(this);
                break;
            case HandlerType.LEFTBACK:
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                break;
            case HandlerType.LOADING:
                AppUtils.showLoading(mContext);
                break;
            case HandlerType.SHOWBETDIA:
                if (dialogBet == null) dialogBet = new DialogBet(this, R.style.DialogTheme);
                dialogBet.show();
                if (dialogBetBottom == null)
                    dialogBetBottom = new DialogBetBottom(this, R.style.DialogTheme);
                dialogBetBottom.show();
                break;
            case HandlerType.HIDEBETDIA:
                if (dialogBet != null) dialogBet.hide();
                AppUtils.dispatchEvent(new ObData(EventType.HIDEBETLIST, null));
                break;
            case HandlerType.DELETEBETDIA:
                if (dialogBet != null) dialogBet.dismiss();
                dialogBet = null;
                if (dialogBetBottom != null) dialogBetBottom.dismiss();
                dialogBetBottom = null;
                break;
            case HandlerType.GAMESELECT:
                if (dialogGameSelect != null) return;
                dialogGameSelect = new DialogGameSelect(mContext, R.style.DialogTheme);
                dialogGameSelect.showView();
                break;
            case HandlerType.HIDGAMESELECT:
                if (dialogGameSelect == null) return;
                dialogGameSelect.dismiss();
                dialogGameSelect = null;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁移除所有消息，避免内存泄露
        mHandler.removeCallbacks(null);
    }

}
