package com.lldj.tc.login;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.handler.HandlerType;
import com.lldj.tc.httpMgr.HttpMsg;
import com.lldj.tc.register.ForgetFrament;
import com.lldj.tc.register.RegisterFrament;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
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

        private int screenHeight = 0;
        private String userCount = "";
        private String password = "";

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;

            if (frameaddlayout.getLayoutParams().height <= screenHeight)
                frameaddlayout.getLayoutParams().height = screenHeight;

        }

        @OnClick({R.id.forget_psw_tv, R.id.login_tv, R.id.register_tv, R.id.just_look_tv})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.forget_psw_tv:
                    if (lossFrament == null) { lossFrament = new ForgetFrament(); }
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.add(R.id.frameaddlayout, lossFrament);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                    frameaddlayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.login_tv:
                    if(!checkAll()) return;
                    HttpMsg.sendLogin(userCount, password, new HttpTool.msgListener(){
                        @Override
                        public void onFinish(int code, String msg) {
                            Log.w("-----code", code + "");
                            Log.w("-----msg", msg + "");
                            Toast.makeText(mContext,"---------------login back code = " + code + " /msg = " + msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                    ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, "denglu！", ToastUtils.LENGTH_SHORT);
                    break;
                case R.id.register_tv:
                    if (resFrament == null) { resFrament = new RegisterFrament(); }

                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.add(R.id.frameaddlayout, resFrament);
                    ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft1.commit();

                    frameaddlayout.setVisibility(View.VISIBLE);

                    break;
                case R.id.just_look_tv:
                    HandlerInter.getInstance().sendEmptyMessage(HandlerType.GOTOMAIN);
                    break;
            }
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
            }
        }

    }

}
