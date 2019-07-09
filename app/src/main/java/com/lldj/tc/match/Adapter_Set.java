package com.lldj.tc.match;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.login.Adapter_Register;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.recycleDialog.BaseRecycleDialog;
import com.lldj.tc.toolslibrary.recycleDialog.RecycleCell;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;
import com.lldj.tc.utils.HandlerType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Adapter_Set extends RecycleCell {
    public static BaseRecycleDialog setting;

    private Context mContext;
    private ArrayList<String> mlist = new ArrayList<>();
    private Adapter_Set.viewHolder mHolder = null;

    public Adapter_Set(Context mContext) {
        this.mContext = mContext;
    }

    public void changeData(ArrayList<String> plist) {
        mlist = plist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_Set.viewHolder(LayoutInflater.from(mContext).inflate(R.layout.dialog_set, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mHolder = (Adapter_Set.viewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return null != mlist ? mlist.size() : 0;
    }

    public static void dismiss() {
        if(setting != null) setting.dismiss();
        setting = null;
    }

    public static void showView(Context context){
        if(setting != null) return;
        setting = new BaseRecycleDialog(context, R.style.DialogTheme, null);
        setting.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismiss();
            }
        });
        setting.setbBckground(context.getResources().getColor(R.color.color_bg));
        Window window = setting.getWindow();
        window.setGravity(Gravity.LEFT);
        window.setWindowAnimations(R.style.Anim_left);
        setting.showView(new Adapter_Set(context));
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
