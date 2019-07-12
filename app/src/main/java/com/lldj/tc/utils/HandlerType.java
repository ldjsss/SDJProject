package com.lldj.tc.utils;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HandlerType {

    private HandlerType annotationSeason = null;

    public static final int LEFTMENU = 1;
    public static final int LEFTBACK = 2;
    public static final int GOTOMAIN = 4;
    public static final int SHOWTOAST = 5;
    public static final int REGISTSUCC = 6;
    public static final int LOADING = 7;
    public static final int SHOWBETDIA = 8;
    public static final int JUSTLOOK = 11;
    public static final int MATCHLIST = 12;
    public static final int GAMESELECT = 13;
    public static final int LEAVEGAME = 15;

    public HandlerType(@Season int season) {
        System.out.println("Season :" + season);
    }

    @IntDef({LEFTMENU, LEFTBACK, GOTOMAIN, SHOWTOAST, REGISTSUCC, LOADING, SHOWBETDIA, JUSTLOOK, MATCHLIST, GAMESELECT, LEAVEGAME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Season {
    }

    public static void main(int args) {
        HandlerType annotationSeason = new HandlerType(args);
    }
}