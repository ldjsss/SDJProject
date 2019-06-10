package com.lldj.tc;

import android.app.Application;
import android.content.Context;

/**
 * description: <p>
 * user: lenovo<p>
 */


public class SDJApplication extends Application {
    private static SDJApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = SDJApplication.this;
    }

    public static Context getContext() {
        return application;
    }
}
