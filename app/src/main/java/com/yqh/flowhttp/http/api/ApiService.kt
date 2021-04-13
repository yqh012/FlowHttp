package com.yqh.flowhttp.http.api

import com.yqh.flowhttp.BannerInfo
import com.yqh.flowhttp.http.bean.HttpWrapBean
import retrofit2.http.GET

interface ApiService {
    @GET("banner/json")
    suspend fun getBannerList(): HttpWrapBean<List<BannerInfo>>

}