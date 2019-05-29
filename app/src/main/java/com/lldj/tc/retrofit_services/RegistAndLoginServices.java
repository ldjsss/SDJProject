package com.lldj.tc.retrofit_services;

import com.lldj.tc.mine.info.InfoBean;
import com.lldj.tc.toolslibrary.retrofit.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * description: <p>
 * user: wangclia<p>
 * Creat Time: 2019/1/14 10:05<p>
 * Modify Time: 2019/1/14 10:05<p>
 */


public interface RegistAndLoginServices {

    @FormUrlEncoded
    @POST("registAndLogin/appUserRegist")
    Observable<BaseEntity<InfoBean>> appUserRegist(@Field("phone") String phone, @Field("password") String password, @Field("code") String code);

    @FormUrlEncoded
    @POST("registAndLogin/findPassword")
    Observable<BaseEntity<InfoBean>> findPassword(@Field("phone") String phone, @Field("password") String password, @Field("code") String code);

    @FormUrlEncoded
    @POST("registAndLogin/appLoginByPassword")
    Observable<BaseEntity<InfoBean>> appLoginByPassword(@Field("phone") String phone,@Field("password") String password);



}
