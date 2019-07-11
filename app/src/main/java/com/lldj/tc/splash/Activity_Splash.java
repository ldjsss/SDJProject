package com.lldj.tc.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lldj.tc.R;
import com.lldj.tc.Activity_Login;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;

public class Activity_Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
        RxTimerUtil.interval(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
//                Intent mIntent = new Intent(Activity_Splash.this,GuideActivity.class);
//                startActivity(mIntent);
//                Intent mIntent = new Intent(Activity_Splash.this, LoginActivity.class);
//                startActivity(mIntent);

                Intent mIntent = new Intent(Activity_Splash.this, Activity_Login.class);
                startActivity(mIntent);
                RxTimerUtil.cancel();
                finish();
            }

            @Override
            public void onComplete() {
//                Intent mIntent = new Intent(MainActivity.this,GuideActivity.class);
//                startActivity(mIntent);
            }
        });
    }
}
