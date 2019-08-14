package com.lldj.tc.jsInterface;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lldj.tc.info.Activity_Getmoney;
import com.lldj.tc.info.Activity_tradings;
import com.lldj.tc.toolslibrary.util.DonwloadSaveImg;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.wintone.smartvision_bankCard.ScanCamera;

import java.util.List;

public class DecoObject {
    private Activity c;

    public  DecoObject(Activity activity){
        c = activity;
    }

    @JavascriptInterface
    public void savePic(String url){
        if (Build.VERSION.SDK_INT >= 23) {
            Acp.getInstance(c).request(new AcpOptions.Builder().setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).build(), new AcpListener() {
                @Override
                public void onGranted() {
                    doNext(url);
                }

                @Override
                public void onDenied(List<String> permissions) {
                }
            });
        }
        else{
            doNext(url);
        }
    }

    private void doNext(String url){
//        Toast.makeText(c, "-------android----savePic  = " + url, Toast.LENGTH_SHORT).show();
        DonwloadSaveImg.donwloadImg(c, url);
    }


    @JavascriptInterface
    public void tradeHistoryLog(){
        c.startActivity(new Intent(c, Activity_tradings.class));
    }

    @JavascriptInterface
    public void withdrawMoney(){
        c.startActivity(new Intent(c, Activity_Getmoney.class));
    }
}
