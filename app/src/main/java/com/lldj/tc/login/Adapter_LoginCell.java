package com.lldj.tc.login;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.JsonBean;
import com.lldj.tc.mainUtil.GlobalVariable;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.recycleDialog.RecycleCell;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Adapter_LoginCell extends RecycleCell {

    private Context mContext;
    private ArrayList<String> mlist = new ArrayList<>();
    private viewHolder mHolder = null;

    public Adapter_LoginCell(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<String> plist) {
        mlist = plist;
        notifyDataSetChanged();
    }

    public void handleMsg(Message msg) {
        mHolder.handleMsg(msg);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.dialog_login, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mHolder = (viewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return null != mlist ? mlist.size() : 0;
    }


    //////////////////////////////////////////////////
    //////////////////////////////////////////////////

    class viewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.frameaddlayout)
        FrameLayout frameaddlayout;
        @BindView(R.id.tel_num_et)
        EditText telNumEt;
        @BindView(R.id.psw_et)
        EditText pswEt;
        @BindView(R.id.psw_show_or_hid_iv)
        ImageView pswShowOrHidIv;

        private int screenHeight = 0;
        private String userCount = "";
        private String password = "";
        private boolean mPswIsShow = false;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;

            if (frameaddlayout.getLayoutParams().height <= screenHeight)
                frameaddlayout.getLayoutParams().height = screenHeight;

            //设置密码的显示or隐藏
            pswStatus(pswEt, pswShowOrHidIv);

            tokenLogin();

        }

        @OnClick({R.id.forget_psw_tv, R.id.login_tv, R.id.register_tv, R.id.just_look_tv, R.id.psw_show_or_hid_iv})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.forget_psw_tv:
                    Adapter_Forget.showView(mContext);
                    break;
                case R.id.login_tv:
                    if (!checkAll()) return;
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                    HttpMsg.getInstance().sendLogin(userCount, password, JsonBean.class, new HttpMsg.Listener() {

                        @Override
                        public void onFinish(Object _res) {
                            JsonBean res = (JsonBean)_res;
                            if (res.getCode() == GlobalVariable.succ) {
                                saveLoginData(res);
                            }
                        }
                    });
                    break;
                case R.id.register_tv:
                    Adapter_Register.showView(mContext);
                    break;
                case R.id.just_look_tv:
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.JUSTLOOK);
                    break;
                case R.id.psw_show_or_hid_iv:
                    pswStatus(pswEt, pswShowOrHidIv);
            }
        }

        private void tokenLogin() {
            String token = SharePreUtils.getToken(mContext);
            if(TextUtils.isEmpty(token)) return;
            HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
            HttpMsg.getInstance().sendTokenLogin(token, JsonBean.class, new HttpMsg.Listener() {
                @Override
                public void onFinish(Object _res) {
                    JsonBean res = (JsonBean) _res;
                    if (res.getCode() == GlobalVariable.succ) {
                        saveLoginData(res);
                    }
                }
            });
        }

        private void saveLoginData(JsonBean res) {
            ResultsModel ret = (ResultsModel)res.getResult();
            SharePreUtils.getInstance().setLoginInfo(mContext, ret.getAccess_token(), ret.getExpires_in(), ret.getOpenid());
            Toast.makeText(mContext, mContext.getResources().getString(R.string.loginsucc), Toast.LENGTH_SHORT).show();
            HandlerInter.getInstance().sendEmptyMessage(HandlerType.GOTOMAIN);

        }

        private void pswStatus(EditText pPswEt, ImageView pPswStatusIv) {
            if (mPswIsShow) {
                pPswStatusIv.setImageResource(R.mipmap.psw_show);
                //密码可见
                pPswEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                pPswStatusIv.setImageResource(R.mipmap.psw_hiddle);
                //密码不可见
                pPswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mPswIsShow = !mPswIsShow;
        }

        private boolean checkAll() {
            userCount = telNumEt.getText().toString().trim();
            password = pswEt.getText().toString().trim();

            if (TextUtils.isEmpty(userCount) || userCount.length() < 6 || userCount.length() > 16) {
                ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getResources().getString(R.string.errorRemind), ToastUtils.LENGTH_SHORT);
                return false;
            }
            if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
                ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getResources().getString(R.string.errorRemind1), ToastUtils.LENGTH_SHORT);
                return false;
            }
            return true;
        }

        private void fillCount() {
            telNumEt.setText(SharePreUtils.getInstance().getUserName(mContext));
            pswEt.setText(SharePreUtils.getInstance().getPassword(mContext));
        }

        public void handleMsg(Message msg) {
            switch (msg.what) {
                case HandlerType.REGISTSUCC:
                    fillCount();
                    break;

            }
        }

    }

}
