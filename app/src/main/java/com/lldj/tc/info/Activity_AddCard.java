package com.lldj.tc.info;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.EventType;
import com.lldj.tc.utils.GlobalVariable;
import com.lsh.library.BankNumEditText;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.wintone.smartvision_bankCard.ScanCamera;

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

    private int bank_id = -1;

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

        bankNumEditText = (BankNumEditText) findViewById(R.id.bankCardNum);
        bankNumEditText.setFullVerify(false).setBankNameListener(new BankNumEditText.BankNameListener() {
                    @Override
                    public void success(String name) {
                        bankname.setText(name);
                    }

                    @Override
                    public void failure(int failCode, String failmsg) {
                        bankname.setText(failmsg + failCode);
                    }
                });

        bankNumEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        tvbankcardman.setText(SharePreUtils.getName(mContext));

        int[] picR = getIntent().getIntArrayExtra("PicR");
        char[] StringR = getIntent().getCharArrayExtra("StringR");
        if(StringR != null) {
            String _str = String.valueOf(StringR);
            bankNumEditText.setText(_str);
            bankNumEditText.setText(bankNumEditText.getBankNum() + "");
        }
//        Bitmap bitmap = Bitmap.createBitmap(picR, 400, 80, Bitmap.Config.ARGB_8888);
//        imageView.setImageBitmap(bitmap);

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

        HttpMsg.getInstance().sendSuportBankList(SharePreUtils.getToken(this), BankBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                BankBean res = (BankBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    List<BankBean.BankModel> list =  res.getResult();
                    if(list != null) {
                        _list.clear();
                        _list.addAll(list);
                    }
                }
            }
        });

        AppUtils.setEditTextHintSize(findViewById(R.id.bankCardNum), getResourcesString(R.string.getmoneywarm), 14);

    }

    public void onScanPress(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CAMERA).build(), new AcpListener() {
                @Override
                public void onGranted() {
                    startActivity(new Intent(mContext, ScanCamera.class));
                    finish();;
                }

                @Override
                public void onDenied(List<String> permissions) {
                }
            });
        }
    }

    private void setBankinfo(BankBean.BankModel bank){
        if(bank == null) return;
        bankname.setText(bank.getBank_name());
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.camera, R.id.selectlayout, R.id.selectbanklayout, R.id.addsure, R.id.toolbar_back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:
                onScanPress(view);
                break;
            case R.id.selectlayout:
                Toast.makeText(this, getResourcesString(R.string.cardname), Toast.LENGTH_SHORT).show();
                break;
            case R.id.selectbanklayout:
                DialogManager.getInstance().show(new DialogSelectCard(mContext, R.style.DialogTheme, _list, false));
                break;
            case R.id.addsure:

                String cardnum = bankNumEditText.getBankNum().toString();
                String bank = bankname.getText().toString().trim();
                if(TextUtils.isEmpty(bank)){
                    ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getResourcesString(R.string.cardbankname), ToastUtils.LENGTH_SHORT);
                    return;
                }
                else if(TextUtils.isEmpty(cardnum)){
                    ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getResourcesString(R.string.addcardnum), ToastUtils.LENGTH_SHORT);
                    return;
                }

                for (int i = 0; i < _list.size(); i++) {
                    if(bank.indexOf(_list.get(i).getBank_name()) != -1){
                        bank_id = _list.get(i).getBank_id();
                        break;
                    }
                }


                AppUtils.showLoading(this);
                HttpMsg.getInstance().sendBindCard(SharePreUtils.getToken(this), cardnum, bank_id+"", BankBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        BankBean res = (BankBean) _res;
                        if (res.getCode() == GlobalVariable.succ) {
                            SharePreUtils.setSelectBank(mContext, bank_id);
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
