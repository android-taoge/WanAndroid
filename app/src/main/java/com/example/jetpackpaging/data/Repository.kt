package com.example.jetpackpaging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jetpackpaging.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


const val ARTICLE_PAGING_START = 0
const val PAGING_SIZE = 20

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dataBase: AppDataBase
) {


    fun fetchArticle(): Flow<PagingData<ArticleEntity>> {
        val sourceFactory = { dataBase.articleDao().queryArticle() }

        return Pager(
            config = PagingConfig(pageSize = PAGING_SIZE, enablePlaceholders = false,prefetchDistance = 2),
            remoteMediator = ArticleRemoteMediator(apiService, dataBase),
            pagingSourceFactory =  sourceFactory
        ).flow
    }

}