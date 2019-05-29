package com.lldj.tc.toolslibrary.retrofit;


import android.util.Log;


import com.lldj.tc.toolslibrary.util.Clog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Creator: mrni-mac on 16-1-4.
 * Email  : nishengwen_android@163.com
 */
public enum OkHttpUtilsCookier {
    INSTANCE;
    String murl;
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    //    private final PersistentCookieStore cookieStore = new PersistentCookieStore(StarshowApplication.getContext());
    OkHttpUtilsCookier() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.socketFactory(HttpsFactroy.getSSLSocketFactory(StarshowApplication.getContext(),   certificates));
//        builder.hostnameVerifier(HttpsFactroy.getHostnameVerifier(hosts));
        //设定10秒超时
        builder.connectTimeout(RetrofitConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(RetrofitConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Clog.e("OkHttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));//网络和日志拦截

        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                Log.e("saveFromResponse的url", url.url().getHost() + "");
                cookieStore.put(url.url().getHost(), cookies);
                if (null != cookies) {
                    for (int i = 0; i < cookies.size(); i++) {
                        Log.e("saveFromResponse的cookie", " " + cookies.get(i));
                    }
                } else {
                    Log.e("saveFromResponse", "cookie是空的");
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                Clog.e("loadForRequest 获取url", url.url().getHost() + "   ====");
                List<Cookie> cookies = cookieStore.get(url.url().getHost());
                return cookies != null ? cookies : new ArrayList<Cookie>();

            }
        });
//        final String UA =  DeviceInfoUtil.getInstance().getUA(StarshowApplication.getContext());
//        Clog.e("UA","US"+UA);

//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request request = original.newBuilder()
//                        .header("Accept", "application/json")
//                        .header("Content-Type", "application/json")
//                        .header("User-Agent", UA)
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });
        okHttpClient = builder.build();
    }

    private OkHttpClient okHttpClient;

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    private String cookieHeader(List cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        for (int i = 0, size = cookies.size(); i < size; i++) {
            if (i > 0) {
                cookieHeader.append(";");
            }
            Cookie cookie = (Cookie) cookies.get(i);
            cookieHeader.append(cookie.name()).append("=").append(cookie.value());
        }
        return cookieHeader.toString();
    }
}
