package com.lldj.tc.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.immersionbar.OnKeyboardListener;
import com.lldj.tc.toolslibrary.util.KeyboardUtil;
import com.lldj.tc.toolslibrary.util.TextWatcherUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 输入真实姓名<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/5 18:56<p>
 * Modify Time: 2018/12/5 18:56<p>
 */


public class EditNameActivity extends BaseActivity implements OnKeyboardListener {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.tel_num_line)
    View telNumLine;
    @BindView(R.id.save_tv)
    TextView saveTv;
    //0修改姓名，1 修改昵称
    private int mType;
    String mNameStr;


    public static void launch(Activity pContext, String pName, int pType) {
        Intent mIntent = new Intent(pContext, EditNameActivity.class);
        mIntent.putExtra(NAME, pName);
        mIntent.putExtra(TYPE, pType);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        mType = getIntent().getIntExtra(TYPE, 0);
        mNameStr = getIntent().getStringExtra("name");
        switch (mType) {
            case 0:
                toolbarTitleTv.setText("修改姓名");
                nameEt.setHint("请输入您的真实姓名");
                break;
            case 1:
                toolbarTitleTv.setText("修改昵称");
                nameEt.setHint("请输入您的昵称");
                break;
        }

        if (!TextUtils.isEmpty(mNameStr)) {
            nameEt.setText(mNameStr);
            nameEt.setSelection(nameEt.length());
        }
        setBtnStatus();
        TextWatcherUtils mTextWatcher = new TextWatcherUtils(mContext, nameEt, 20, false);
        nameEt.addTextChangedListener(mTextWatcher);
        mTextWatcher.setAfterTextChangedListener(new TextWatcherUtils.AfterTextChangedListener() {
            @Override
            public void onTextChange(Editable s) {
                mNameStr = nameEt.getText().toString().trim();
                setBtnStatus();

            }
        });

    }


    @Override
    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
        Log.e("键盘弹出状态", isPopup + "===");
    }

    @Override
    public void onBackPressed() {
        exitActivity();

    }

    public void exitActivity() {
        KeyboardUtil.hideInput(mContext, nameEt);
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick({R.id.toolbar_back_iv, R.id.save_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.save_tv:
                mNameStr = nameEt.getText().toString().trim();
//                Intent mIntent = new Intent();
//                mIntent.putExtra("name", name);
//                mIntent.putExtra("type", mType);
//                setResult(100, mIntent);
                SharePreUtils.setUserName(mContext, mNameStr);
                finish();
                KeyboardUtil.hideInput(mContext, nameEt);
                break;
        }
    }


    /**
     * 设置按钮的状态
     */
    public void setBtnStatus() {
        if (!TextUtils.isEmpty(mNameStr)) {
            saveTv.setEnabled(true);
            saveTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            saveTv.setEnabled(false);
            saveTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }


}
