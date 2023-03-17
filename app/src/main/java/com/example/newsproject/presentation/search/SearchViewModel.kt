package com.example.newsproject.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel: ViewModel() {
    private val remoteRepository = DataObject.remoteRepository
    private val localRepository = DataObject.localRepository

    private val _movies = MutableLiveData<List<Doc>>()
    val movies = _movies.asLiveData()
//    init {
//        searchMovie("человек паук")
//    }

     fun searchMovie(name:String) = viewModelScope.launch{
        val response = withContext(Dispatchers.IO) {
            remoteRepository.searchMovie(name)
        }
        _movies.value = response.docs
    }

}