package com.lldj.tc.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.immersionbar.OnKeyboardListener;
import com.lldj.tc.toolslibrary.util.KeyboardUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 输入真实姓名<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/5 18:56<p>
 * Modify Time: 2018/12/5 18:56<p>
 */


public class InputNameActivity extends BaseActivity implements OnKeyboardListener {


    @BindView(R.id.top_bg)
    View topBg;
    @BindView(R.id.real_name_et)
    EditText realNameEt;
    @BindView(R.id.input_layout)
    RelativeLayout inputLayout;
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.edit_root_layout)
    LinearLayout editRootLayout;
    @BindView(R.id.ensure_tv)
    TextView ensureTv;

    public static void launch(Context pContext, String pName) {
        Intent mIntent = new Intent(pContext, InputNameActivity.class);
        mIntent.putExtra("name", pName);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_name_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(topBg).setOnKeyboardListener(this).keyboardEnable(true).init();
        String mNameStr = getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(mNameStr)) {
            realNameEt.setText(mNameStr);
        }

    }

    @OnClick({R.id.close_iv, R.id.ensure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.input_layout:
                break;
            case R.id.close_iv:
                exitActivity();
                break;
            case R.id.ensure_tv:
                String name = realNameEt.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    KeyboardUtil.hideInput(mContext, realNameEt);
                    EventBus.getDefault().post(new NameEventBus(name));
                    finish();
                    overridePendingTransition(0, 0);
                } else {
                    ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, "请输入姓名", ToastUtils.LENGTH_SHORT);
                }
                break;
        }
    }



    @Override
    public void onBackPressed() {
        exitActivity();

    }

    public void exitActivity() {
        KeyboardUtil.hideInput(mContext, realNameEt);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {

    }
}
