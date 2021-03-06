package com.example.jetpackpaging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpackpaging.model.ProjectEntity
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

const val PROJECT_LIST_START_PAGE = 1
const val PROJECT_PAGING_SIZE = 15


class ProjectPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val categoryId: Int
) :
    PagingSource<Int, ProjectEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ProjectEntity>): Int? {
        return PROJECT_LIST_START_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectEntity> {
        val pageKey = params.key ?: PROJECT_LIST_START_PAGE
        return try {
            val response = apiService.fetchProjectListByCid(pageKey, categoryId)
            val projects = response.data.datas
            val nextKey = if (projects.isEmpty()) null else pageKey + 1

            LoadResult.Page(
                data = projects,
                prevKey = if (pageKey == PROJECT_LIST_START_PAGE) null else pageKey - 1,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}


