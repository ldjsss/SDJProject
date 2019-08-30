package com.lldj.tc.match;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.MatchBean;
import com.lldj.tc.info.Activity_Webview;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityGameSelect extends BaseActivity {
    private List<ResultsModel> list;
    private Adapter_GameSelect adapter;
    @BindView(R.id.gamerecycleview)
    RecyclerView gamerecycleview;
    @BindView(R.id.maintoptitlelayout)
    LinearLayout maintoptitlelayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gameselect);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.in_from_top,0);

    }


    @Override
    protected void initData() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).titleBar(maintoptitlelayout).init();

        HttpMsg.getInstance().sendGetGameList(MatchBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                MatchBean res = (MatchBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    list = res.getResult();

                    if(adapter == null) adapter = new Adapter_GameSelect(mContext, list);
                    gamerecycleview.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                    gamerecycleview.setAdapter(adapter);
                    gamerecycleview.setItemAnimator(new DefaultItemAnimator());

                    Map<Integer,ResultsModel > _map = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        _map.put(list.get(i).getId(), list.get(i));
                    }
                    SharePreUtils.getInstance().setGamelist(_map);
                }
            }
        });

    }

    @OnClick({R.id.toolbar_left_menu_iv, R.id.toolbar_gameselect, R.id.connectservice, R.id.tv_selectgame})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_menu_iv:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEFTMENU);
                break;
            case R.id.toolbar_gameselect:
                finish();
                overridePendingTransition(0, R.anim.out_to_top);
                break;
            case R.id.connectservice:
//                Toast.makeText(mContext,"---------------test2",Toast.LENGTH_SHORT).show();
                String url = "http://chat.yjwl.ltd:7070/index/index/mobile?id=14&name=%E4%BC%9A%E5%91%9814&group=1&avatar=http%3A%2F%2Fwx2.sinaimg.cn%2Fmw690%2F5db11ff4gy1flxmew7edlj203d03wt8n.jpg";
                Intent intent2 = new Intent(mContext, Activity_Webview.class);
                intent2.putExtra("url", url);
                intent2.putExtra("title", getResources().getString(R.string.connectservice));
                startActivity(intent2);
                break;
            case R.id.tv_selectgame:
                String selects = adapter.getSelect();
                SharePreUtils.getInstance().setSelectGame(mContext, selects);
                if(adapter != null) AppUtils.dispatchEvent(new ObData(EventType.SELECTGAMEID, selects));
                finish();
                overridePendingTransition(0, R.anim.out_to_top);
                break;
        }
    }
}
