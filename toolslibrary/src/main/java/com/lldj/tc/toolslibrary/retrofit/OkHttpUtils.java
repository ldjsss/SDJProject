package com.lldj.tc.toolslibrary.retrofit;

import com.lldj.tc.toolslibrary.Sharepre.SharedPreferencesUtil;
import com.lldj.tc.toolslibrary.ToolsApplication;
import com.lldj.tc.toolslibrary.util.Clog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public enum OkHttpUtils {
    INSTANCE;

    OkHttpUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定10秒超时
        builder.connectTimeout(RetrofitConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(RetrofitConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                //post参数
                Request.Builder requestBuilder = original.newBuilder();
                //add header
                requestBuilder.header("VersionNo", "1.0.0");
                requestBuilder.header("SourceType","Android");
                String token = SharedPreferencesUtil.getStringValue(ToolsApplication.getContext(),"token");
                requestBuilder.header("Authorization",token);

//                if (original.body() instanceof FormBody) {
//                    FormBody.Builder newFormBody = new FormBody.Builder();
//                    FormBody oidFormBody = (FormBody) original.body();
//                    for (int i = 0; i < oidFormBody.size(); i++) {
//                        newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
//                    }
//                    newFormBody.add("ver", "1.0");
//                    newFormBody.add("client", "1");
//                    requestBuilder.method(original.method(), newFormBody.build());
//
//                } else {
//                    HttpUrl originalHttpUrl = original.url();
//                    HttpUrl url = originalHttpUrl.newBuilder()
//                            .addQueryParameter("ver", "1.0")
//                            .addQueryParameter("client", "1")
//                            .build();
//                    requestBuilder.url(url);
//                    requestBuilder.method(original.method(), original.body());
//                }


                Request request = requestBuilder.build();

//
                return chain.proceed(request);
            }
        });

        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Clog.e("OkHttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));//网络和日志拦截

        okHttpClient = builder.build();
    }

    private OkHttpClient okHttpClient;

    public OkHttpClient getClient() {
        return okHttpClient;
    }
}
