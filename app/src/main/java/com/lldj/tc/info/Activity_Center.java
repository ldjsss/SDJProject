package com.lldj.tc.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.http.beans.UrlBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.utils.GlobalVariable;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Activity_Center extends BaseActivity {

    @BindView(R.id.moneycur)
    TextView moneycur;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.hellow)
    TextView hellow;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_set);

        ButterKnife.bind(this);

        overridePendingTransition(R.anim.in_from_left,0);
    }


    @Override
    protected void initData() {
        ButterKnife.bind(this);

        version.setText(AppUtils.getChannel(mContext) + "(" + AppUtils.getVersionName(mContext) + ")");
        moneycur.setText("¥" + SharePreUtils.getMoney(mContext));
        tvname.setText(SharePreUtils.getUserName(mContext));
        hellow.setText(getGreetings());

        ImmersionBar.with(bActivity).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(bActivity.getResources().getString(R.string.myinformation));

        HttpMsg.getInstance().sendGetUserInfo(mContext, SharePreUtils.getInstance().getToken(mContext), JsonBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    moneycur.setText("¥" + SharePreUtils.getMoney(mContext));
                    tvname.setText(SharePreUtils.getUserName(mContext));
                }
            }
        });

        HttpMsg.getInstance().sendGetUrl(SharePreUtils.getInstance().getToken(mContext), UrlBean.class, null);

    }

    private String getGreetings() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String ret = "hello";
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= 0 && a <= 6) {
            ret = mContext.getString(R.string.goodinthenmorning);
        }
        if (a > 6 && a <= 12) {
            ret = mContext.getString(R.string.goodmorning);
        }
        if (a > 12 && a <= 13) {
            ret = mContext.getString(R.string.goodafternoon);
        }
        if (a > 13 && a <= 18) {
            ret = mContext.getString(R.string.Goodafternoon);
        }
        if (a > 18 && a <= 24) {
            ret = mContext.getString(R.string.goodnight);
        }

        return ret;
    }

    @OnClick({R.id.packed_iv, R.id.packlayout, R.id.recordlayout, R.id.setiv, R.id.toolbar_back_iv,
            R.id.messlayout, R.id.activitylayout, R.id.rulelayout, R.id.aboutlayout, R.id.agency, R.id.share, R.id.settv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                overridePendingTransition(0, R.anim.out_to_left);
                break;
            case R.id.setiv:
            case R.id.settv:
                mContext.startActivity(new Intent(mContext, Activity_Info.class));
                break;
            case R.id.packlayout:
//                Intent _intent = new Intent(getContext(), Activity_Shop.class);
//                _intent.putExtra("Anim_fade", R.style.Anim_fade);
//                getContext().startActivity(_intent);
                Intent intent0 = new Intent(mContext, Activity_Webview.class);
                intent0.putExtra("url", SharePreUtils.getInstance().getRecharge_url());
                intent0.putExtra("title", mContext.getResources().getString(R.string.wallet));
                startActivity(intent0);
                break;
            case R.id.recordlayout:
                startActivity(new Intent(mContext, Activity_Records.class));
                break;
            case R.id.messlayout:
                startActivity(new Intent(mContext, Activity_Mess.class));
                break;
            case R.id.activitylayout:
                startActivity(new Intent(mContext, Activity_Activitys.class));
                break;
            case R.id.rulelayout:
                Intent intent = new Intent(mContext, Activity_Webview.class);
                intent.putExtra("url", SharePreUtils.getInstance().getRule_url());
                intent.putExtra("title", getResources().getString(R.string.rules));
                startActivity(intent);
                break;
            case R.id.aboutlayout:
                Intent intent1 = new Intent(mContext, Activity_Webview.class);
                intent1.putExtra("url", SharePreUtils.getInstance().getAbout_url());
                intent1.putExtra("title", getResources().getString(R.string.about));
                startActivity(intent1);
                break;
            case R.id.agency:
                Intent intent2 = new Intent(mContext, Activity_Webview.class);
                intent2.putExtra("url", SharePreUtils.getInstance().getAgent_url());
                intent2.putExtra("title", getResources().getString(R.string.agency));
                startActivity(intent2);
                break;
            case R.id.share:
                Intent intent3 = new Intent(mContext, Activity_Webview.class);
                intent3.putExtra("url", SharePreUtils.getInstance().getQrcode_url());
                intent3.putExtra("title", getResources().getString(R.string.share));
                startActivity(intent3);
                break;
        }
    }
}
