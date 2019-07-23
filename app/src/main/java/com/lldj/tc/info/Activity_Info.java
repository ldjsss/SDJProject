package com.lldj.tc.info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.utils.HandlerType;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Info extends BaseActivity {

    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.tvbrithday)
    TextView tvbrithday;
    @BindView(R.id.tvphone)
    TextView tvphone;
    @BindView(R.id.phonelayout)
    RelativeLayout phonelayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int Anim_fade = intent.getIntExtra("Anim_fade", R.style.Anim_fade);

        setContentView(R.layout.dialog_myinfo);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = Anim_fade;
        getWindow().setAttributes(params);

        tvname.setText(SharePreUtils.getName(this));
        tvphone.setText(SharePreUtils.getPhone(this));

        if(AppUtils.isNumer(SharePreUtils.getInstance().birthday))tvbrithday.setText(AppUtils.getFormatTime2(Long.parseLong(SharePreUtils.getInstance().birthday)));
    }

    @OnClick({R.id.back_main_iv, R.id.brithlayout, R.id.phonelayout, R.id.keylayout, R.id.exitlayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_main_iv:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            case R.id.brithlayout:
                Calendar calendar = Calendar.getInstance();
                if(AppUtils.isNumer(SharePreUtils.getInstance().birthday))calendar.setTimeInMillis(Long.parseLong(SharePreUtils.getInstance().birthday));
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SharePreUtils.getInstance().birthday = date.getTime() + "";
                        tvbrithday.setText(AppUtils.getFormatTime2(Long.parseLong(SharePreUtils.getInstance().birthday)));

                        Toast.makeText(Activity_Info.this, "---------------服务器接口不支持 ", Toast.LENGTH_SHORT).show();
                    }
                })

                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText(getResourcesString(R.string.btncancle))//取消按钮文字
                        .setSubmitText(getResourcesString(R.string.btnsure))//确认按钮文字
    //                .setContentSize(18)//滚轮文字大小
    //                .setTitleSize(20)//标题文字大小
    //                //.setTitleText("Title")//标题文字
    //                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
    //                .isCyclic(true)//是否循环滚动
                    .setTitleColor(Color.WHITE)//标题文字颜色
                    .setSubmitColor(R.color.color_cdc3b3)//确定按钮文字颜色
                    .setCancelColor(R.color.color_cdc3b3)//取消按钮文字颜色
                    .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                    .setBgColor(0x293041)//滚轮背景颜色 Night mode
                    .setTextColorCenter(Color.WHITE)
                    .setDate(calendar)// 如果不设置的话，默认是系统时间*/
    //                .setRangDate(startDate,endDate)//起始终止年月日设定
    //                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                            //.isDialog(true)//是否显示为对话框样式
                            .build();

                pvTime.show();

                break;
            case R.id.phonelayout:
                DialogManager.getInstance().show(new Dialog_ChangePhone(this, R.style.DialogTheme));
                break;
            case R.id.keylayout:
                DialogManager.getInstance().show(new Dialog_Change(this, R.style.DialogTheme));
                break;
            case R.id.exitlayout:
                finish();
                SharePreUtils.setToken(this, "");
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEAVEGAME);
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
