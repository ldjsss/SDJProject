package com.lldj.tc.toolslibrary.retrofit;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Description: 从asset下读取数据
 * Author: wang
 * Time: 2017/7/10 11:08
 */
public enum RetrofitUtils {
    INSTANCE;

    private Retrofit retrofitsingleton;

    RetrofitUtils() {
        Retrofit.Builder builder = new Retrofit.Builder();
        //配置服务器路径(baseUrl和注解中url连接的”/”最好写在baseUrl的后面，而不是注解中url的前面，否则可能会出现不可预知的错误。)
        builder.baseUrl(RetrofitConfig.TEST_HOST_URL_1024);
        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtils.INSTANCE.getClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        // 添加Retrofit到RxJava的转换器(如果返回为Call那么可以不添加这个配置。如果使用Observable那就必须添加这个配置)
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitsingleton = builder.build();

    }

    public <T> T getClient(Class<T> clazz) {
        return retrofitsingleton.create(clazz);
    }

    //基于特定的url生成的 RestAdapter
    public <T> T getSpecialClient(String url, Class<T> clazz) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);//配置服务器路径
        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtilsCookier.INSTANCE.getClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit specialSingleton = builder.build();
        return specialSingleton.create(clazz);
    }
    //基于特定的url生成的 RestAdapter
    public <T> T getSpecialClientCookier(String url, Class<T> clazz) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);//配置服务器路径
        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtilsCookier.INSTANCE.getClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit specialSingleton = builder.build();
        return specialSingleton.create(clazz);
    }

    //基于特定的url生成的 RestAdapter
    public <T> T getSpecialClient(String url, Class<T> clazz, Converter.Factory converterFactory, CallAdapter.Factory callFactory) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);//配置服务器路径
        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtils.INSTANCE.getClient());
        builder.addConverterFactory(converterFactory);
        builder.addCallAdapterFactory(callFactory);
        Retrofit specialSingleton = builder.build();
        return specialSingleton.create(clazz);
    }


//    public <T> T getClientCache(Class<T> clazz) {
//        Retrofit.Builder builder = new Retrofit.Builder();
//        builder.baseUrl(RetrofitConfig.TEST_HOST_URL);//配置服务器路径
//        //设置OKHttpClient为网络客户端
//        builder.client(OkHttpUtilsCache.INSTANCE.getClient());
//        builder.addConverterFactory(GsonConverterFactory.create());
//        Retrofit specialSingleton = builder.build();
//        return specialSingleton.create(clazz);
//    }

}

