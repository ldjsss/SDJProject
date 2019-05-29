package com.lldj.tc.retrofit_services;

import com.lldj.tc.bean.TestBean;
import com.lldj.tc.toolslibrary.retrofit.BaseEntity;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KnowledgeListServices {
    //求知首页项目列表（本版不用，修改为分开请求）
//    @GET("project/get_project_list")
    Observable<BaseEntity<TestBean>> getProjectList();
    //推荐项目列表
    @GET("project/get_recommend_project_list")
    Observable<BaseEntity<TestBean>> getRecommendProjectList(@Query("page_number") int page_number,
                                                             @Query("page_size") int page_size);
    //远程项目列表
    @GET("project/get_remote_project_list")
    Observable<BaseEntity<TestBean>> getRemoteProjectList(@Query("page_number") int page_number,
                                                          @Query("page_size") int page_size);
    //线下项目列表
    @GET("project/get_offline_project_list")
    Observable<BaseEntity<TestBean>> getOfflineProjectList();

    @GET("project/get_project_list")
    Observable<BaseEntity<ArrayList<TestBean>>> get_project_list();
}
