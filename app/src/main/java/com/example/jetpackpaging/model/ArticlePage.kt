package com.example.jetpackpaging.model


data class ArticlePage(
    val curPage: Int = 0,
    val datas: List<ArticleEntity> = listOf(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
)