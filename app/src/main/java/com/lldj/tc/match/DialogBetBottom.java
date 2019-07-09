package com.lldj.tc.match;

import android.content.Context;
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
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BetMatchBean;
import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBetBottom extends BaseDialog {
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
        window.setWindowAnimations(R.style.main_bet_animStyle2);
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
                if(totalBet <= 0){
                    ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, getContext().getResources().getString(R.string.inputBetNum), ToastUtils.LENGTH_SHORT);
                    return;
                }
                JSONArray arrayList=new JSONArray();
                Iterator<Map.Entry<String, BetModel>> entries = betList.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, BetModel> entry = entries.next();

                    BetModel value = entry.getValue();
                    if (value != null) {
                        if(value.getAmount() < value.getBet_min()) {
                            ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, value.getName() + getContext().getResources().getString(R.string.betminnum) + value.getBet_min(), ToastUtils.LENGTH_SHORT);
                            return;
                        }
                        else if(value.getAmount() > value.getBet_max()){
                            ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, value.getName() + getContext().getResources().getString(R.string.betmaxnum) + value.getBet_max(), ToastUtils.LENGTH_SHORT);
                            return;
                        }

                        arrayList.put(value.getJSONObject());
                    }
                }
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("datas", arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            //"{\"datas\": [{\"amount\": 0,\"oddsid\": 85759}]}"
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendBetList(SharePreUtils.getToken(getContext()), jsonObj.toString(), BetMatchBean.class, new HttpMsg.Listener(){
                    @Override
                    public void onFinish(Object _res) {
                        BetMatchBean res = (BetMatchBean) _res;
                        if(res.getCode() == GlobalVariable.succ){
                            new DialogBetResult(getContext(), R.style.DialogTheme).showView(res.getResult());
                            removeHaveBet(res.getResult());
                        }
                    }
                });
                break;
            case R.id.betbottomlayout2:
                Log.d("betbottomlayout2", "-->");
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.SHOWBETDIA);
                betbottomlayout1.setVisibility(View.VISIBLE);
                betbottomlayout2.setVisibility(View.GONE);

                break;
        }
    }

    // because this list is used as a reference, it can be removed here and written here for the time being. This usage is not encouraged
    private void removeHaveBet(List<BetModel> list){
        if(list == null) return;
        BetModel betInfo, listInfo;
        for (int i = 0; i < list.size(); i++) {
            betInfo = list.get(i);
            for (int j = 0; j < betList.size(); j++) {
                listInfo = betList.get(j);
                if(betInfo.getCode() == GlobalVariable.succ && betInfo.getOddsid() == listInfo.getOddsid()) {
                    betList.remove(j);
                }
            }
        }

    }
}
