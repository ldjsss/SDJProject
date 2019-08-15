package com.lldj.tc.info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.lldj.tc.R;
import com.lldj.tc.jsInterface.DecoObject;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Webview extends BaseActivity {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.toolbar_title_tv)
    StrokeTextView toolbarTitleTv;
    @BindView(R.id.imservice)
    ImageView imservice;
    @BindView(R.id.connectservice)
    RelativeLayout connectservice;
    @BindView(R.id.toolbar_root_layout)
    RelativeLayout toolbarRootLayout;
    @BindView(R.id.rulewebview)
    WebView webview;

    private String _url = "";
    private String title = "";
    private ArrayList<String> loadHistoryUrls = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webviewlayout);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);

        Intent intent = getIntent();
        _url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(title);

        if(AppUtils.isEmptyString(_url)) return;

        AppUtils.showLoading(this);

        webview.setBackgroundColor(getResources().getColor(R.color.color_bg));
        webview.loadDataWithBaseURL(null, "加载中。。", "text/html", "utf-8",null);
        webview.setVisibility(View.VISIBLE);
        webview.requestFocus();
        loadUrl(webview, _url);
        WebSettings settings = webview.getSettings();
        settings.setDefaultTextEncodingName("utf-8") ;
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = webview.getSettings().getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);//利用反射机制去修改设置对象
                if (method != null) {
                    method.invoke(webview.getSettings(), true);//修改设置
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        webview.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(request.getUrl().toString());
//                return true;
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webview.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                Log.d( "--------","url = "+ url);
                for (int i = 0; i < loadHistoryUrls.size(); i++) {
                    if(loadHistoryUrls.get(i).equalsIgnoreCase(url)) return;
                }
                if(url.indexOf("http") > -1)loadHistoryUrls.add(url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d( "--------","start load url = "+ url);
                view.getSettings().setBlockNetworkImage(true);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // super.onReceivedSslError(view, handler, error);
                //super句话一定要删除，或者注释掉，否则又走handler.cancel()默认的不支持https的了。
                handler.proceed();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                view.loadUrl("about:blank");// 避免出现默认的错误界面
//                view.loadUrl(mErrorUrl);// 加载自定义错误页面
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    AppUtils.hideLoading();
                    view.getSettings().setBlockNetworkImage(false);
                     Log.d("------","加载完成");

                } else {
                    Log.d("------","加载中..." + newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // android 6.0 以下通过title获取判断
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("404") || title.contains("500") || title.contains("Error") || title.contains("找不到网页") || title.contains("网页无法打开")) {
                        view.loadUrl("about:blank");// 避免出现默认的错误界面
//                        view.loadUrl(mErrorUrl);// 加载自定义错误页面
                    }
                }
            }

            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier,
                                                long quota, long estimatedDatabaseSize, long totalQuota,
                                                WebStorage.QuotaUpdater quotaUpdater) {
                // This default implementation passes the current quota back to WebCore.
                // WebCore will interpret this that new quota was declined.
//                quotaUpdater.updateQuota(quota);
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }
         });


        webview.addJavascriptInterface(new DecoObject(this),"decoObject");


    }

    private void loadUrl(WebView view, String url){
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.loadUrl(url);
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.releaseAllWebViewCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webview.getSettings().setJavaScriptEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
//        webview.onPause();
        webview.loadUrl("about:blank");
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

    }

    @OnClick(R.id.toolbar_back_iv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                if(loadHistoryUrls.size() >= 2) {
                    webview.loadUrl(loadHistoryUrls.get(loadHistoryUrls.size() - 2));
                    loadHistoryUrls.remove(loadHistoryUrls.size() - 1);
                    return;
                }

                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
        }
    }


}
