package com.lldj.tc.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.lldj.tc.Activity_MainUI;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleDialog.BaseRecycleDialog;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Login extends BaseActivity implements HandlerInter.HandleMsgListener {
    @BindView(R.id.videoView)
    VideoView videoView;
    private BaseRecycleDialog _dialog;

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

        _dialog = new BaseRecycleDialog(this, R.style.DialogTheme, null);
        _dialog.setCancelable(false);
        _dialog.showView(new Adapter_Login(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _dialog.dismiss();
        _dialog = null;
    }

    @Override
    protected void initData() { }

    @Override
    public void handleMsg(Message msg) {
        if(_dialog != null) _dialog.getRecycleCell().handleMsg(msg);
        switch (msg.what) {
            case HandlerType.GOTOMAIN:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.getInstance().sendGetUserInfo(SharePreUtils.getInstance().getToken(mContext), SharePreUtils.getInstance().getUserId(mContext), JsonBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        JsonBean res = (JsonBean) _res;
                        if (res.getCode() == GlobalVariable.succ) {
                            ResultsModel ret = (ResultsModel) res.getResult();
                            SharePreUtils.getInstance().setUserInfo(mContext, ret.getOpenid(), ret.getMobile(), ret.getMoney(), ret.getUsername());
                            Toast.makeText(mContext, getResources().getString(R.string.getUseInfoSucc), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, Activity_MainUI.class));
                            finish();
                        }
                    }
                });
                break;
            case HandlerType.JUSTLOOK:
                startActivity(new Intent(mContext, Activity_MainUI.class));
                finish();
                break;
            case HandlerType.SHOWTOAST:
                ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, msg.getData().getString("msg"), ToastUtils.LENGTH_SHORT);
                break;
            case HandlerType.LOADING:
                AppUtils.showLoading(mContext);
                break;
        }
    }

}