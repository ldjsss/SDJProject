package com.lldj.tc.info;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.beans.BankBean;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.utils.EventType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class DialogSelectCard extends BaseDialog {
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.addlayout)
    LinearLayout addlayout;
    @BindView(R.id.addparlayout)
    RelativeLayout addparlayout;
    private List<BankBean.BankModel> _list = new ArrayList();

    public DialogSelectCard(@NonNull Context context, @StyleRes int themeResId, List<BankBean.BankModel> _list, boolean add) {
        super(context, themeResId);

        if(_list != null)this._list = _list;

        View view = View.inflate(context, R.layout.selectbanklayout, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_bet_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(false);

        for (int i = 0; i < _list.size(); i++) {
            View addView = View.inflate(context, R.layout.bankcelllayout, null);
            addView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addlayout.addView(addView);
            Log.e("---addlayout.height = " + addlayout.getLayoutParams().height, "--addparlayout.height = " + addparlayout.getLayoutParams().height);
            if(addlayout.getLayoutParams().height > addparlayout.getLayoutParams().height) addlayout.getLayoutParams().height = addparlayout.getLayoutParams().height;

            BankBean.BankModel bank = _list.get(i);
            String cardNum = bank.getCard();
            String addString = (!TextUtils.isEmpty(cardNum) && cardNum.length() > 4) ? "(" + cardNum.substring(cardNum.length() - 4, cardNum.length()) + ")" : "";
            ((TextView) addView.findViewById(R.id.bankName)).setText(bank.getBank_name() + addString);
            LinearLayout bankcelllayout = addView.findViewById(R.id.bankcelllayout);
            addView.setTag(i);
            bankcelllayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int _index = (int) v.getTag();
                    AppUtils.dispatchEvent(new ObData(EventType.SELECTBANK, _list.get(_index)));
                    DialogManager.getInstance().removeDialog("DialogSelectCard");
                }
            });

            HttpTool.getBitmapUrl(bank.getLogo(), new HttpTool.bmpListener() {
                @Override
                public void onFinish(Bitmap bitmap) {
                    if (bitmap != null) ((ImageView)addView.findViewById(R.id.bankicon)).setImageBitmap(bitmap);
                }
            });
        }

        if(!add) return;

        View addBankView = View.inflate(context, R.layout.addbankcelllayout, null);
        addBankView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addlayout.addView(addBankView);

        addBankView.findViewById(R.id.addbankcelllayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getInstance().removeDialog("DialogSelectCard");
                getContext().startActivity(new Intent(getContext(), Activity_AddCard.class));
                bActivity.overridePendingTransition(0, R.anim.out_to_left);
                bActivity.finish();
            }
        });
    }

    @OnClick({R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                DialogManager.getInstance().removeDialog("DialogSelectCard");
                break;
        }
    }
}
