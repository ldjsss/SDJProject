package com.lldj.tc.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;

public class Utils {

    public static void setFlickerAnimation(View view, int repeatCount) {
        view.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(200); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(repeatCount);//(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); //
        view.setAnimation(animation);

        RxTimerUtilPro.timer(200*repeatCount + 50, new RxTimerUtilPro.IRxNext() {
            @Override
            public void doNext(long number) {
                view.setAlpha(0.0f);
            }
            @Override
            public void onComplete() { }
        });

    }
}
