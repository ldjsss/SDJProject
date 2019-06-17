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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.httpMgr.beans.FormatModel.JsonBean;
import com.lldj.tc.httpMgr.beans.FormatModel.Results;
import com.lldj.tc.register.ForgetFrament;
import com.lldj.tc.register.RegisterFrament;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.util.AppURLCode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: login<p>
 */


public class LoginCellAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mlist = new ArrayList<>();
    private viewHolder mHolder = null;
    FragmentManager fragmentManager = null;

    public LoginCellAdapter(Context mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
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
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_login, parent, false));
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

        private RegisterFrament resFrament = null;
        private ForgetFrament lossFrament = null;

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
        //密码是否显示
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
                    if (lossFrament == null) {
                        lossFrament = new ForgetFrament();
                    }
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.add(R.id.frameaddlayout, lossFrament);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                    frameaddlayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.login_tv:
                    if (!checkAll()) return;
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
                    HttpMsg.sendLogin(userCount, password, new HttpMsg.Listener() {
                        @Override
                        public void onFinish(JsonBean res) {
                            if (res.getCode() == AppURLCode.succ) {
                                saveLoginData(res);
                            }
                        }
                    });
                    break;
                case R.id.register_tv:
                    if (resFrament == null) {
                        resFrament = new RegisterFrament();
                    }

                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.add(R.id.frameaddlayout, resFrament);
                    ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft1.commit();

                    frameaddlayout.setVisibility(View.VISIBLE);

                    break;
                case R.id.just_look_tv:
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.GOTOMAIN);
                    break;
                case R.id.psw_show_or_hid_iv:
                    pswStatus(pswEt, pswShowOrHidIv);
            }
        }

        private void tokenLogin() {
            String token = SharePreUtils.getToken(mContext);
            if(TextUtils.isEmpty(token)) return;
            HandlerInter.getInstance().sendEmptyMessage(HandlerType.LOADING);
            HttpMsg.sendTokenLogin(token, new HttpMsg.Listener() {
                @Override
                public void onFinish(JsonBean res) {
                    if (res.getCode() == AppURLCode.succ) {
                        saveLoginData(res);
                    }
                }
            });
        }

        private void saveLoginData(JsonBean res) {
            Results ret = (Results)res.getResult();
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
                case HandlerType.REMOVERES:
                    if (resFrament != null)
                        fragmentManager.beginTransaction().remove(resFrament).commit();
                    resFrament = null;

                    if (lossFrament != null)
                        fragmentManager.beginTransaction().remove(lossFrament).commit();
                    lossFrament = null;

                    frameaddlayout.setVisibility(View.GONE);
                    break;
                case HandlerType.REGISTSUCC:
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.REMOVERES);
                    fillCount();
                    break;
            }
        }

    }

}
