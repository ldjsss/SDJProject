package com.lldj.tc.login.VerifyCodeLogin;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;

public class IVerifyLoginPresenterCompl implements IVerifyLoginPresenter, CompoundButton.OnCheckedChangeListener {
    private IVerifyloginView mIVerifyloginView;
    private String pTelNumStr;
    private String pVerifyCodeStr;


    public IVerifyLoginPresenterCompl(IVerifyloginView mIloginView) {
        this.mIVerifyloginView = mIloginView;
    }

    @Override
    public void login() {

    }

    @Override
    public void telNumOperate(final EditText pTelNumEt, final ImageView pTelStatusIv) {
        pTelNumEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIVerifyloginView.telNumFocus(b);
            }
        });

        pTelNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pTelNumStr = pTelNumEt.getText().toString().trim();
                if (pTelNumStr.length() == 11) {
                    pTelStatusIv.setImageResource(R.mipmap.log_correct_msg);
                    pTelStatusIv.setVisibility(View.VISIBLE);
                } else {
                    pTelStatusIv.setVisibility(View.INVISIBLE);
                }
                checkLoginStatus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkLoginStatus() {
        if (!TextUtils.isEmpty(pVerifyCodeStr) && pVerifyCodeStr.length() == 4 && !TextUtils.isEmpty(pTelNumStr) && pTelNumStr.length() == 11) {
            mIVerifyloginView.setLoginStatus(true);
        } else {
            mIVerifyloginView.setLoginStatus(false);
        }
    }

    @Override
    public void verifyCodeOperate(final EditText pverifyCodeEt) {
        pverifyCodeEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIVerifyloginView.verifyCodeFocus(b);
            }
        });

        pverifyCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pVerifyCodeStr = pverifyCodeEt.getText().toString().trim();
                checkLoginStatus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void getVerifyCode(final TextView pGetVerifyCodeTv) {
        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                long time = 60 - number;
                if (time > 0) {
                    pGetVerifyCodeTv.setText(time + "\"");
                } else {
                    onComplete();
                }

            }

            @Override
            public void onComplete() {
                pGetVerifyCodeTv.setText("再次发送");
                RxTimerUtil.cancel();
            }
        });

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        checkLoginStatus();
    }
}
