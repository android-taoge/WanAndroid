package com.example.jetpackpaging.data

import com.example.jetpackpaging.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


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


    /**
     * get project category
     */
    @GET("project/tree/json")
    suspend fun fetchProjectCate(): Response.Success<List<ProjectCate>>


    /**
     * get someone category project list by categoryId
     */

    @GET("project/list/{page}/json")
    suspend fun fetchProjectListByCid(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Response.Success<ProjectPage>

}