package com.example.newsproject.presentation.cartoons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsproject.data.DataObject
import com.example.newsproject.presentation.first.paging.MoviePagingSource

class CartoonsViewModel:ViewModel() {
    private val remoteRepository = DataObject.remoteRepository
    fun getMovie() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(remoteRepository, type = "cartoon") }
    ).liveData.cachedIn(viewModelScope)
}

