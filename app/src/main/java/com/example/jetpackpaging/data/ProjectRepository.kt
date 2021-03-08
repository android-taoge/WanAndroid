package com.example.jetpackpaging.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jetpackpaging.model.ProjectCate
import com.example.jetpackpaging.model.ProjectEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val apiService: ApiService
) {

    @Inject
    lateinit var projectSourceFactory: ProjectSourceFactory


    suspend fun fetchProjectCate(): Flow<List<ProjectCate>> = flow {
        emit(apiService.fetchProjectCate().data)
    }

    fun fetchProjectList(cateId: Int): Flow<PagingData<ProjectEntity>> =


        Pager(
            config = PagingConfig(
                pageSize = PROJECT_PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { projectSourceFactory.create(cateId) }
        ).flow
}