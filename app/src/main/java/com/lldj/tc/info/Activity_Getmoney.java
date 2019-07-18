package com.lldj.tc.info;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.BankBean;
import com.lldj.tc.http.beans.BaseBean;
import com.lldj.tc.http.beans.FormatModel.RecordModel;
import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.http.beans.RecordBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Getmoney extends BaseActivity {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.imservice)
    ImageView imservice;
    @BindView(R.id.tvservices)
    TextView tvservices;
    @BindView(R.id.connectservice)
    RelativeLayout connectservice;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.tvbankcard)
    TextView tvbankcard;
    @BindView(R.id.editmoney)
    EditText editmoney;
    @BindView(R.id.tvmoneypoket)
    TextView tvmoneypoket;
    @BindView(R.id.tvallget)
    TextView tvallget;
    @BindView(R.id.tbgetsure)
    TextView tbgetsure;

    private List<BankBean.BankModel> _list = new ArrayList<>();
    private int bankid = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.getcashlayout);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);

        bankid = SharePreUtils.getSelectBank(this);

        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.getmoneytitle));

        editmoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        tvmoneypoket.setText(String.format(getResources().getString(R.string.moneypoket), SharePreUtils.getMoney(this)));

        HttpMsg.getInstance().sendBankList(SharePreUtils.getToken(this), BankBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                BankBean res = (BankBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    _list =  res.getResult();

                    if(_list.size() > 0){
                        setBankinfo(_list.get(0));
                    }
                    for (BankBean.BankModel bank: _list) {
                        if(bank.getId() == bankid){
                            setBankinfo(bank);
                            break;
                        }
                    }
                }
            }
        });

    }

    private void setBankinfo(BankBean.BankModel bank){
        if(bank == null) return;
        String cardNum = bank.getCard();
        if(TextUtils.isEmpty(cardNum) || cardNum.length() < 4) return;
        String addString = cardNum.substring(cardNum.length() - 4, cardNum.length());
        tvbankcard.setText(SharePreUtils.getName(mContext) + " " + bank.getCard_name() + "(" + addString + ")");
        tvbankcard.setTag(bank.getId());
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

    }

    @OnClick({R.id.toolbar_back_iv, R.id.tvbankcard, R.id.tvallget, R.id.tbgetsure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            case R.id.tvbankcard:
                break;
            case R.id.tvallget:
                String money = SharePreUtils.getMoney(this);
                editmoney.setText(Math.ceil(Double.parseDouble(money)) + "");
                break;
            case R.id.tbgetsure:
                Object _id = tvbankcard.getTag();
                String _money = editmoney.getText().toString().trim();
                if(_id == null || _id == ""){
                    ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getResources().getString(R.string.selectbankcard), ToastUtils.LENGTH_SHORT);
                    return;
                }
                else if(TextUtils.isEmpty(_money)){
                    ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getResources().getString(R.string.inputmoneywarm), ToastUtils.LENGTH_SHORT);
                    return;
                }
                int _bankid = (int)_id;

                SharePreUtils.setSelectBank(this, _bankid);

                HttpMsg.getInstance().sendGetCash(SharePreUtils.getToken(this), _money, _bankid+"", BaseBean.class, new HttpMsg.Listener() {
                    @Override
                    public void onFinish(Object _res) {
                        BankBean res = (BankBean) _res;
                        if (res.getCode() == GlobalVariable.succ) {
                            Toast.makeText(mContext, "---------------succ ", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, "---------------fail ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}
