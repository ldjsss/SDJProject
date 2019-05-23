package com.lldj.tc.mine.verify.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AssetUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 职业认证--选择职业<p>
 * user: wlj<p>
 * Creat Time: 2018/12/17 10:18<p>
 * Modify Time: 2018/12/17 10:18<p>
 */


public class JobVerifyJobActivity extends BaseActivity implements VerifyJobAdapter.OnItemClickListener {
    @BindView(R.id.job_recycleview)
    RecyclerView jobRecycleview;
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.next_step_tv)
    TextView nextStepTv;
    private String mSelectJobStr;
    private VerifyJobAdapter mAdapter;
    private ArrayList<JobBean> mJobList;

    public static void launch(Activity pContext) {
        Intent mIntent = new Intent(pContext, JobVerifyJobActivity.class);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_job_job);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText("职业认证");
        nextstepStatus();
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        jobRecycleview.setLayoutManager(manager);
        mAdapter = new VerifyJobAdapter(mContext);
        jobRecycleview.setAdapter(mAdapter);
        mJobList = new ArrayList<>();
        mJobList.add(new JobBean("医疗人员"));
        mJobList.add(new JobBean("护理人员"));
        mJobList.add(new JobBean("药学人员"));
        mJobList.add(new JobBean("公共卫生人员"));
        mJobList.add(new JobBean("医技人员"));
        mJobList.add(new JobBean("基础医学专业人员"));
        mAdapter.changeData(mJobList);
        mAdapter.setOnItemClickListener(this);
        String json_str = AssetUtil.getText(mContext, "city.text");
        Gson gson = new Gson();
//        cityModel = gson.fromJson(json_str, new TypeToken<CityModel>() {
//        }.getType());

    }

    @OnClick({R.id.toolbar_back_iv, R.id.next_step_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                break;
            case R.id.next_step_tv:
                JobVerifyCityActivity.launch(mContext);
                break;
        }
    }


    /**
     * 下一步按钮的状态
     */
    public void nextstepStatus() {
        if (!TextUtils.isEmpty(mSelectJobStr)) {
            nextStepTv.setEnabled(true);
            nextStepTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            nextStepTv.setEnabled(false);
            nextStepTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }

    @Override
    public void onItemClickListener(int position) {
        mSelectJobStr = mJobList.get(position).getJob();
        for (int i = 0; i < mJobList.size(); i++) {
            if (position == i) {
                mJobList.get(i).setSelect(true);
            } else {
                mJobList.get(i).setSelect(false);
            }
        }
        mAdapter.changeData(mJobList);
        nextstepStatus();
    }
}
