package com.lldj.tc.login;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lldj.tc.R;

public class ILoginPresenterCompl implements ILoginPresenter {
    private IloginView mIloginView;
    //密码是否显示
    private boolean mPswIsShow = false;
    private String pTelNumStr;
    private String pPswStr;

    public ILoginPresenterCompl(IloginView mIloginView) {
        this.mIloginView = mIloginView;
    }

    @Override
    public void login() {

    }

    @Override
    public void telNumOperate(final EditText pTelNumEt, final ImageView pTelStatusIv) {
        pTelNumEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIloginView.telNumFocus(b);
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
                if(pPswStr.length()>=6 && pTelNumStr.length()==11){
                    mIloginView.setLoginStatus(true);
                }else{
                    mIloginView.setLoginStatus(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void pswOperate(final EditText pPswEt) {
        pPswEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mIloginView.pswFocus(b);
            }
        });

        pPswEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pPswStr = pPswEt.getText().toString().trim();
                if(pPswStr.length()>=6 && pTelNumStr.length()==11){
                    mIloginView.setLoginStatus(true);
                }else{
                    mIloginView.setLoginStatus(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void pswStatus(EditText pPswEt, ImageView pPswStatusIv) {
        if (mPswIsShow) {
            pPswStatusIv.setImageResource(R.mipmap.psw_show);
            pPswEt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pPswStatusIv.setImageResource(R.mipmap.psw_hiddle);
            pPswEt.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        mPswIsShow = !mPswIsShow;
    }


}
