package com.lldj.tc.toolslibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.util.ArrayList;

public class BaseDialog extends Dialog {
    private String tag = "";
    public boolean isShow = false;
    private ArrayList<Observer<ObData>> eventList = new ArrayList<>();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
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

        View view = getWindow().getDecorView();

        AppUtils.fullScreenImmersive(view);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            view.setSystemUiVisibility(View.GONE);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
        isShow = true;

        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();

        for (int i = 0; i < eventList.size(); i++) {
            AppUtils.unregisterEvent(eventList.get(i));
        }
        eventList.clear();
        isShow = false;
    }

    @Override
    public void hide() {
        super.hide();
        isShow = false;
    }

    protected void registEvent(Observer<ObData> observer){
        AppUtils.registEvent(observer);
        eventList.add(observer);
    }

    protected void unregisterEvent(Observer<ObData> observer){
        AppUtils.unregisterEvent(observer);
        for (int i = 0; i < eventList.size(); i++) {
            if (observer == eventList.get(i)){
                eventList.remove(i);
                break;
            }
        }
    }
}
