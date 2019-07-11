package com.lldj.tc.match;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dialog_Set extends BaseDialog {

    public Dialog_Set(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_set, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = this.getWindow();
        window.setGravity(Gravity.LEFT);
        window.setWindowAnimations(R.style.Anim_left);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        window.setAttributes(layoutParams);

        this.setCanceledOnTouchOutside(true);
    }

    @OnClick({R.id.back_main_iv, R.id.imset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_main_iv:
                dismiss();
                break;
            case R.id.imset:
                Toast.makeText(getContext(),"---------------Not yet implemented ",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
