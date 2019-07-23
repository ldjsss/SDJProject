package com.lldj.tc.info;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BankBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.StringUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lsh.library.BankNumEditText;
import com.wintone.smartvision_bankCard.ScanCamera;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_AddCard extends BaseActivity{

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
//    @BindView(R.id.editaddcardnum)
//    EditText editaddcardnum;
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

    private List<BankBean.BankModel> _list = new ArrayList<>();
    public static final int MY_SCAN_REQUEST_CODE = 10;

    private BankNumEditText bankNumEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addcardlayout);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);


        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResourcesString(R.string.addcardtitle));

//        editaddcardnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//            }
//        });

        bankNumEditText = (BankNumEditText) findViewById(R.id.bankCardNum);
        bankNumEditText.setFullVerify(false).setBankNameListener(new BankNumEditText.BankNameListener() {
                    @Override
                    public void success(String name) {
                        bankname.setText(name);
                    }

                    @Override
                    public void failure(int failCode, String failmsg) {
                        bankname.setText(failCode+failmsg);
                    }
                });

        tvbankcardman.setText(SharePreUtils.getName(mContext));

        registEvent(new Observer<ObData>() {
            @Override
            public void onUpdate(Observable<ObData> observable, ObData data) {
                if (data.getKey().equalsIgnoreCase(EventType.SELECTBANK)) {
                    BankBean.BankModel _bank = (BankBean.BankModel) data.getValue();
                    if(_bank == null) return;
                    setBankinfo(_bank);
                }
            }
        });

        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_29), R.mipmap.user_bank_29));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_30), R.mipmap.user_bank_30));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_31), R.mipmap.user_bank_31));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_32), R.mipmap.user_bank_32));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_33), R.mipmap.user_bank_33));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_34), R.mipmap.user_bank_34));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_35), R.mipmap.user_bank_35));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_36), R.mipmap.user_bank_36));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_37), R.mipmap.user_bank_37));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_38), R.mipmap.user_bank_38));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_39), R.mipmap.user_bank_39));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_40), R.mipmap.user_bank_40));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_41), R.mipmap.user_bank_41));
        _list.add(new BankBean.BankModel(getResourcesString(R.string.user_bank_42), R.mipmap.user_bank_42));

    }

    public void onScanPress(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CAMERA).build(), new AcpListener() {
                @Override
                public void onGranted() {
                    startActivity(new Intent(mContext, ScanCamera.class));
                }

                @Override
                public void onDenied(List<String> permissions) {
                }
            });
        }
    }

    private void setBankinfo(BankBean.BankModel bank){
        if(bank == null) return;
        bankname.setText(bank.getCard_name());
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.camera, R.id.selectlayout, R.id.selectbanklayout, R.id.addsure, R.id.toolbar_back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:
//                Toast.makeText(this, "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                onScanPress(view);
                break;
            case R.id.selectlayout:
                Toast.makeText(this, getResourcesString(R.string.cardname), Toast.LENGTH_SHORT).show();
                break;
            case R.id.selectbanklayout:
                DialogManager.getInstance().show(new DialogSelectCard(mContext, R.style.DialogTheme, _list, false));
                break;
            case R.id.addsure:

                String cardnum = (bankNumEditText.getText().toString());
                cardnum = cardnum.replaceAll("\\s*", "");
                String bank = bankname.getText().toString().trim();
                if(TextUtils.isEmpty(bank)){
                    ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getResourcesString(R.string.cardbankname), ToastUtils.LENGTH_SHORT);
                    return;
                }
                else if(TextUtils.isEmpty(cardnum)){
                    ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getResourcesString(R.string.addcardnum), ToastUtils.LENGTH_SHORT);
                    return;
                }

                AppUtils.showLoading(this);
                HttpMsg.getInstance().sendBindCard(SharePreUtils.getToken(this), cardnum, bank, BankBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        BankBean res = (BankBean) _res;
                        if (res.getCode() == GlobalVariable.succ) {
                            ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, getResourcesString(R.string.addcardsucc), ToastUtils.LENGTH_SHORT);
                            close();
                        }
                    }
                });
                break;
            case R.id.toolbar_back_iv:
                close();
                break;
        }
    }

    private void close(){
        Intent _intent = new Intent(this, Activity_Getmoney.class);
        _intent.putExtra("Anim_fade", R.style.Anim_left);
        startActivity(_intent);
        finish();
    }

}
