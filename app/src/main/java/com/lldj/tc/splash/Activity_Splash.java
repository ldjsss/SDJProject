package com.lldj.tc.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.lldj.tc.R;
import com.lldj.tc.login.Activity_Login;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.RxTimerUtil;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

public class Activity_Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();

        RxTimerUtil.interval(2000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                RxTimerUtil.cancel();
//                if (Build.VERSION.SDK_INT >= 23) {
//                    Acp.getInstance(Activity_Splash.this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).build(), new AcpListener() {
//                        @Override
//                        public void onGranted() {
//                            goGame();
//                        }
//
//                        @Override
//                        public void onDenied(List<String> permissions) {
//                            goGame();
//                        }
//                    });
//                }
//                else {
                    goGame();
//                }

            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void goGame(){
        Intent mIntent = new Intent(Activity_Splash.this, Activity_Login.class);
        startActivity(mIntent);
        finish();
    }
}
