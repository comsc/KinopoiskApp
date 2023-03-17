package com.example.newsproject.presentation.first

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel : ViewModel() {

    private val remoteRepository = DataObject.remoteRepository
    private val localRepository = DataObject.localRepository

    private val _articles = MutableLiveData<List<Doc>>()
    val articles = _articles.asLiveData()

    val favoriteArticles = localRepository.getFavoiteGiphyFromDb()

    init {
        getMovieData()
    }

    private fun getMovieData() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            remoteRepository.getMovie()
        }
        _articles.value = response.docs
        handleAllArticles(favoriteArticles.value)
    }

    fun handleAllArticles(list: List<Doc>?) { // 2 favorite
        _articles.value?.map { it.copy(isFavorite = false) }?.toMutableList()
            ?.onEach { article ->
                list?.any { it.id == article.id }?.let { article.isFavorite = it }
            }?.let { _articles.value = it }
    }

    fun handleFavorites(article: Doc) {
        if (article.isFavorite) {
            deleteFavorite(article)
        } else {
            addFavorite(article)
        }
    }

    private fun deleteFavorite(article: Doc) = viewModelScope.launch {
        localRepository.removeArticle(article)
        val oldList = _articles.value?.toMutableList()
        _articles.value =
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
        localRepository.addArticle(article.copy(isFavorite = true))
    }
}