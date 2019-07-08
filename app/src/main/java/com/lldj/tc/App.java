package com.lldj.tc;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.lldj.tc.toolslibrary.util.AppUtils;

public class App extends Application {
    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = App.this;
//        AppUtils.screenAdapterStart(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        AppUtils.screenAdapterRest(this);
    }

    public static Context getContext() {
        return application;
    }
}
