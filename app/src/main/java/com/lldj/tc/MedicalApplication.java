package com.lldj.tc;

import android.app.Application;
import android.content.Context;

/**
 * description: <p>
 * user: lenovo<p>
 */


public class MedicalApplication extends Application {
    private static MedicalApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = MedicalApplication.this;
    }

    public static Context getContext() {
        return application;
    }
}
