package com.lldj.tc.mine.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
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


public class VerifySuccDialogActivity extends BaseActivity {
    @BindView(R.id.top_bg)
    View topBg;
    @BindView(R.id.verify_layout)
    LinearLayout verifyLayout;
    @BindView(R.id.verify_close_iv)
    ImageView verifyCloseIv;
    @BindView(R.id.edit_root_layout)
    LinearLayout editRootLayout;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, VerifySuccDialogActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_real_name_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(topBg).init();
    }

    @OnClick({R.id.verify_close_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_close_iv:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
