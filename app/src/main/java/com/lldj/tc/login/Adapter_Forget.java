package com.lldj.tc.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.utils.HandlerType;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleDialog.BaseRecycleDialog;
import com.lldj.tc.toolslibrary.recycleDialog.RecycleCell;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class Adapter_Forget extends RecycleCell {

    public static BaseRecycleDialog forget;
    private static Disposable getCodeDisposable;

    private Context mContext;
    private ArrayList<String> mlist = new ArrayList<>();
    private Adapter_Forget.viewHolder mHolder = null;

    private String phoneNum = "";
    private String password = "";
    private String phoneCode = "";
    private int codeTime = 120;

    public Adapter_Forget(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<String> plist) {
        mlist = plist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_Forget.viewHolder(LayoutInflater.from(mContext).inflate(R.layout.dialog_forgetpassword, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mHolder = (Adapter_Forget.viewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return null != mlist ? mlist.size() : 0;
    }

    public static void dismiss() {
        if(forget != null) forget.dismiss();
        forget = null;
        if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
    }

    public static void showView(Context context){
        if(forget != null) return;
        forget = new BaseRecycleDialog(context, R.style.DialogTheme);
        forget.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismiss();
            }
        });
        forget.setbBckground(context.getResources().getColor(R.color.color_bg));
        Window window = forget.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setWindowAnimations(R.style.Anim_fade);
        forget.showView(new Adapter_Forget(context));
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.toolbar_title_tv)
        StrokeTextView toolbarTitleTv;
        @BindView(R.id.toolbar_root_layout)
        RelativeLayout toolbarRootLayout;
        @BindView(R.id.connectservice)
        RelativeLayout connectservice;
        @BindView(R.id.rescodetel_num_et)
        EditText rescodetelNumEt;
        @BindView(R.id.resverifycode_et)
        EditText resverifycodeEt;
        @BindView(R.id.respsw_title)
        TextView respswTitle;
        @BindView(R.id.res_paset)
        EditText resPaset;
        @BindView(R.id.reslayoutpar)
        RelativeLayout reslayoutpar;
        @BindView(R.id.resget_verify_codebtn)
        Button resgetVerifyCodebtn;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            connectservice.setVisibility(View.VISIBLE);
            ImmersionBar.with((Activity)mContext).titleBar(toolbarRootLayout).init();
            toolbarTitleTv.setText(mContext.getResources().getString(R.string.forget_pswTitle));

        }


        @OnClick({R.id.toolbar_back_iv, R.id.toolbar_title_tv, R.id.connectservice, R.id.resget_verify_codebtn, R.id.register_tv})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.toolbar_back_iv:
                    dismiss();
                    break;
                case R.id.toolbar_title_tv:
                    break;
                case R.id.connectservice:
                    ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, "暂未实现！", ToastUtils.LENGTH_SHORT);
                    break;
                case R.id.resget_verify_codebtn:
                    phoneNum = rescodetelNumEt.getText().toString().trim();
                    if (!AppUtils.isMobileNO(phoneNum)) {
                        showToast(R.string.errorRemind5);
                        return;
                    }
                    codeTime = 120;
                    resgetVerifyCodebtn.setEnabled(false);

                    if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
                    getCodeDisposable = RxTimerUtilPro.interval(1000, new RxTimerUtilPro.IRxNext() {
                        @Override
                        public void doNext(long number) {
                            codeTime--;
                            if (codeTime <= 0) {
                                if (getCodeDisposable != null) RxTimerUtilPro.cancel(getCodeDisposable);
                                getCodeDisposable = null;
                                resgetVerifyCodebtn.setEnabled(true);
                                resgetVerifyCodebtn.setText(mContext.getText(R.string.get_verify_code));
                                return;
                            }
                            resgetVerifyCodebtn.setText(codeTime + "s");
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                    HttpMsg.getInstance().sendGetCode(phoneNum, JsonBean.class, new HttpMsg.Listener() {
                        @Override
                        public void onFinish(Object _res) {
                            JsonBean res = (JsonBean) _res;
                            if(res.getCode() == GlobalVariable.succ) {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.codeHaveSend), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
                case R.id.register_tv:
                    if (!checkAll()) return;
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                    HttpMsg.getInstance().sendForgetKey(phoneNum, password, phoneCode, JsonBean.class, new HttpMsg.Listener() {
                        @Override
                        public void onFinish(Object _res) {
                            JsonBean res = (JsonBean) _res;
                            if(res.getCode() == GlobalVariable.succ) {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.passwordHaveChange), Toast.LENGTH_SHORT).show();
                                SharePreUtils.setPassWord(mContext, password);
                                HandlerInter.getInstance().sendEmptyMessage(HandlerType.REGISTSUCC);
                                dismiss();
                            }
                        }
                    });
                    break;
            }
        }

        private void showToast(int id) {
            ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getResources().getString(id), ToastUtils.LENGTH_SHORT);
        }

        private boolean checkAll() {

            password = resPaset.getText().toString().trim();
            phoneNum = rescodetelNumEt.getText().toString().trim();
            phoneCode = resverifycodeEt.getText().toString().trim();

            if (TextUtils.isEmpty(phoneNum) || !AppUtils.isMobileNO(phoneNum)) {
                showToast(R.string.errorRemind5);
                return false;
            }
            if (TextUtils.isEmpty(phoneCode)) {
                showToast(R.string.errorRemind6);
                return false;
            }
            if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
                showToast(R.string.errorRemind1);
                return false;
            }

            return true;
        }


    }

}

