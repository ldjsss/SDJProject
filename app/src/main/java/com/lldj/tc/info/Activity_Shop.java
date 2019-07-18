package com.lldj.tc.info;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.JsonBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.RxTimerUtilPro;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.StrokeTextView;
import com.lldj.tc.utils.GlobalVariable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class Activity_Shop extends BaseActivity {
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
    @BindView(R.id.getmoney)
    TextView getmoney;
    @BindView(R.id.shopwebview)
    WebView shopwebview;
    @BindView(R.id.tvservices)
    TextView tvservices;
    @BindView(R.id.tvmoney)
    TextView tvmoney;

    private Disposable disposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int Anim_fade = intent.getIntExtra("Anim_fade", 0);

        setContentView(R.layout.shoplayout);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = Anim_fade;
        getWindow().setAttributes(params);


        ImmersionBar.with(this).titleBar(toolbarRootLayout).init();
        toolbarTitleTv.setText(getResources().getString(R.string.wallet));

        shopwebview.setBackgroundColor(getResources().getColor(R.color.color_bg));
        shopwebview.setVisibility(View.VISIBLE);
        shopwebview.loadUrl(SharePreUtils.getRecharge_url(mContext));
        WebSettings settings = shopwebview.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        shopwebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        shopwebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        imservice.setImageResource(R.mipmap.main_search);
        tvservices.setVisibility(View.GONE);
        connectservice.setVisibility(View.VISIBLE);

        tvmoney.setText(SharePreUtils.getMoney(this));

        disposable = RxTimerUtilPro.interval(3000, new RxTimerUtilPro.IRxNext() {
            @Override
            public void doNext(long number) {
                getInfo();
            }

            @Override
            public void onComplete() {
            }
        });

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtilPro.cancel(disposable);
        disposable = null;
    }

    @OnClick({R.id.toolbar_back_iv, R.id.connectservice, R.id.getmoney})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_iv:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            case R.id.connectservice:
                Toast.makeText(this, "---------------Not yet implemented ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.getmoney:
                startActivity(new Intent(this, Activity_Getmoney.class));
                overridePendingTransition(0, R.anim.out_to_left);
                finish();
                break;
        }
    }

    private void getInfo() {
        HttpMsg.getInstance().sendGetUserInfo(mContext, SharePreUtils.getInstance().getToken(mContext), JsonBean.class, new HttpMsg.Listener() {
            @Override
            public void onFinish(Object _res) {
                JsonBean res = (JsonBean) _res;
                if (res.getCode() == GlobalVariable.succ) {
                    tvmoney.setText(SharePreUtils.getMoney(mContext));
                }
            }
        });
    }

}
