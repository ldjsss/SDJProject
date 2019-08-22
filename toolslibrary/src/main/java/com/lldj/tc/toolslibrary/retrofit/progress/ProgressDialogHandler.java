package com.lldj.tc.toolslibrary.retrofit.progress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.lldj.tc.toolslibrary.util.DialogUtils;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog mProgressDialog;
    private Activity mContext;
    private boolean mIsCancleable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Activity context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.mContext = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.mIsCancleable = cancelable;
    }

    private void initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtils.createLoadingDialog(mContext, null, mIsCancleable);
            if (mIsCancleable) {
                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
