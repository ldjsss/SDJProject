package com.lldj.tc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lldj.tc.toolslibrary.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 职业选择页面<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/4 18:12<p>
 * Modify Time: 2018/12/4 18:12<p>
 */


public class JobSelectActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.jump_tv)
    TextView jumpTv;
    @BindView(R.id.medical_personal_tv)
    TextView medicalPersonalTv;
    @BindView(R.id.nurse_tv)
    TextView nurseTv;
    @BindView(R.id.pharmacy_personnel_tv)
    TextView pharmacyPersonnelTv;
    @BindView(R.id.technician_tv)
    TextView technicianTv;
    @BindView(R.id.public_health_personnel_tv)
    TextView publicHealthPersonnelTv;
    @BindView(R.id.basic_medical_professionals_tv)
    TextView basicMedicalProfessionalsTv;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, JobSelectActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_select);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        titleTv.setText(getResources().getString(R.string.register_ok));

    }

    @OnClick({R.id.jump_tv, R.id.medical_personal_tv, R.id.nurse_tv, R.id.pharmacy_personnel_tv, R.id.technician_tv, R.id.public_health_personnel_tv, R.id.basic_medical_professionals_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jump_tv:
                MainActivity.launch(mContext);
                break;
            case R.id.medical_personal_tv:
                MainActivity.launch(mContext);
                break;
            case R.id.nurse_tv:
                MainActivity.launch(mContext);
                break;
            case R.id.pharmacy_personnel_tv:
                MainActivity.launch(mContext);
                break;
            case R.id.technician_tv:
                MainActivity.launch(mContext);
                break;
            case R.id.public_health_personnel_tv:
                MainActivity.launch(mContext);
                break;
            case R.id.basic_medical_professionals_tv:
                MainActivity.launch(mContext);
                break;
        }
    }
}
