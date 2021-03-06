package com.example.jetpackpaging.data

import com.example.jetpackpaging.model.Banner
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {


    /**
     * get home article data
     */
    @GET("article/list/{page}/json")
    suspend fun fetchArticleByPage(@Path("page") page: Int): Response.Success<ArticlePage>


    /**
     * fetch banner data
     */
    @GET("banner/json")
    suspend fun fetchBanner(): Response.Success<List<Banner>>

}