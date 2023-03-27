package com.example.newsproject.presentation.serials

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.utils.Resource
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SerialsViewModel:ViewModel() {
    private val remoteRepository = DataObject.remoteRepository
    private val localRepository = DataObject.localRepository
    private var _movies = MutableLiveData<Resource<List<Doc>>>()
    val movie = _movies.asLiveData()

    val favoriteArticles = localRepository.getFavoriteMoviesFromDb()

    init {
        getSerialsData(favoriteArticles.value)
    }

    fun getSerialsData(list: List<Doc>?) = viewModelScope.launch {
        _movies.postValue(Resource.Loading())
        val response = withContext(Dispatchers.IO) {
            remoteRepository.getSerials()
        }

        if (response.isSuccessful){
            response.body()?.docs?.map { it.copy(isFavorite = false) }?.toMutableList()
                ?.onEach { article ->
                    list?.any { it.id == article.id }?.let { article.isFavorite = it }
                }?.let { _movies.postValue(Resource.Success(it))  }
        }else {
            _movies.postValue(Resource.Error(message = response.message()))
        }

    }

    fun handleFavorites(article: Doc) {
        if (article.isFavorite) {
            deleteFavorite(article)
        } else {
            addFavorite(article)
        }
    }

    private fun deleteFavorite(article: Doc) = viewModelScope.launch {
        localRepository.removeMovieFromDb(article)
        val oldList = _movies.value?.data?.toMutableList()
        _movies.value?.data =
            oldList?.map {
                it.copy(
                    isFavorite = if (article.id == it.id) {
                        false
                    } else {
                        it.isFavorite
                    }
                )
            }
    }

    private fun addFavorite(article: Doc) = viewModelScope.launch {
        localRepository.addMovieToDb(article.copy(isFavorite = true))
    }
}