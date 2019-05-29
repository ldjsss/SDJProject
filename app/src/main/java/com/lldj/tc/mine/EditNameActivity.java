package com.lldj.tc.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.mine.info.InfoBean;
import com.lldj.tc.retrofit_services.UserServices;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.immersionbar.OnKeyboardListener;
import com.lldj.tc.toolslibrary.retrofit.BaseEntity;
import com.lldj.tc.toolslibrary.retrofit.BaseObserver;
import com.lldj.tc.toolslibrary.retrofit.RetrofitConfig;
import com.lldj.tc.toolslibrary.retrofit.RetrofitUtils;
import com.lldj.tc.toolslibrary.retrofit.RxSchedulerHepler;
import com.lldj.tc.toolslibrary.util.KeyboardUtil;
import com.lldj.tc.toolslibrary.util.TextWatcherUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * description: 输入真实姓名<p>
 * user: wangclia<p>
 * Creat Time: 2018/12/5 18:56<p>
 * Modify Time: 2018/12/5 18:56<p>
 */


public class EditNameActivity extends BaseActivity implements OnKeyboardListener, TextWatcherUtils.TextWatcherListener {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbarTitleTv;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.tel_num_line)
    View telNumLine;
    @BindView(R.id.save_tv)
    TextView saveTv;
//    @BindView(R.id.loding_layout)
//    RelativeLayout lodingLayout;
    //0修改姓名，1 修改昵称
    private int mType;
    String mNameStr;


    public static void launch(Activity pContext, String pName, int pType) {
        Intent mIntent = new Intent(pContext, EditNameActivity.class);
        mIntent.putExtra(NAME, pName);
        mIntent.putExtra(TYPE, pType);
        pContext.startActivityForResult(mIntent, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        mType = getIntent().getIntExtra(TYPE, 0);
        mNameStr = getIntent().getStringExtra("name");
        switch (mType) {
            case 0:
                toolbarTitleTv.setText("修改姓名");
                nameEt.setHint("请输入您的真实姓名");
                break;
            case 1:
                toolbarTitleTv.setText("修改昵称");
                nameEt.setHint("请输入您的昵称");
                break;
        }

        if (!TextUtils.isEmpty(mNameStr)) {
            nameEt.setText(mNameStr);
            nameEt.setSelection(nameEt.length());
        }
        setBtnStatus();
        TextWatcherUtils mTextWatcher = new TextWatcherUtils(mContext, nameEt, 20, true);
        mTextWatcher.setTextChangedListener(this);
        nameEt.addTextChangedListener(mTextWatcher);

    }


    @Override
    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
        Log.e("键盘弹出状态", isPopup + "===");
    }

    @Override
    public void onBackPressed() {
        exitActivity();

    }


    @OnClick({R.id.toolbar_back_iv, R.id.save_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                exitActivity();
                break;
            case R.id.save_tv:
                mNameStr = nameEt.getText().toString().trim();
                getData();
//                switch (mType) {
//                    case 0:
//                        SharePreUtils.setUserName(mContext, mNameStr);
//                        break;
//                    case 1:
//                        SharePreUtils.setNickName(mContext, mNameStr);
//                        break;
//                }
//                exitActivity();

                break;
        }
    }


    /**
     * 设置按钮的状态
     */
    public void setBtnStatus() {
        if (!TextUtils.isEmpty(mNameStr)) {
            saveTv.setEnabled(true);
            saveTv.setBackgroundResource(R.drawable.rec_00be0a_round100);
        } else {
            saveTv.setEnabled(false);
            saveTv.setBackgroundResource(R.drawable.rec_c8c8c8_round100);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mNameStr = nameEt.getText().toString().trim();
        setBtnStatus();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void currentTextLength(long pTextLength) {

    }

    public void getData() {
        saveTv.setEnabled(false);
//        lodingLayout.setVisibility(View.VISIBLE);
        UserServices listServices = RetrofitUtils.INSTANCE.getSpecialClient(RetrofitConfig.TEST_HOST_URL_9999, UserServices.class);
        Observable<BaseEntity<InfoBean>> m;
        if (mType == 0) {
            m = listServices.saveAndUpdateName(mNameStr, "");
        } else {
            m = listServices.saveAndUpdateName("", mNameStr);
        }
        m.compose(RxSchedulerHepler.<BaseEntity<InfoBean>>io_main())
                .subscribe(new BaseObserver<InfoBean>() {
                    @Override
                    protected void onSuccess(InfoBean infoBean) {
                        saveTv.setEnabled(true);
//                        lodingLayout.setVisibility(View.GONE);
                        switch (mType) {
                            case 0:
                                SharePreUtils.setUserName(mContext, mNameStr);
                                break;
                            case 1:
                                SharePreUtils.setNickName(mContext, mNameStr);
                                break;
                        }
                        exitActivity();
                    }

                    @Override
                    protected void onFail(int code, String msg) {
                        saveTv.setEnabled(true);
//                        lodingLayout.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, msg, ToastUtils.LENGTH_SHORT);
                        }
                    }
                });

    }

    public void exitActivity() {
        KeyboardUtil.hideInput(mContext, nameEt);
        finish();
        overridePendingTransition(0, 0);
    }
}
