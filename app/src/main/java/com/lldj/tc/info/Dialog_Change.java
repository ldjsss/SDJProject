package com.lldj.tc.info;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BaseBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Dialog_Change extends BaseDialog {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.res_title)
    TextView resTitle;
    @BindView(R.id.restel_num_et)
    EditText restelNumEt;
    @BindView(R.id.res_paset)
    EditText resPaset;
    @BindView(R.id.res_repaset)
    EditText resRepaset;
    @BindView(R.id.respsw_title)
    TextView respsw_title;


    private String oldPassword = "";
    private String password = "";
    private String password1 = "";


    public Dialog_Change(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_change, null);
        setContentView(view);

        ButterKnife.bind(this, view);

        Window window = this.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setWindowAnimations(R.style.Anim_fade);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(true);

        ImmersionBar.with(bActivity).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(context.getResources().getString(R.string.changePassword));

        resTitle.setText(context.getString(R.string.oldPassword));
        respsw_title.setText(context.getString(R.string.newPassword));
    }


    @OnClick({R.id.toolbar_back_iv, R.id.register_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                DialogManager.getInstance().removeDialog(this);
                break;
            case R.id.register_tv:
                if (!checkAll()) return;
                HttpMsg.getInstance().sendChangeKey(SharePreUtils.getInstance().getToken(getContext()), password, oldPassword, BaseBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        BaseBean res = (BaseBean) _res;
                        if (res.getCode() == GlobalVariable.succ) {
                            showToast(R.string.passwordHaveChangesucc);
                            HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEAVEGAME);
                        }
                    }
                });

                break;
        }
    }

    private void showToast(int id) {
        ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, getContext().getResources().getString(id), ToastUtils.LENGTH_SHORT);
    }

    private boolean checkAll() {
        oldPassword = restelNumEt.getText().toString().trim();
        password = resPaset.getText().toString().trim();
        password1 = resRepaset.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword) || oldPassword.length() < 6 || oldPassword.length() > 16) {
            showToast(R.string.errorRemind);
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
            showToast(R.string.errorRemind1);
            return false;
        }
        if (TextUtils.isEmpty(password1) || password1.length() < 6 || password1.length() > 16) {
            showToast(R.string.errorRemind2);
            return false;
        }
        if (!TextUtils.equals(password, password1)) {
            showToast(R.string.errorRemind3);
            return false;
        }
        return true;
    }
}

