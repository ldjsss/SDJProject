package com.lldj.tc.firstpage;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.AppUtils;

import butterknife.ButterKnife;

public class DialogBetResult extends Dialog {
    public DialogBetResult(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.betresult, null);
        setContentView(view);

        ButterKnife.bind(this, view);

        this.setCanceledOnTouchOutside(false);

        getWindow().setDimAmount(0.5f);

        Window window = this.getWindow();
        window.setLayout((int)(AppUtils.getDisplayMetrics(getContext()).widthPixels*0.96), ViewGroup.LayoutParams.WRAP_CONTENT);//设置对话框大小
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void show() {
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
    }
}
