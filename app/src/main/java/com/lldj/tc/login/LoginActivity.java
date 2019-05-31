package com.lldj.tc.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.lldj.tc.JobSelectActivity;
import com.lldj.tc.MainActivity2;
import com.lldj.tc.R;
import com.lldj.tc.login.VerifyCodeLogin.VerifyCodeLoginActivity;
import com.lldj.tc.register.RegisterActivity;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements IloginView {

    @BindView(R.id.verify_code_login_tv)
    TextView verifyCodeLoginTv;
    @BindView(R.id.forget_psw_tv)
    TextView forgetPswTv;
    @BindView(R.id.login_tv)
    TextView loginTv;

    @BindView(R.id.just_look_tv)
    TextView justLookTv;
    @BindView(R.id.register_layout)
    RelativeLayout registerLayout;
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.quhao_tv)
    TextView quhaoTv;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tel_num_et)
    EditText telNumEt;
    @BindView(R.id.tel_layout)
    RelativeLayout telLayout;
    @BindView(R.id.account_not_exist_tip)
    TextView accountNotExistTip;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw_layout)
    RelativeLayout pswLayout;
    @BindView(R.id.psw_wrong_tip)
    TextView pswWrongTip;
    @BindView(R.id.register_tv)
    TextView registerTv;
    @BindView(R.id.tel_num_line)
    View telNumLine;
    @BindView(R.id.psw_line)
    View pswLine;
    @BindView(R.id.tel_status)
    ImageView telStatus;
    @BindView(R.id.psw_show_or_hid_iv)
    ImageView pswShowOrHidIv;
    @BindView(R.id.psw_status)
    ImageView pswStatus;

    private ILoginPresenterCompl iLoginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        VideoView videov = (VideoView) findViewById(R.id.videoView);
        MediaController mediaC = new MediaController(this);
        String path = "android.resource://" + getPackageName() + "/"+ R.raw.main_movie;
        videov.setVideoURI( Uri.parse(path));
        videov.start();
        videov.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videov.setVideoURI( Uri.parse(path));
                videov.start();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videov.setLayoutParams(layoutParams);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        //设置登录按钮的状态
        setLoginStatus(false);
        iLoginPresenter = new ILoginPresenterCompl(this);
        iLoginPresenter.telNumOperate(telNumEt, telStatus);
        iLoginPresenter.pswOperate(pswEt);
        //设置密码的显示or隐藏
        iLoginPresenter.pswStatus(pswEt, pswShowOrHidIv);

    }

    @Override
    public void onSubmitResult() {

    }

    @Override
    public void telNumFocus(boolean isFocus) {
        if (isFocus) {
            telNumLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            telNumLine.setBackgroundColor(getResources().getColor(R.color.color_969696));
        }
    }

    @Override
    public void pswFocus(boolean isFocus) {
        if (isFocus) {
            pswLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            pswLine.setBackgroundColor(getResources().getColor(R.color.color_969696));
        }
    }

    @Override
    public void setLoginStatus(boolean isEnable) {
        if (isEnable) {
            loginTv.setEnabled(true);
        } else {
            loginTv.setEnabled(false);
        }
    }

    @OnClick({R.id.verify_code_login_tv, R.id.forget_psw_tv, R.id.login_tv, R.id.register_layout, R.id.just_look_tv, R.id.psw_show_or_hid_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_code_login_tv:
                startActivity(new Intent(this, VerifyCodeLoginActivity.class));
                break;
            case R.id.forget_psw_tv:
                RegisterActivity.launch(LoginActivity.this, 1);
                break;
            case R.id.login_tv:
                startActivity(new Intent(this, JobSelectActivity.class));
                finish();
                break;
            case R.id.register_layout:
                RegisterActivity.launch(LoginActivity.this, 0);
                break;
            case R.id.just_look_tv:
                startActivity(new Intent(this, MainActivity2.class));
                Intent mIntent = new Intent(this,TestActivity.class);
                startActivity(mIntent);
                finish();
                break;
            case R.id.psw_show_or_hid_iv:
                iLoginPresenter.pswStatus(pswEt, pswShowOrHidIv);
                break;
        }
    }


}
