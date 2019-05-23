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
import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/10 15:30<p>
 * Modify Time: 2018/12/10 15:30<p>
 */


public class VerifyEnsureActivity extends BaseActivity {
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String IDNUM = "idnum";
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.real_name_str_tv)
    TextView realNameStrTv;
    @BindView(R.id.real_name_et)
    TextView realNameEt;
    @BindView(R.id.name_arrow)
    ImageView nameArrow;
    @BindView(R.id.id_type_tv)
    TextView idTypeTv;
    @BindView(R.id.id_type_num)
    TextView idTypeNum;
    @BindView(R.id.type_arrow)
    ImageView typeArrow;
    @BindView(R.id.certification_num_str_tv)
    TextView certificationNumStrTv;
    @BindView(R.id.certification_num_et)
    TextView certificationNumEt;
    @BindView(R.id.next_step_tv)
    TextView nextStepTv;
    private String mName;
    private int mId;
    private String mIdNum;


    public static void launch(Activity pContext, String pName, int pid, String pIdNum) {
        Intent mIntent = new Intent(pContext, VerifyEnsureActivity.class);
        mIntent.putExtra(NAME, pName);
        mIntent.putExtra(ID, pid);
        mIntent.putExtra(IDNUM, pIdNum);
        pContext.startActivityForResult(mIntent,100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_ensure_info);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        mName = getIntent().getStringExtra(NAME);
        mId = getIntent().getIntExtra(ID, 1);
        mIdNum = getIntent().getStringExtra(IDNUM);
        realNameEt.setText(mName);
        switch (mId) {
            case 1:
                idTypeTv.setText("身份证");
                break;
            case 2:
                idTypeTv.setText("社保卡");
                break;
            case 3:
                idTypeTv.setText("军人证");
                break;
        }

        idTypeNum.setText(mIdNum);
    }

    @OnClick({R.id.toolbar_back_iv, R.id.next_step_tv, R.id.real_name_et, R.id.id_type_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.real_name_et:
            case R.id.id_type_tv:
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.next_step_tv:
                VerifyFaceActivity.launch(mContext);
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
