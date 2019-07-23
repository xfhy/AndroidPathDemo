package com.xfhy.retrofitdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by xfhy on 2019/7/23 23:02
 * Description :
 */
interface IArticleApi {

    //https://wanandroid.com/wxarticle/list/408/1/json
    //GET方式
    @GET("wxarticle/list/{id}/{page}/json")
    fun getAirticles(@Path("id") id: String, @Path("page") page: String): Call<BaseData>

}