package com.lldj.tc.handler;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HandlerType {

    private HandlerType annotationSeason = null;

    public static final int LEFTMENU = 1;
    public static final int LEFTBACK = 2;
    public static final int REMOVERES = 3;
    public static final int GOTOMAIN = 4;
    public static final int SHOWTOAST = 5;

    public HandlerType(@Season int season) {
        System.out.println("Season :" + season);
    }

    @IntDef({LEFTMENU, LEFTBACK, REMOVERES, GOTOMAIN, SHOWTOAST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Season {
    }

    public static void main(int args) {
        HandlerType annotationSeason = new HandlerType(args);
    }
}