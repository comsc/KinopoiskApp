package com.example.newsproject.presentation.serials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.presentation.first.paging.MoviePagingSource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SerialsViewModel:ViewModel() {
    private val remoteRepository = DataObject.remoteRepository

    private val _movie: MutableLiveData<PagingData<Doc>> = MutableLiveData()
    val movie: LiveData<PagingData<Doc>> = _movie


    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { MoviePagingSource(remoteRepository, type = "tv-series") }
            ).flow.cachedIn(viewModelScope).onEach {
                _movie.value = it
            }.launchIn(viewModelScope)
        }
    }


}

