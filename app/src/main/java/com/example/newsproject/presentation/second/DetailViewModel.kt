package com.example.newsproject.presentation.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.models.movie.Movie
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel: ViewModel() {

    private val localRepository = DataObject.localRepository
    private val remoteRepository = DataObject.remoteRepository
    private val _detailArticle = MutableLiveData<Doc>()
    val detailArticle = _detailArticle.asLiveData()

    private val _movies = MutableLiveData<Movie>()
    val movies = _movies.asLiveData()

    fun movieId(movieId:String) = viewModelScope.launch{
        val response = withContext(Dispatchers.IO) {
            remoteRepository.movieId(movieId)
        }
        _movies.value = response
    }


    fun setDetail(movie: Doc) {
        _detailArticle.value = movie
    }

    fun handleFavorite() {
        val article = _detailArticle.value ?: return
        if (article.isFavorite) {
            deleteFavorite(article)
        } else {
            addToFavorite(article)
        }
    }
    private fun addToFavorite(article: Doc) {
        val actualArticle = article.copy(isFavorite = true)
        setDetail(actualArticle)
        viewModelScope.launch {
            localRepository.addArticle(actualArticle)
        }
    }

    private fun deleteFavorite(article: Doc) {
        val actualArticle = article.copy(isFavorite = false)
        setDetail(actualArticle)
        viewModelScope.launch {
            localRepository.removeArticle(article.copy(isFavorite = true))
        }
    }
}