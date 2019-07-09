package com.lldj.tc.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.lldj.tc.toolslibrary.util.RxTimerUtil;

public class Utils {

    public static void setFlickerAnimation(View view, int repeatCount) {
        view.setVisibility(View.VISIBLE);
        final Animation animation = new AlphaAnimation(0, 1); // Change alpha from fully visible to invisible
        animation.setDuration(200); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(repeatCount);//(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); //
        view.setAnimation(animation);

        RxTimerUtil.timer(500*repeatCount, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                view.setVisibility(View.GONE);
            }
            @Override
            public void onComplete() { }
        });
    }
}
