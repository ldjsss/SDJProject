package com.lldj.tc.login;

import android.app.Activity;
import android.content.Context;
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

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class Dialog_Register extends BaseDialog {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.connectservice)
    RelativeLayout connectservice;
    @BindView(R.id.restel_num_et)
    EditText restelNumEt;
    @BindView(R.id.res_paset)
    EditText resPaset;
    @BindView(R.id.res_repaset)
    EditText resRepaset;
    @BindView(R.id.rescodetel_num_et)
    EditText rescodetelNumEt;
    @BindView(R.id.resget_verify_codebtn)
    Button resgetVerifyCodebtn;
    @BindView(R.id.resverifycode_et)
    EditText resverifycodeEt;
    @BindView(R.id.register_tv)
    TextView registerTv;
    @BindView(R.id.resview_line33)
    View resviewLine33;
    @BindView(R.id.resname_et)
    EditText resnameEt;
    private Disposable getCodeDisposable;

    private String phoneNum = "";
    private String userCount = "";
    private String password = "";
    private String password1 = "";
    private String phoneCode = "";
    private String userName = "";
    private int codeTime = 120;


    public Dialog_Register(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_register, null);
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

        connectservice.setVisibility(View.VISIBLE);
        ImmersionBar.with((Activity) context).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(context.getResources().getString(R.string.register_str));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
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
                ToastUtils.show_middle_pic(getContext(), R.mipmap.cancle_icon, "暂未实现！", ToastUtils.LENGTH_SHORT);
                break;
            case R.id.resget_verify_codebtn:
                phoneNum = rescodetelNumEt.getText().toString().trim();
                if (!AppUtils.isMobileNO(phoneNum)) {
                    showToast(R.string.errorRemind5);
                    return;
                }
                codeTime = 120;
                resgetVerifyCodebtn.setEnabled(false);

                if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
                getCodeDisposable = RxTimerUtilPro.interval(1000, new RxTimerUtilPro.IRxNext() {
                    @Override
                    public void doNext(long number) {
                        codeTime--;
                        if (codeTime <=0 ){
                            if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
                            getCodeDisposable = null;
                            resgetVerifyCodebtn.setEnabled(true);
                            resgetVerifyCodebtn.setText(getContext().getText(R.string.get_verify_code));
                            return;
                        }
                        resgetVerifyCodebtn.setText(codeTime + "s");
                    }

                    @Override
                    public void onComplete() {}
                });
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendGetCode(phoneNum, JsonBean.class, new HttpMsg.Listener(){
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean)_res;
                        if(res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.register_tv:
                if (!checkAll()) return;
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendRegister(userCount, password, userName, phoneNum, phoneCode, AppUtils.getChannel(getContext()), "", JsonBean.class, new HttpMsg.Listener(){
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if(res.getCode() == GlobalVariable.succ){
                            SharePreUtils.getInstance().setRegistInfo(getContext(), userCount, password, userName, phoneNum, AppUtils.getChannel(getContext()), "");
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.registSucc),Toast.LENGTH_SHORT).show();
                            HandlerInter.getInstance().sendEmptyMessage(HandlerType.REGISTSUCC);
                            dismiss();
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

        userCount = restelNumEt.getText().toString().trim();
        password = resPaset.getText().toString().trim();
        password1 = resRepaset.getText().toString().trim();
        phoneNum = rescodetelNumEt.getText().toString().trim();
        phoneCode = resverifycodeEt.getText().toString().trim();
        userName = resnameEt.getText().toString().trim();

        if (TextUtils.isEmpty(userCount) || userCount.length() < 6 || userCount.length() > 16) {
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
        if (TextUtils.isEmpty(userName)) {
            showToast(R.string.errorRemind4);
            return false;
        }
        if (!AppUtils.isMobileNO(phoneNum)) {
            showToast(R.string.errorRemind5);
            return false;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            showToast(R.string.errorRemind6);
            return false;
        }

        return true;
    }

}

