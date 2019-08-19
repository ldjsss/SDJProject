package com.lldj.tc.info;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BaseBean;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.time.BasicTimer;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Dialog_ChangePhone extends BaseDialog {
    private BasicTimer getCodeDisposable;
    private String phoneNum = "";
    private String password = "";
    private String phoneCode = "";
    private int codeTime = 120;

    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.rescodetel_num_et)
    EditText rescodetelNumEt;
    @BindView(R.id.resverifycode_et)
    EditText resverifycodeEt;
    @BindView(R.id.respsw_title)
    TextView respswTitle;
    @BindView(R.id.res_paset)
    EditText resPaset;
    @BindView(R.id.resget_verify_codebtn)
    Button resgetVerifyCodebtn;

    public Dialog_ChangePhone(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_changephone, null);
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
        toolbarTitleTv.setText(context.getResources().getString(R.string.changePhone));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (getCodeDisposable != null) getCodeDisposable.cancel();
    }

    @OnClick({R.id.toolbar_back_iv, R.id.toolbar_title_tv, R.id.connectservice, R.id.resget_verify_codebtn, R.id.register_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                DialogManager.getInstance().removeDialog(this);
                break;
            case R.id.resget_verify_codebtn:
                phoneNum = rescodetelNumEt.getText().toString().trim();
                if (!AppUtils.isMobileNO(phoneNum)) {
                    showToast(R.string.errorRemind5);
                    return;
                }

                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendGetUseCode(SharePreUtils.getInstance().getToken(getContext()), phoneNum, JsonBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if(res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();

                            codeTime = 120;
                            resgetVerifyCodebtn.setEnabled(false);

                            if (getCodeDisposable != null) getCodeDisposable.cancel();

                            getCodeDisposable = new BasicTimer(new BasicTimer.BasicTimerCallback() {
                                @Override
                                public void onTimer() {
                                    codeTime--;
                                    if (codeTime <= 0) {
                                        if (getCodeDisposable != null) getCodeDisposable.cancel();
                                        getCodeDisposable = null;
                                        resgetVerifyCodebtn.setEnabled(true);
                                        resgetVerifyCodebtn.setText(getContext().getText(R.string.get_verify_code));
                                        return;
                                    }
                                    resgetVerifyCodebtn.setText(codeTime + "s");
                                }
                            });
                            getCodeDisposable.start(1000);
                        }
                    }
                });
                break;
            case R.id.register_tv:
                if (!checkAll()) return;
                HttpMsg.getInstance().sendChangePhone(SharePreUtils.getInstance().getToken(getContext()), phoneCode, phoneNum, password, BaseBean.class, new HttpMsg.Listener() {
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

        password = resPaset.getText().toString().trim();
        phoneNum = rescodetelNumEt.getText().toString().trim();
        phoneCode = resverifycodeEt.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNum) || !AppUtils.isMobileNO(phoneNum)) {
            showToast(R.string.errorRemind5);
            return false;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            showToast(R.string.errorRemind6);
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
            showToast(R.string.errorRemind1);
            return false;
        }

        return true;
    }

}

