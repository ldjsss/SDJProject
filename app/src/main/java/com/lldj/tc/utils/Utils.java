package com.lldj.tc.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.lldj.tc.toolslibrary.util.AppUtils;

public class Utils {

    public static void setFlickerAnimation(View view, int repeatCount, final Listener listener) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1.0f);
        Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(200); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(repeatCount);//(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); //
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) { }
            @Override public void onAnimationEnd(Animation animation) {
                view.setAlpha(0.0f);
                if(listener != null) listener.onFinish();
            }
             @Override
             public void onAnimationRepeat(Animation animation) { }
        });

        view.setAnimation(animation);

    }

    public static String getDevices(Activity activity){
        StringBuffer sbf = new StringBuffer();
        sbf.append(AppUtils.getDeviceName());
        sbf.append("/");
        sbf.append(AppUtils.getSystemVersion());
        sbf.append("/");
        sbf.append(AppUtils.getVersionName(activity));
        sbf.append("/");
        sbf.append(AppUtils.getResolution(activity));

        return sbf.toString();
    }


    public interface Listener{
        void onFinish();
    }
}
