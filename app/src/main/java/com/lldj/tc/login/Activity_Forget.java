package com.lldj.tc.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.time.BasicTimer;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.KeyboardUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Forget extends BaseActivity {
    private BasicTimer getCodeDisposable;
    private String phoneNum = "";
    private String password = "";
    private String phoneCode = "";
    private int codeTime = 120;

    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.connectservice)
    RelativeLayout connectservice;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgetpw);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);

        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.forget_pswTitle));

    }

    public void dismiss() {
        if (getCodeDisposable != null) getCodeDisposable.cancel();
        KeyboardUtil.showOrHide(mContext, this);
        finish();
    }

    @OnClick({R.id.toolbar_back_iv, R.id.toolbar_title_tv, R.id.connectservice, R.id.resget_verify_codebtn, R.id.register_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                dismiss();
                break;
            case R.id.toolbar_title_tv:
                break;
            case R.id.connectservice:
                ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, "暂未实现！", ToastUtils.LENGTH_SHORT);
                break;
            case R.id.resget_verify_codebtn:
                phoneNum = rescodetelNumEt.getText().toString().trim();
                if (!AppUtils.isMobileNO(phoneNum)) {
                    showToast(R.string.errorRemind5);
                    return;
                }

                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendGetCode(phoneNum, JsonBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if(res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();
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
                                        resgetVerifyCodebtn.setText(mContext.getText(R.string.get_verify_code));
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
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendForgetKey(phoneNum, password, phoneCode, JsonBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if(res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.passwordHaveChange), Toast.LENGTH_SHORT).show();
                            SharePreUtils.setPassWord(mContext, password);
                            HandlerInter.getInstance().sendEmptyMessage(HandlerType.REGISTSUCC);
                            dismiss();
                        }
                    }
                });
                break;
        }
    }

    private void showToast(int id) {
        ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getResources().getString(id), ToastUtils.LENGTH_SHORT);
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

