package com.lldj.tc.mainUtil;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class Utils {

    public static void setFlickerAnimation(View view, int repeatCount) {
        final Animation animation = new AlphaAnimation(0, 1); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(repeatCount);//(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); //
        view.setAnimation(animation);
    }
}
