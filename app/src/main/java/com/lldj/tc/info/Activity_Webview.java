package com.lldj.tc.info;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;

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
    WebView rulewebview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webviewlayout);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.Anim_fade;
        getWindow().setAttributes(params);


    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.rules));

        rulewebview.setBackgroundColor(getResources().getColor(R.color.color_bg));
        rulewebview.loadDataWithBaseURL(null, "加载中。。", "text/html", "utf-8",null);
        rulewebview.setVisibility(View.VISIBLE);
        rulewebview.loadUrl("http://192.168.1.53:8080/rule.html");
        WebSettings settings = rulewebview.getSettings();
        settings.setDefaultTextEncodingName("utf-8") ;
        settings.setJavaScriptEnabled(true);
        rulewebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        rulewebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

    }

    @OnClick(R.id.toolbar_back_iv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
        }
    }
}
