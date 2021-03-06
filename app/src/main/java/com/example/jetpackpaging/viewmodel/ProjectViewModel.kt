package com.example.jetpackpaging.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.jetpackpaging.data.Repository
import com.example.jetpackpaging.model.ProjectCate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ProjectViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _projectCate = MutableLiveData<List<ProjectCate>>()

    val projectCate get() = _projectCate

    init {

        fetchProjectCate()
    }

    private fun fetchProjectCate() {

        viewModelScope.launch {
            repository.fetchProjectCate().collectLatest {
                _projectCate.value = it
            }
        }

    }


}