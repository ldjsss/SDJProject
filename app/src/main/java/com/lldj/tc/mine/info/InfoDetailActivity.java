package com.lldj.tc.mine.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lldj.tc.R;
import com.lldj.tc.cropimage.PhotoEvent;
import com.lldj.tc.login.VerifyCodeLogin.IVerifyLoginPresenterCompl;
import com.lldj.tc.mine.EditNameActivity;
import com.lldj.tc.photo.OpenCameraOrGalleryActivity;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 个人信息<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/6 13:57<p>
 * Modify Time: 2018/12/6 13:57<p>
 */


public class InfoDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.photo_layout)
    RelativeLayout photoLayout;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.name_layout)
    RelativeLayout nameLayout;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.nick_name_layout)
    RelativeLayout nickNameLayout;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.phone_layout)
    RelativeLayout phoneLayout;
    @BindView(R.id.career_tv)
    TextView careerTv;
    @BindView(R.id.career_layout)
    RelativeLayout careerLayout;
    @BindView(R.id.dapartment_tv)
    TextView dapartmentTv;
    @BindView(R.id.dapartment_layout)
    RelativeLayout dapartmentLayout;
    @BindView(R.id.job_title_tv)
    TextView jobTitleTv;
    @BindView(R.id.job_title_layout)
    RelativeLayout jobTitleLayout;
    @BindView(R.id.my_skills_recycleview)
    RecyclerView mySkillsRecycleview;
    @BindView(R.id.add_more_skills_tv)
    LinearLayout addMoreSkillsTv;
    @BindView(R.id.input_num_tv)
    TextView inputNumTv;
    @BindView(R.id.caree_tip_close_iv)
    ImageView careeTipCloseIv;
    @BindView(R.id.caree_tip_root_layout)
    LinearLayout careeTipRootLayout;
    @BindView(R.id.department_fragement)
    FrameLayout departmentFragement;
    @BindView(R.id.job_title_fragement)
    FrameLayout jobTitleFragement;
    private IVerifyLoginPresenterCompl iLoginPresenter;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, InfoDetailActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        toolbarTitleTv.setText("个人信息");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String uName = SharePreUtils.getUserName(mContext);
        if (!TextUtils.isEmpty(uName)) {
            nameTv.setText(uName);
        }
        String nickNameStr = SharePreUtils.getNickName(mContext);
        if (!TextUtils.isEmpty(nickNameStr)) {
            nickNameTv.setText(nickNameStr);
        }
        String mTelNumStr = SharePreUtils.getTelNum(mContext);
        if (!TextUtils.isEmpty(mTelNumStr)) {
            phoneTv.setText(mTelNumStr);
        }
        setDepartment();
        setJobTitle();
    }

    @OnClick({R.id.toolbar_back_iv, R.id.photo_layout, R.id.name_layout, R.id.nick_name_layout, R.id.phone_layout, R.id.career_layout, R.id.dapartment_layout, R.id.job_title_layout, R.id.caree_tip_close_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.photo_layout:
                OpenCameraOrGalleryActivity.launch(mContext);
                break;
            case R.id.name_layout:
                EditNameActivity.launch(mContext, nameTv.getText().toString(), 0);
                break;
            case R.id.nick_name_layout:
                EditNameActivity.launch(mContext, nickNameTv.getText().toString(), 1);
                break;
            case R.id.phone_layout:
                ModifyTelNumActivity.launch(mContext, phoneTv.getText().toString().trim());
                break;
            case R.id.career_layout:
                careeTipRootLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.dapartment_layout:
                replace(R.id.department_fragement, new dapartmentFragement());
                departmentFragement.setVisibility(View.VISIBLE);
                break;
            case R.id.job_title_layout:
                replace(R.id.job_title_fragement, new JobTitleFragement());
                jobTitleFragement.setVisibility(View.VISIBLE);
                break;
            case R.id.caree_tip_close_iv:
                careeTipRootLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100 && null != data) {
            String mNameStr = data.getStringExtra("name");
            int mType = data.getIntExtra("type", 0);
            if (mType == 0) {
                nameTv.setText(mNameStr);
            } else {
                nickNameTv.setText(mNameStr);
            }
        }
    }


    public void setDepartment() {
        departmentFragement.setVisibility(View.GONE);
        String departmentstr = SharePreUtils.getDepartment(mContext);
        if (null != departmentstr) {
            dapartmentTv.setText(departmentstr);
        }
    }

    public void setJobTitle() {
        jobTitleFragement.setVisibility(View.GONE);
        String jobTItlestr = SharePreUtils.getJobTitle(mContext);
        if (null != jobTItlestr) {
            jobTitleTv.setText(jobTItlestr);
        }
    }

    @Subscribe
    public void onEvent(Object pObject) {
        if (pObject instanceof PhotoEvent) {
            final PhotoEvent mPhotoEvent = (PhotoEvent) pObject;
            if (mPhotoEvent.getmActionName().equals("ok")) {
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(InfoDetailActivity.this)
                                .load(mPhotoEvent.getPhotoPath())
                                .into(photoIv);
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
