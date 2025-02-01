package com.seirar.wanandroid.domain.model.article

import retrofit2.http.GET
import retrofit2.http.Path


interface ArticleApiService {

    @GET("article/list/{page}/json")
    suspend fun getArticles(@Path("page") page :Int) : ApiResponse<ArticleData>

}