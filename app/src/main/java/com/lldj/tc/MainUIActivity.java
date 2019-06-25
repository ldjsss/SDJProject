package com.lldj.tc;

import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.widget.FrameLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import com.lldj.tc.firstpage.BetDialog;
import com.lldj.tc.firstpage.FragmentSet;
import com.lldj.tc.firstpage.FragmentViewPager;
import com.lldj.tc.firstpage.MatchDetailDialog;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainUIActivity extends BaseActivity implements HandlerInter.HandleMsgListener {

    @BindView(R.id.mainflayout)
    FrameLayout mainflayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private BetDialog betDialog;

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
        getSupportFragmentManager().beginTransaction().add(R.id.mainflayout, new FragmentViewPager()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainleft, new FragmentSet()).commit();

        registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.BETDETAILUI)) {
                    Map<String, Integer> _map = (Map)data.getValue();
                    new MatchDetailDialog(mContext, R.style.DialogTheme).showView(_map.get("ViewType"), _map.get("id"));

                    if(betDialog != null){
                        List<ObData> groups = betDialog.getGroups();
                        HandlerInter.getInstance().sendEmptyMessage(HandlerType.DELETEBETDIA);
                        RxTimerUtilPro.timer(100, new RxTimerUtilPro.IRxNext() {
                            @Override
                            public void doNext(long number) {
                                Message message=new Message();
                                message.what=HandlerType.SHOWBETDIA;
                                message.obj = groups;
                                HandlerInter.getInstance().sendMessage(message);
                            }

                            @Override
                            public void onComplete() { }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case HandlerType.LEFTMENU:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case HandlerType.LEFTBACK:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                break;
            case HandlerType.LOADING:
                AppUtils.showLoading(mContext);
                break;
            case HandlerType.SHOWBETDIA:
                if(betDialog!=null && betDialog.isShowing()) return;
                List<ObData> groups = null;
                if(msg.obj!=null)groups = (List<ObData>)msg.obj;
                betDialog = new BetDialog(this, R.style.DialogTheme);
                betDialog.showView(groups);
                break;
            case HandlerType.HIDEBETDIA:
                if(betDialog == null) return;
                betDialog.hide();
                break;
            case HandlerType.DELETEBETDIA:
                if(betDialog == null) return;
                betDialog.dismiss();
                betDialog = null;
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

//post请求数据
//    JSONObject body = new JSONObject();
//    body.put("username", "panghao");
//    body.put("password", "12345");


