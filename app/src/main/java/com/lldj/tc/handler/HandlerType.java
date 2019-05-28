package com.lldj.tc.handler;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HandlerType {

    private HandlerType annotationSeason = null;

    public static final int LEFTMENU = 0;
    public static final int LEFTBACK = 1;
    public static final int SUMMER = 2;
    public static final int FALL = 3;

    public HandlerType(@Season int season) {
        System.out.println("Season :" + season);
    }

    @IntDef({LEFTMENU, LEFTBACK, SUMMER, FALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Season {
    }

    public static void main(int args) {
        HandlerType annotationSeason = new HandlerType(args);
    }
}