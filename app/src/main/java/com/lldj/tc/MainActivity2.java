package com.lldj.tc;

import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.lldj.tc.firstpage.FragmentSet;
import com.lldj.tc.firstpage.FragmentViewPager;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;


public class MainActivity2 extends BaseActivity implements HandlerInter.HandleMsgListener {

    @BindView(R.id.mainflayout)
    FrameLayout mainflayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    Disposable sss;
    Disposable ddd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mHandler.setHandleMsgListener(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);        //禁止手势滑动
        getSupportFragmentManager().beginTransaction().add(R.id.mainflayout, new FragmentViewPager()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainleft, new FragmentSet()).commit();

//         sss = RxTimerUtilPro.interval(1000, new RxTimerUtilPro.IRxNext() {
//            @Override
//            public void doNext(long number) {
//                Log.w("-----ssssss", "dddddd 11111111");
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
//
//         ddd = RxTimerUtilPro.interval(1000, new RxTimerUtilPro.IRxNext() {
//            @Override
//            public void doNext(long number) {
//                Log.w("-----ssssss", "dddddd 22222");
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case HandlerType.LEFTMENU:
                drawerLayout.openDrawer(Gravity.LEFT);

//                String s = "{\"error\":0,\"status\":\"success\",\"results\":[{\"currentCity\":\"青岛\",\"index\":[{\"title\":\"穿衣\",\"zs\":\"较冷\",\"tipt\":\"穿衣指数\",\"des\":\"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\"},{\"title\":\"紫外线强度\",\"zs\":\"最弱\",\"tipt\":\"紫外线强度指数\",\"des\":\"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。\"}]}]}";
//                Gson gson = new Gson();
//                //把JSON数据转化为对象
//                JsonBean jsonBean = gson.fromJson(s, JsonBean.class);
//
//                Log.w("-----ssssss", jsonBean.getResults().get(0).getCurrentCity());

//                HttpMsg.test(new HttpTool.msgListener(){
//                    @Override
//                    public void onFinish(int code, String msg) {
//                        Log.w("-----code", code + "");
//                        Log.w("-----msg", msg + "");
//                        Toast.makeText(mContext,"---------------test1",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                RxTimerUtilPro.cancel(sss);
//                RxTimerUtilPro.cancel(ddd);
                break;
            case HandlerType.LEFTBACK:
                drawerLayout.closeDrawer(Gravity.LEFT);
            case HandlerType.SHOWTOAST:

                Toast.makeText(this, msg.getData().getString("msg"), Toast.LENGTH_SHORT).show();
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


