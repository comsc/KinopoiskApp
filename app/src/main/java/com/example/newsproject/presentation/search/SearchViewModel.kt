package com.example.newsproject.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel: ViewModel() {
    private val remoteRepository = DataObject.remoteRepository
    private val localRepository = DataObject.localRepository
    val favoriteMovies = localRepository.getFavoriteMoviesFromDb()

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
         handleAllArticles(favoriteMovies.value)
    }


    fun handleAllArticles(list: List<Doc>?) { // 2 favorite
        _movies.value?.map { it.copy(isFavorite = false) }?.toMutableList()
            ?.onEach { article ->
                list?.any { it.id == article.id }?.let { article.isFavorite = it }
            }?.let { _movies.value = it }
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
        val oldList = _movies.value?.toMutableList()
        _movies.value =
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