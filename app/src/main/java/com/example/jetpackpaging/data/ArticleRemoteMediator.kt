package com.example.jetpackpaging.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jetpackpaging.model.ArticleEntity
import com.example.jetpackpaging.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator constructor(
    private val apiService: ApiService,
    private val dataBase: AppDataBase
) : RemoteMediator<Int, ArticleEntity>() {

    private val TAG = ArticleRemoteMediator::class.java.simpleName

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getAnchorPositionRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: ARTICLE_PAGING_START
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstItemRemoteKey(state)
                    ?: return MediatorResult.Success(endOfPaginationReached = false)
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getLastItemRemoteKeys(state)
                if (remoteKeys?.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                remoteKeys.nextKey
            }
        }



         try {


            val response = apiService.fetchArticleByPage(page)
            val articles = response.data.datas
            val endOfReached = articles.isEmpty()
            Log.e(TAG, "page==$page")
            Log.e(TAG, "endOfReached==$endOfReached")


            dataBase.withTransaction {

                //clear dao
                if (loadType == LoadType.REFRESH) {
                    dataBase.articleDao().clear()
                    dataBase.remoteKeysDao().clearRemoteKeys()
                }

                val prevKey = if (page == ARTICLE_PAGING_START) null else page - 1
                val nextKey = if (endOfReached) null else page + 1

                val remoteKeys = articles.map { article ->
                    RemoteKeys(articleId = article.id, prevKey = prevKey, nextKey = nextKey)
                }

                dataBase.remoteKeysDao().insertAll(remoteKeys)
                dataBase.articleDao().insertAll(articles)
            }

             return MediatorResult.Success(endOfPaginationReached = endOfReached)

        } catch (e: IOException) {
             return  MediatorResult.Error(e)
        } catch (e: HttpException) {
             return  MediatorResult.Error(e)
        }

    }

    private suspend fun getAnchorPositionRemoteKey(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { article ->
                dataBase.remoteKeysDao().queryRemoteKey(article.id)
            }
        }
    }

    private suspend fun getFirstItemRemoteKey(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { article ->
            dataBase.remoteKeysDao().queryRemoteKey(article.id)
        }
    }

    private suspend fun getLastItemRemoteKeys(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { article ->

            dataBase.remoteKeysDao().queryRemoteKey(article.id)
        }

    }
}