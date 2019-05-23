package com.lldj.tc.mine.verify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.ScreenUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/10 17:11<p>
 * Modify Time: 2018/12/10 17:11<p>
 */


public class VerifyFaceActivity extends BaseActivity {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.ok_tv)
    TextView okTv;
    @BindView(R.id.face_iv)
    ImageView faceIv;

    public static void launch(Activity pContext) {
        Intent mIntent = new Intent(pContext, VerifyFaceActivity.class);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_face);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResourcesString(R.string.verify_str));
        int mWidth = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dipTopx(mContext, 114);
        ScreenUtil.setLinearLayoutParams(faceIv, mWidth, mWidth);
    }

    @OnClick({R.id.toolbar_back_iv, R.id.ok_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.ok_tv:
                VerifySuccActivity.launch(mContext);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            setResult(100);
            finish();
        }
    }
}
