package com.lldj.tc.register;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;

public class IRegisterPresenterCompl implements IRegisterPresenter, CompoundButton.OnCheckedChangeListener {
    private IRegisterView mIRegisterView;

    private String pTelNumStr;
    private String pVerifyCodeStr;
    private String pPswStr;
    //密码是否显示
    private boolean mPswIsShow = false;
    private CheckBox mServicesAgreementCheckbox;


    public IRegisterPresenterCompl(IRegisterView pIRegisterView, CheckBox pServicesAgreementCheckbox) {
        this.mIRegisterView = pIRegisterView;
        this.mServicesAgreementCheckbox = pServicesAgreementCheckbox;
        mServicesAgreementCheckbox.setOnCheckedChangeListener(this);
    }

    @Override
    public void login() {

    }


    @Override
    public void telNumChangedListener(final EditText pTelNumEt, final ImageView pTelStatusIv) {
        pTelNumEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIRegisterView.telNumFocus(b);
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
                checkRegisterStatus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    @Override
    public void verifyCodeChangeListener(final EditText pverifyCodeEt) {
        pverifyCodeEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIRegisterView.verifyCodeFocus(b);
            }
        });

        pverifyCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pVerifyCodeStr = pverifyCodeEt.getText().toString().trim();
                checkRegisterStatus();
            }
        });
    }

    @Override
    public void getVerifyCode(final TextView pGetVerifyCodeTv) {
        pGetVerifyCodeTv.setText("59\"");

        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                long time = 59 - number;
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
    public void pswChangeListener(final EditText pPswEt) {
        pPswEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIRegisterView.pswFocus(b);
            }
        });

        pPswEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                pPswStr = pPswEt.getText().toString().trim();
                checkRegisterStatus();
            }
        });
    }

    @Override
    public void pswStatus(EditText pPswEt, ImageView pPswStatusIv) {
        if (mPswIsShow) {
            pPswStatusIv.setImageResource(R.mipmap.psw_show);
            pPswEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pPswStatusIv.setImageResource(R.mipmap.psw_hiddle);
            pPswEt.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        mPswIsShow = !mPswIsShow;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        checkRegisterStatus();
    }

    private void checkRegisterStatus() {
        if (!TextUtils.isEmpty(pVerifyCodeStr) && pVerifyCodeStr.length() == 4 && !TextUtils.isEmpty(pTelNumStr) && pTelNumStr.length() == 11 && !TextUtils.isEmpty(pPswStr) && pPswStr.length() >= 6 && mServicesAgreementCheckbox.isChecked()) {
            mIRegisterView.setRegisterBgStatus(true);
        } else {
            mIRegisterView.setRegisterBgStatus(false);
        }
    }


}
