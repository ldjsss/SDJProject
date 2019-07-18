package com.lldj.tc.info;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.DialogManager;
import com.lldj.tc.R;
import com.lldj.tc.http.beans.BankBean;
import com.lldj.tc.toolslibrary.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogSelectCard extends BaseDialog {
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.addlayout)
    LinearLayout addlayout;
    private List<BankBean.BankModel> _list = new ArrayList();

    public DialogSelectCard(@NonNull Context context, @StyleRes int themeResId, List<BankBean.BankModel> _list) {
        super(context, themeResId);

        this._list = _list;

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

            BankBean.BankModel bank = _list.get(i);
            String cardNum = bank.getCard();
            if(TextUtils.isEmpty(cardNum) || cardNum.length() < 4) return;
            String addString = cardNum.substring(cardNum.length() - 4, cardNum.length());
            ((TextView)addView.findViewById(R.id.bankName)).setText(bank.getCard_name() + "(" + addString + ")");
            LinearLayout bankcelllayout = addView.findViewById(R.id.bankcelllayout);
            addView.setTag(bank.getId());
            bankcelllayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int _id = (int)v.getTag();

                    Toast.makeText(getContext(), "---------------_id " + _id, Toast.LENGTH_SHORT).show();
                }
            });
        }

        View addBankView = View.inflate(context, R.layout.addbankcelllayout, null);
        addBankView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addlayout.addView(addBankView);

        addBankView.findViewById(R.id.addbankcelllayout).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogManager.getInstance().removeDialog("DialogSelectCard");
                Toast.makeText(getContext(), "------------dddddd---_id ", Toast.LENGTH_SHORT).show();
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
