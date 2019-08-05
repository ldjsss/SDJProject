package com.lldj.tc.toolslibrary.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lldj.tc.toolslibrary.R;

public class CustomDialog extends BaseDialog {
    private TextView tv_title, tv_message;
    private TextView bt_cancel, bt_confirm;

    private String title, message;
    private String cancel, confirm;

    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCancel(String cancel, IOnCancelListener cancelListener) {
        this.cancel = cancel;
        this.cancelListener = cancelListener;
    }

    public void setConfirm(String confirm, IOnConfirmListener confirmListener) {
        this.confirm = confirm;
        this.confirmListener = confirmListener;
    }

    //CustomDialog类的构造方法
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.8);
        Window window = this.getWindow();
        window.setAttributes(p);

        tv_title = findViewById(R.id.tv_title);
        tv_message = findViewById(R.id.tv_message);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_confirm = findViewById(R.id.bt_confirm);

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)) {
            bt_cancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)) {
            bt_confirm.setText(confirm);
        }

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onCancel(CustomDialog.this);
                }
                dismiss();
            }
        });

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onConfirm(CustomDialog.this);
                }
                dismiss();
            }
        });
    }

    public interface IOnCancelListener {
        void onCancel(CustomDialog dialog);
    }

    public interface IOnConfirmListener {
        void onConfirm(CustomDialog dialog);
    }
}
