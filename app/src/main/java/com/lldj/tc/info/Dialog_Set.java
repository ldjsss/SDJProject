package com.lldj.tc.info;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.utils.GlobalVariable;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dialog_Set extends BaseDialog {

    @BindView(R.id.moneycur)
    TextView moneycur;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.hellow)
    TextView hellow;

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

    @OnClick({R.id.back_main_iv, R.id.imset, R.id.packed_iv, R.id.packlayout, R.id.record_iv, R.id.recordlayout,
            R.id.messlayout, R.id.activitylayout, R.id.rulelayout, R.id.aboutlayout, R.id.agency, R.id.contatus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_main_iv:
                dismiss();
                break;
            case R.id.imset:
                new Dialog_Info(getContext(), R.style.DialogTheme).show();
                break;
            case R.id.packed_iv:
                break;
            case R.id.packlayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.record_iv:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recordlayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.messlayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activitylayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rulelayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutlayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.agency:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contatus:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
