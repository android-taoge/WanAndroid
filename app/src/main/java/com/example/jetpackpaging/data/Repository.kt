package com.example.jetpackpaging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jetpackpaging.data.dao.ArticleDao
import com.example.jetpackpaging.model.ArticleEntity
import com.example.jetpackpaging.model.Banner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


const val ARTICLE_PAGING_START = 0
const val PAGING_SIZE = 20

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val articleDao: ArticleDao,
    private val apiService: ApiService,
    private val articleRemoteMediator: ArticleRemoteMediator
) {


    fun fetchArticle(): Flow<PagingData<ArticleEntity>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = articleRemoteMediator,
            pagingSourceFactory = { articleDao.queryArticle() }
        ).flow
    }


    suspend fun fetchBanner(): Flow<List<Banner>> {
        return flow {
            val bannerJson = apiService.fetchBanner()
            emit(bannerJson.data)
        }
    }
}