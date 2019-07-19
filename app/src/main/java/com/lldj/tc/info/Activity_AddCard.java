package com.lldj.tc.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_AddCard extends BaseActivity {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.tvbankcardman)
    TextView tvbankcardman;
    @BindView(R.id.selectlayout)
    LinearLayout selectlayout;
    @BindView(R.id.editaddcardnum)
    EditText editaddcardnum;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.tvbanktitle)
    TextView tvbanktitle;
    @BindView(R.id.bankname)
    TextView bankname;
    @BindView(R.id.selectbanklayout)
    RelativeLayout selectbanklayout;
    @BindView(R.id.addsure)
    TextView addsure;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addcardlayout);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);


        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.addcardtitle));

        editaddcardnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.camera, R.id.selectlayout, R.id.selectbanklayout, R.id.addsure, R.id.toolbar_back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:
                Toast.makeText(this, "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.selectlayout:
                Toast.makeText(this, getResourcesString(R.string.cardname), Toast.LENGTH_SHORT).show();
                break;
            case R.id.selectbanklayout:

                break;
            case R.id.addsure:

                break;
            case R.id.toolbar_back_iv:
                Intent _intent = new Intent(this, Activity_Getmoney.class);
                _intent.putExtra("Anim_fade", R.style.Anim_left);
                startActivity(_intent);
                finish();
                break;
        }
    }
}
