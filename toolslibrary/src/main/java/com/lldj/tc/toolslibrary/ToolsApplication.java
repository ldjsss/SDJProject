package com.lldj.tc.toolslibrary;

import android.app.Application;
import android.content.Context;

import com.lldj.tc.toolslibrary.util.Clog;


/**
 * Description:
 * Author: wangclia
 * Time: 2019/1/22
 */

public class ToolsApplication extends Application {
    private static ToolsApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = ToolsApplication.this;
        Clog.i("ToolsApplication","ToolsApplication");
    }

    public static Context getContext() {
        return application;
    }
}
