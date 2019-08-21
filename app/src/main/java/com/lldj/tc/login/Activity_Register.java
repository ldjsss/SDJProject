package com.lldj.tc.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.channel.helper.ChannelReaderUtil;
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
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Register extends BaseActivity {
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
    private BasicTimer getCodeDisposable;

    private String phoneNum = "";
    private String userCount = "";
    private String password = "";
    private String password1 = "";
    private String phoneCode = "";
    private String userName = "";
    private int codeTime = 120;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        overridePendingTransition(R.anim.in_from_right,0);

        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        ButterKnife.bind(this);

        connectservice.setVisibility(View.VISIBLE);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.register_str));

    }

    public void dismiss() {
        if (getCodeDisposable != null) getCodeDisposable.cancel();
        KeyboardUtil.showOrHide(mContext, this);
        finish();
        overridePendingTransition(0,R.anim.out_to_right);
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
                ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, "暂未实现！", ToastUtils.LENGTH_SHORT);
                break;
            case R.id.resget_verify_codebtn:
                phoneNum = rescodetelNumEt.getText().toString().trim();
                if (!AppUtils.isMobileNO(phoneNum)) {
                    showToast(R.string.errorRemind5);
                    return;
                }

                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendGetCode(phoneNum, JsonBean.class, new HttpMsg.Listener(){
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean)_res;
                        if(res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(mContext, getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();
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
                String package_info = ChannelReaderUtil.getChannel(bActivity.getApplicationContext());
                String devices = Utils.getDevices(bActivity);
//                ToastUtils.show_middle_pic(Activity_Login.this, R.mipmap.cancle_icon, "channel = " + channel, ToastUtils.LENGTH_LONG);
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendRegister(userCount, password, userName, phoneNum, phoneCode, AppUtils.getChannel(mContext), devices, package_info, JsonBean.class, new HttpMsg.Listener(){
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if(res.getCode() == GlobalVariable.succ){
                            SharePreUtils.getInstance().setRegistInfo(mContext, userCount, password, userName, phoneNum, AppUtils.getChannel(mContext), devices);
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.registSucc),Toast.LENGTH_SHORT).show();
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

