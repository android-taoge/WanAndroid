package com.example.jetpackpaging.data

import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {


    @GET("article/list/{page}/json")
    suspend fun fetchArticleByPage(@Path("page") page: Int): Response.Success<ArticlePage>

}