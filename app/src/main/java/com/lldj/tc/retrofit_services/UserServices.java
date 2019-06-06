package com.lldj.tc.retrofit_services;

import com.lldj.tc.mine.info.InfoBean;
import com.lldj.tc.toolslibrary.retrofit.BaseEntity;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * description: <p>
 * user: wangclia<p>
 * Creat Time: 2019/1/15 10:05<p>
 * Modify Time: 2019/1/15 10:05<p>
 */


public interface UserServices {

    //获取验证码
    @FormUrlEncoded
    @POST("register/sms")
    Observable<BaseEntity<InfoBean>> getPhoneCode(@Path("mobile") String mobile);













    //姓名或者昵称
    @FormUrlEncoded
    @POST("appUser/saveAndUpdate")
    Observable<BaseEntity<InfoBean>> saveAndUpdateName(@Field("userName") String userName, @Field("nickName") String nickName);

    //姓名或者昵称
    @FormUrlEncoded
    @POST("appUser/saveAndUpdate")
    Observable<BaseEntity<InfoBean>> saveAndUpdatePhone(@Field("phone") String phone, @Field("code") String code);

    //科室
    @FormUrlEncoded
    @POST("appUser/saveAndUpdate")
    Observable<BaseEntity<InfoBean>> saveAndUpdatedepartmentName(@Field("departmentName") String departmentName);

    //职称
    @FormUrlEncoded
    @POST("appUser/saveAndUpdate")
    Observable<BaseEntity<InfoBean>> saveAndUpdateprofessionalRank(@Field("professionalRank") String professionalRank);

    @FormUrlEncoded
    @POST("appUser/saveAndUpdate")
    Observable<BaseEntity<InfoBean>> saveAndUpdate(@Field("userName") String userName, @Field("nickName") String nickName, @Field("phone") String phone, @Field("departmentName") String departmentName, @Field("professionalName") String professionalName, @Field("professionalRank") String professionalRank, @Field("skills") String skills);

    //实名认证
    @FormUrlEncoded
    @POST("appUser/realNamevalidate")
    Observable<BaseEntity<InfoBean>> realNamevalidate(@Field("realName") String realName,@Field("certificateType") String certificateType,@Field("certificateNo") String certificateNo);

    //职业认证
    @FormUrlEncoded
    @POST("appUser/professionalValidate ")
    Observable<BaseEntity<InfoBean>> professionalValidate (@Field("realName") String realName,@Field("professionalName") String professionalName,@Field("institution") String institution,@Field("licenseNo") String licenseNo);

    @GET("appUser/userInfo")
    Observable<BaseEntity<InfoBean>> userInfo ();

//    @GET("appUser/skillsList")
//    Observable<BaseEntity<ArrayList<SkillBean>>> skilList();
}
