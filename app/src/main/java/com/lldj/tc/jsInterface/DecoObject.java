package com.lldj.tc.jsInterface;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class DecoObject {
    private Activity c;

    public  DecoObject(Activity activity){
        c = activity;
    }

    @JavascriptInterface
    public void savePic(String url){
        Toast.makeText(c, "-------android----savePic  = " + url, Toast.LENGTH_SHORT).show();
    }
}
