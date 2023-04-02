package com.example.newsproject.presentation.first

import androidx.lifecycle.*
import androidx.paging.*
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.presentation.first.paging.MoviePagingSource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    private val remoteRepository = DataObject.remoteRepository
    private val _movie:MutableLiveData<PagingData<Doc>> = MutableLiveData()
    val movie:LiveData<PagingData<Doc>> = _movie


    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { MoviePagingSource(remoteRepository, type = "movie") }
            ).flow.cachedIn(viewModelScope).onEach {
                _movie.value = it
            }.launchIn(viewModelScope)
        }
    }
//    fun getMovie() = Pager(
//        config = PagingConfig(
//            pageSize = 10,
//            enablePlaceholders = false
//        ),
//        pagingSourceFactory = { MoviePagingSource(remoteRepository, type = "movie") }
//    ).flow.cachedIn(viewModelScope)


}

