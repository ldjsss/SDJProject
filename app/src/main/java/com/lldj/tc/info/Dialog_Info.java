package com.lldj.tc.info;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.lldj.tc.R;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.view.BaseDialog;
import com.lldj.tc.utils.HandlerType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dialog_Info extends BaseDialog {

    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.tvbrithday)
    TextView tvbrithday;
    @BindView(R.id.tvphone)
    TextView tvphone;
    @BindView(R.id.phonelayout)
    RelativeLayout phonelayout;

    public Dialog_Info(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.dialog_myinfo, null);
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

        tvname.setText(SharePreUtils.getName(context));
        tvphone.setText(SharePreUtils.getPhone(context));
    }

    @OnClick({R.id.back_main_iv, R.id.brithlayout, R.id.phonelayout, R.id.keylayout, R.id.exitlayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_main_iv:
                dismiss();
                break;
            case R.id.brithlayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phonelayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.keylayout:
                Toast.makeText(getContext(), "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exitlayout:
                SharePreUtils.setToken(getContext(), "");
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEAVEGAME);
                break;
        }
    }
}
