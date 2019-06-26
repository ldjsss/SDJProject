package com.lldj.tc.firstpage;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.BetModel;
import com.lldj.tc.mainUtil.EventType;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBetBottom extends Dialog {
    @BindView(R.id.betcouttv)
    TextView betcouttv;
    @BindView(R.id.betgetcouttv)
    TextView betgetcouttv;
    @BindView(R.id.betsurebtn)
    TextView betsurebtn;
    @BindView(R.id.betbottomlayout1)
    RelativeLayout betbottomlayout1;
    @BindView(R.id.haveselecttitle)
    TextView haveselecttitle;
    @BindView(R.id.betselectcount)
    TextView betselectcount;
    @BindView(R.id.lookbtn)
    TextView lookbtn;
    @BindView(R.id.betbottomlayout2)
    RelativeLayout betbottomlayout2;
    private Observer<ObData> observer;
    private Map<String, BetModel> betList = new HashMap();
    private int totalBet = 0;
    private float totalGet = 0;
    private String selectCount = "0";

    public DialogBetBottom(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialogbetbottom, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //核心代码是这个属性。
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);

        observer = new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.BETCHANGE)) {
                    betList = (Map<String, BetModel>) data.getValue();

                    totalBet = 0;
                    totalGet = 0;
                    selectCount = data.getTag();

                    Iterator<Map.Entry<String, BetModel>> entries = betList.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry<String, BetModel> entry = entries.next();
                        Clog.e("--key " + entry.getKey(), " ---value " + (entry.getValue() == null ? "null" : entry.getValue().toString()));
                        if (entry.getValue() != null) {
                            totalBet += entry.getValue().getAmount();
                            totalGet += entry.getValue().getWillget();
                        }
                    }

                    update();
                }
                else if(data.getKey().equalsIgnoreCase(EventType.HIDEBETLIST)){
                    betbottomlayout1.setVisibility(View.GONE);
                    betbottomlayout2.setVisibility(View.VISIBLE);
                }
            }
        };
        AppUtils.registEvent(observer);
    }

    private void update() {
        betcouttv.setText(totalBet + "");
        betgetcouttv.setText(totalGet + "");
        betselectcount.setText(selectCount);
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

    @Override
    public void dismiss() {
        super.dismiss();
        AppUtils.unregisterEvent(observer);
        observer = null;
    }

    @OnClick({R.id.betsurebtn, R.id.betbottomlayout2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.betsurebtn:
                Log.d("betsurebtn", "-->");
                break;
            case R.id.betbottomlayout2:
                Log.d("betbottomlayout2", "-->");
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                betbottomlayout1.setVisibility(View.VISIBLE);
                betbottomlayout2.setVisibility(View.GONE);

                break;
        }
    }
}
