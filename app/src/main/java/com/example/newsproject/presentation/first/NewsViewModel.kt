package com.example.newsproject.presentation.first

import androidx.lifecycle.*
import androidx.paging.*
import com.example.newsproject.data.DataObject
import com.example.newsproject.presentation.first.paging.MoviePagingSource

class NewsViewModel: ViewModel() {

    private val remoteRepository = DataObject.remoteRepository

    fun getMovie() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(remoteRepository, type = "movie") }
    ).liveData.cachedIn(viewModelScope)


}

