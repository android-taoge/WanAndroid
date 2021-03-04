package com.example.jetpackpaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetpackpaging.data.Repository
import com.example.jetpackpaging.model.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private var currentArticleResult: Flow<PagingData<ArticleEntity>>? = null

    fun fetchArticle(): Flow<PagingData<ArticleEntity>> {
        val lastArticleResult = currentArticleResult
        if (lastArticleResult != null) {
            return lastArticleResult
        }

        val newFetchResult = repository.fetchArticle().cachedIn(viewModelScope)
        currentArticleResult = newFetchResult
        return newFetchResult
    }


}