package com.lldj.tc;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.lldj.tc.firstpage.FragmentSet;
import com.lldj.tc.firstpage.FragmentViewPager;
import com.lldj.tc.handler.HandlerInter;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity2 extends BaseActivity implements HandlerInter.HandleMsgListener {

    @BindView(R.id.mainflayout)
    FrameLayout mainflayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private HandlerInter mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        mHandler = HandlerInter.getInstance();
        mHandler.setHandleMsgListener(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);        //禁止手势滑动
        getSupportFragmentManager().beginTransaction().add(R.id.mainflayout, new FragmentViewPager()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainleft, new FragmentSet()).commit();

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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁移除所有消息，避免内存泄露
        mHandler.removeCallbacks(null);
    }
}
