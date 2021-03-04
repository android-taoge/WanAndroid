package com.example.jetpackpaging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpackpaging.model.ArticleEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticlePagingSource @Inject constructor(
    private val apiService: ApiService
) :
    PagingSource<Int, ArticleEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        return ARTICLE_PAGING_START
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {

        val position = params.key ?: ARTICLE_PAGING_START

        return try {

            val response = apiService.fetchArticleByPage(position)
            val articles = response.data.datas
            val nextKey = if (articles.isEmpty()) null else position + 1

            LoadResult.Page(
                data = articles,
                prevKey = if (position == ARTICLE_PAGING_START) null else position - 1,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }
}