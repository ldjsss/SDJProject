package com.lldj.tc.mine.verify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 实名认证第一页<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/10 11:24<p>
 * Modify Time: 2018/12/10 11:24<p>
 */


public class VerifyInputIdentityInfoActivity extends BaseActivity implements IDTypeAdapter.OnItemClickListener {


    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.real_name_et)
    EditText realNameEt;
    @BindView(R.id.real_name_line)
    View realNameLine;
    @BindView(R.id.certification_type_tv)
    TextView certificationTypeTv;
    @BindView(R.id.type_arrow)
    ImageView typeArrow;
    @BindView(R.id.certification_num_et)
    EditText certificationNumEt;
    @BindView(R.id.certification_num_line)
    View certificationNumLine;
    @BindView(R.id.next_step_tv)
    TextView nextStepTv;
    @BindView(R.id.type_recycleview)
    RecyclerView typeRecycleview;
    private IDTypeAdapter mAdapter;
    private ArrayList<IDTypeBean> mDataList;
    private boolean mIDTypeShow;
    private String name;
    private int mId;
    private String mIdNum;


    public static void launch(Activity pContext) {
        Intent mIntent = new Intent(pContext, VerifyInputIdentityInfoActivity.class);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_identityinfo);


    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResourcesString(R.string.verify_str));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        typeRecycleview.setLayoutManager(linearLayoutManager);
        mAdapter = new IDTypeAdapter(mContext);
        typeRecycleview.setAdapter(mAdapter);
        mDataList = new ArrayList<>();
        mDataList.add(new IDTypeBean(1, "身份证"));
        mDataList.add(new IDTypeBean(2, "社保卡"));
        mDataList.add(new IDTypeBean(3, "军人证"));
        mAdapter.changeData(mDataList);
        mAdapter.setOnItemClickListener(this);
        nextstepStatus();
        realNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nextstepStatus();
            }
        });
        certificationNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nextstepStatus();
            }
        });

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.toolbar_back_iv, R.id.type_arrow, R.id.next_step_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.type_arrow:
                mIDTypeShow = !mIDTypeShow;
                if (mIDTypeShow) {
                    typeArrow.setRotation(270);
                    typeRecycleview.setVisibility(View.VISIBLE);
                } else {
                    typeArrow.setRotation(90);
                    typeRecycleview.setVisibility(View.GONE);
                }

                break;
            case R.id.next_step_tv:
                VerifyEnsureActivity.launch(mContext, name, mId, mIdNum);
                break;
        }
    }


    @Override
    public void onItemClickListener(int position) {
        mIDTypeShow = false;
        typeArrow.setRotation(90);
        typeRecycleview.setVisibility(View.GONE);
        certificationTypeTv.setText(mDataList.get(position).getName());
        mId = mDataList.get(position).getId();
        nextstepStatus();
    }

    public void nextstepStatus() {
        name = realNameEt.getText().toString().trim();
        mIdNum = certificationNumEt.getText().toString().trim();
        if (!TextUtils.isEmpty(name) && mId != 0 && !TextUtils.isEmpty(mIdNum)) {
            nextStepTv.setEnabled(true);
            nextStepTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            nextStepTv.setEnabled(false);
            nextStepTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
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
