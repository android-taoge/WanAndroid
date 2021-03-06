package com.example.jetpackpaging.viewmodel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetpackpaging.data.Repository
import com.example.jetpackpaging.model.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private var currentResult: Flow<PagingData<ArticleEntity>>? = null

    private var _article = MutableLiveData<PagingData<ArticleEntity>>()

    init {
        fetchArticle()
    }

    val article get() = _article

    fun refreshData() {
        fetchArticle()
    }


    private fun fetchArticle() {

        viewModelScope.launch {
            repository.fetchArticle().cachedIn(viewModelScope).collectLatest {
                _article.value = it

            }
        }


    }


}