package com.lldj.tc.info;

import android.content.Context;
import android.content.Intent;
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
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.utils.GlobalVariable;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Dialog_Set extends BaseDialog {

    @BindView(R.id.moneycur)
    TextView moneycur;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.hellow)
    TextView hellow;
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.imservice)
    ImageView imservice;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.tvservices)
    TextView tvservices;
    @BindView(R.id.connectservice)
    RelativeLayout connectservice;

    public Dialog_Set(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_set, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = this.getWindow();
        window.setGravity(Gravity.LEFT);
        window.setWindowAnimations(R.style.Anim_left);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(true);

        version.setText(AppUtils.getChannel(context) + "(" + AppUtils.getVersionName(context) + ")");
        moneycur.setText("¥" + SharePreUtils.getMoney(context));
        tvname.setText(SharePreUtils.getUserName(context));
        hellow.setText(getGreetings());

        ImmersionBar.with(bActivity).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(bActivity.getResources().getString(R.string.myinformation));

        HttpMsg.getInstance().sendGetUserInfo(context, SharePreUtils.getInstance().getToken(context), JsonBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    moneycur.setText("¥" + SharePreUtils.getMoney(context));
                    tvname.setText(SharePreUtils.getUserName(context));
                }
            }
        });
    }

    private String getGreetings() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String ret = "hello";
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= 0 && a <= 6) {
            ret = getContext().getString(R.string.goodinthenmorning);
        }
        if (a > 6 && a <= 12) {
            ret = getContext().getString(R.string.goodmorning);
        }
        if (a > 12 && a <= 13) {
            ret = getContext().getString(R.string.goodafternoon);
        }
        if (a > 13 && a <= 18) {
            ret = getContext().getString(R.string.Goodafternoon);
        }
        if (a > 18 && a <= 24) {
            ret = getContext().getString(R.string.goodnight);
        }

        return ret;
    }

    @OnClick({R.id.packed_iv, R.id.packlayout, R.id.recordlayout, R.id.setiv, R.id.toolbar_back_iv,
            R.id.messlayout, R.id.activitylayout, R.id.rulelayout, R.id.aboutlayout, R.id.agency, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                dismiss();
                break;
            case R.id.setiv:
                getContext().startActivity(new Intent(getContext(), Activity_Info.class));
                break;
            case R.id.packlayout:
                Intent _intent = new Intent(getContext(), Activity_Shop.class);
                _intent.putExtra("Anim_fade", R.style.Anim_fade);
                getContext().startActivity(_intent);
                break;
            case R.id.recordlayout:
                getContext().startActivity(new Intent(getContext(), Activity_Records.class));
                break;
            case R.id.messlayout:
                getContext().startActivity(new Intent(getContext(), Activity_Mess.class));
                break;
            case R.id.activitylayout:
                getContext().startActivity(new Intent(getContext(), Activity_Activitys.class));
                break;
            case R.id.rulelayout:
                Intent intent = new Intent(getContext(), Activity_Webview.class);
                intent.putExtra("url", "http://192.168.1.53:8080/rule.html");
                intent.putExtra("title", getContext().getResources().getString(R.string.rules));
                getContext().startActivity(intent);
                break;
            case R.id.aboutlayout:
                Intent intent1 = new Intent(getContext(), Activity_Webview.class);
                intent1.putExtra("url", "http://192.168.1.53:8080/about.html");
                intent1.putExtra("title", getContext().getResources().getString(R.string.about));
                getContext().startActivity(intent1);
                break;
            case R.id.agency:
                Intent intent2 = new Intent(getContext(), Activity_Webview.class);
                intent2.putExtra("url", "http://192.168.1.53:8080/agent_qrcode.html");
                intent2.putExtra("title", getContext().getResources().getString(R.string.agency));
                getContext().startActivity(intent2);
                break;
            case R.id.share:
                Intent intent3 = new Intent(getContext(), Activity_Webview.class);
                intent3.putExtra("url", "http://192.168.1.53:8080/agent_qrcode.html");
                intent3.putExtra("title", getContext().getResources().getString(R.string.share));
                getContext().startActivity(intent3);
                break;
        }
    }
}
