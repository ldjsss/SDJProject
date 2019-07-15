package com.lldj.tc.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.lldj.tc.match.Activity_MainUI;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Login extends BaseActivity implements HandlerInter.HandleMsgListener {
    @BindView(R.id.videoView)
    VideoView videoView;

    @BindView(R.id.tel_num_et)
    EditText telNumEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw_show_or_hid_iv)
    ImageView pswShowOrHidIv;

    private String userCount = "";
    private String password = "";
    private boolean mPswIsShow = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mHandler.setHandleMsgListener(this);

        String path = "android.resource://" + mContext.getPackageName() + "/" + R.raw.main_movie;

        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);

        pswStatus(pswEt, pswShowOrHidIv);
        tokenLogin();

    }

    @OnClick({R.id.forget_psw_tv, R.id.login_tv, R.id.register_tv, R.id.just_look_tv, R.id.psw_show_or_hid_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_psw_tv:
                new Dialog_Forget(this, R.style.DialogTheme).show();
                break;
            case R.id.register_tv:
                new Dialog_Register(this, R.style.DialogTheme).show();
                break;
            case R.id.login_tv:
                if (!checkAll()) return;
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendLogin(userCount, password, JsonBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean)_res;
                        if (res.getCode() == GlobalVariable.succ) {
                            saveLoginData(res);
                        }
                    }
                });
                break;
            case R.id.just_look_tv:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.JUSTLOOK);
                break;
            case R.id.psw_show_or_hid_iv:
                pswStatus(pswEt, pswShowOrHidIv);
        }
    }

    private void tokenLogin() {
        String token = SharePreUtils.getToken(mContext);
        if(TextUtils.isEmpty(token)) return;
        HttpMsg.getInstance().sendTokenLogin(token, JsonBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    saveLoginData(res);
                }
            }
        });
    }

    private void saveLoginData(JsonBean res) {
        ResultsModel ret = (ResultsModel)res.getResult();
        SharePreUtils.getInstance().setLoginInfo(mContext, ret.getAccess_token(), ret.getExpires_in(), ret.getOpenid());
        Toast.makeText(mContext, mContext.getResources().getString(R.string.loginsucc), Toast.LENGTH_SHORT).show();
        HandlerInter.getInstance().sendEmptyMessage(HandlerType.GOTOMAIN);

    }

    private boolean checkAll() {
        userCount = telNumEt.getText().toString().trim();
        password = pswEt.getText().toString().trim();

        if (TextUtils.isEmpty(userCount) || userCount.length() < 6 || userCount.length() > 16) {
            ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getResources().getString(R.string.errorRemind), ToastUtils.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
            ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getResources().getString(R.string.errorRemind1), ToastUtils.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private void fillCount() {
        telNumEt.setText(SharePreUtils.getInstance().getUserName(mContext));
        pswEt.setText(SharePreUtils.getInstance().getPassword(mContext));
    }

    private void pswStatus(EditText pPswEt, ImageView pPswStatusIv) {
        if (mPswIsShow) {
            pPswStatusIv.setImageResource(R.mipmap.psw_show);
            pPswEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            pPswStatusIv.setImageResource(R.mipmap.psw_hiddle);
            pPswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mPswIsShow = !mPswIsShow;
    }

    @Override
    protected void initData() { }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case HandlerType.REGISTSUCC:
                fillCount();
            case HandlerType.GOTOMAIN:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendGetUserInfo(mContext, SharePreUtils.getInstance().getToken(mContext), JsonBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if (res.getCode() == GlobalVariable.succ) {
                            startActivity(new Intent(mContext, Activity_MainUI.class));
                            finish();
                        }
                    }
                });
                break;
            case HandlerType.JUSTLOOK:
                startActivity(new Intent(this, Activity_MainUI.class));
                finish();
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                break;
            case HandlerType.LOADING:
                if(!isFinishing()){
                    AppUtils.showLoading(this);
                }
                break;
        }
    }

}