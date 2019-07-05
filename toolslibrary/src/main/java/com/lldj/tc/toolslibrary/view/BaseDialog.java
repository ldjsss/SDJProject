package com.lldj.tc.toolslibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lldj.tc.toolslibrary.util.AppUtils;

public class BaseDialog extends Dialog {
    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
        AppUtils.fullScreenImmersive(getWindow().getDecorView());
    }
}
