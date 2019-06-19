package com.lldj.tc.register;

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

import com.lldj.tc.R;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.FormatModel.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.mainUtil.GlobalVariable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * description: 注册<p>
 */
public class RegisterFrament extends BaseFragment {


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
    @BindView(R.id.reslayoutpar1)
    RelativeLayout reslayoutpar1;

    private String phoneNum = "";
    private String userCount = "";
    private String password = "";
    private String password1 = "";
    private String phoneCode = "";
    private String userName = "";
    private Disposable getCodeDisposable;
    private int codeTime = 120;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public int getContentView() { return R.layout.frament_register; }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.register_str));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
    }

    @OnClick({R.id.toolbar_back_iv, R.id.toolbar_title_tv, R.id.connectservice, R.id.resget_verify_codebtn, R.id.register_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.REMOVERES);
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
                            resgetVerifyCodebtn.setText(getText(R.string.get_verify_code));
                            return;
                        }
                        resgetVerifyCodebtn.setText(codeTime + "s");
                    }

                    @Override
                    public void onComplete() {}
                });
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.sendGetCode(phoneNum, new HttpMsg.Listener(){
                    @Override
                    public void onFinish(JsonBean res) {
                        if(res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(mContext, getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.register_tv:
                if (!checkAll()) return;
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.sendRegister(userCount, password, userName, phoneNum, phoneCode, AppUtils.getChannel(mContext), "", new HttpMsg.Listener(){
                    @Override
                    public void onFinish(JsonBean res) {
                        if(res.getCode() == GlobalVariable.succ){
                            SharePreUtils.getInstance().setRegistInfo(mContext, userCount, password, userName, phoneNum, AppUtils.getChannel(mContext), "");
                            Toast.makeText(mContext, getResources().getString(R.string.registSucc),Toast.LENGTH_SHORT).show();
                            HandlerInter.getInstance().sendEmptyMessage(HandlerType.REGISTSUCC);
                        }
                    }
                });
                ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, "注册！", ToastUtils.LENGTH_SHORT);
                break;
        }
    }

    private void showToast(int id) {
        ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, getResources().getString(id), ToastUtils.LENGTH_SHORT);
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

