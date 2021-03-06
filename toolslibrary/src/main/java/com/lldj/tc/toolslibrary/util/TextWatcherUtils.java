package com.lldj.tc.toolslibrary.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lldj.tc.toolslibrary.R;
import com.lldj.tc.toolslibrary.view.ToastUtils;


/**
 * Description: 文字监听
 */
public class TextWatcherUtils implements TextWatcher {
    private EditText mEditText;
    private int editStart;
    private int editEnd;
    //中文汉字的长度 ，英文为中文的2倍
    private int maxLength;
    private Context mContext;
    //是否区分中英文
    private boolean mpdistinguishChineseOrEnglish;


    public TextWatcherUtils(Context pContext, EditText pEditText, int pMaxLength, boolean pdistinguishChineseOrEnglish) {
        this.mEditText = pEditText;
        this.maxLength = pMaxLength;
        this.mContext = pContext;
        mpdistinguishChineseOrEnglish = pdistinguishChineseOrEnglish;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mTextChangedListener != null) {
            mTextChangedListener.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mTextChangedListener != null) {
            mTextChangedListener.onTextChanged(s, start, before, count);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        editStart = mEditText.getSelectionStart();
        editEnd = mEditText.getSelectionEnd();

        // 先去掉监听器，否则会出现栈溢出
        mEditText.removeTextChangedListener(this);

        // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
        // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
        Clog.e("字符数", calculateLength(s.toString()) + "");
        while (calculateLength(s.toString()) > maxLength) { // 当输入字符个数超过限制的大小时，进行截断操作
            ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, "最多输入" + maxLength + "个字", ToastUtils.LENGTH_SHORT);
            s.delete(editStart - 1, editEnd);
            editStart--;
            editEnd--;
        }
        mEditText.setText(s);
        mEditText.setSelection(editStart);

        // 恢复监听器
        mEditText.addTextChangedListener(this);

        if (mTextChangedListener != null) {
            mTextChangedListener.afterTextChanged(s);
            mTextChangedListener.currentTextLength(calculateLength(s.toString()));
        }
    }

    private long calculateLength(CharSequence c) {
        double len = 0;
        if (mpdistinguishChineseOrEnglish) {
            for (int i = 0; i < c.length(); i++) {
                int tmp = (int) c.charAt(i);
                if (tmp > 0 && tmp < 127) {
                    len += 0.5;
                } else {
                    len++;
                }
            }
        } else {
            for (int i = 0; i < c.length(); i++) {
                len++;

            }
        }
        return Math.round(len);
    }


    private  TextWatcherListener mTextChangedListener;
    public interface TextWatcherListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void afterTextChanged(Editable s);
        void currentTextLength(long pTextLength);

    }
    public void setTextChangedListener(TextWatcherListener pListener) {
        this.mTextChangedListener = pListener;
    }
}
