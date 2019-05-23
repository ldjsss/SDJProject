package com.lldj.tc.mine.qrcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description:我的二维码 <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/6 12:13<p>
 * Modify Time: 2018/12/6 12:13<p>
 */


public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.goto_cerification_tv)
    TextView gotoCerificationTv;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, QRCodeActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResourcesString(R.string.my_qr_code));
    }

    @OnClick({R.id.toolbar_back_iv, R.id.goto_cerification_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.goto_cerification_tv:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
