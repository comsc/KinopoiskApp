package com.example.newsproject.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.repository.RemoteRepository


class MovieSourceFactory {
    private val remoteRepository = DataObject.remoteRepository
    fun getMovie()= Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(remoteRepository) }
    ).liveData
}