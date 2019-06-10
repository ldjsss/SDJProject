package com.lldj.tc.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.R;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.test.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.util.AppURLCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * description: 忘记密码<p>
 */
public class ForgetFrament extends BaseFragment {

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
    @BindView(R.id.reslayoutpar)
    RelativeLayout reslayoutpar;
    @BindView(R.id.resget_verify_codebtn)
    Button resgetVerifyCodebtn;

    private String phoneNum = "";
    private String password = "";
    private String phoneCode = "";
    private Disposable getCodeDisposable;
    private int codeTime = 120;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.frament_forgetpassword;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.forget_pswTitle));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
                        if (codeTime <= 0) {
                            if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
                            getCodeDisposable = null;
                            resgetVerifyCodebtn.setEnabled(true);
                            resgetVerifyCodebtn.setText(getText(R.string.get_verify_code));
                            return;
                        }
                        resgetVerifyCodebtn.setText(codeTime + "s");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.sendGetCode(phoneNum, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(JsonBean res) {
                        if(res.getCode() == AppURLCode.succ) {
                            Toast.makeText(mContext, getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.register_tv:
                if (!checkAll()) return;
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                HttpMsg.sendForgetKey(phoneNum, password, phoneCode, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(JsonBean res) {
                        if(res.getCode() == AppURLCode.succ) {
                            Toast.makeText(mContext, getResources().getString(R.string.passwordHaveChange), Toast.LENGTH_SHORT).show();
                            SharePreUtils.setPassWord(mContext, password);
                            HandlerInter.getInstance().sendEmptyMessage(HandlerType.REGISTSUCC);
                        }
                    }
                });
                break;
        }
    }

    private void showToast(int id) {
        ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, getResources().getString(id), ToastUtils.LENGTH_SHORT);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
    }
}

