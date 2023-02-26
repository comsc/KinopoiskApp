package com.example.newsproject.presentation.first

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Article
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel : ViewModel() {

    private val remoteRepository = DataObject.remoteRepository
    private val localRepository = DataObject.localRepository

    private val _articles = MutableLiveData<List<Article>?>()
    val articles = _articles.asLiveData()

    val favoriteArticles = localRepository.getAllFavoriteArticles()

    init {
        getNewsData()
    }

    private fun getNewsData() = viewModelScope.launch {
        val response = withContext(Dispatchers.IO) {
            remoteRepository.getNews()
        }
        _articles.value = response.articles
        handleAllArticles(favoriteArticles.value)
    }

    fun handleAllArticles(list: List<Article>?) { // 2 favorite
        _articles.value?.map { it.copy(isFavorite = false) }?.toMutableList()
            ?.onEach { article ->
                list?.any { it.title == article.title }?.let { article.isFavorite = it }
            }?.let { _articles.value = it }
    }

    fun handleFavorites(article: Article) {
        if (article.isFavorite) {
            deleteFavorite(article)
        } else {
            addFavorite(article)
        }
    }

    private fun deleteFavorite(article: Article) = viewModelScope.launch {
        localRepository.removeArticle(article)
        val oldList = _articles.value?.toMutableList()
        _articles.value =
            oldList?.map {
                it.copy(
                    isFavorite = if (article.title == it.title) {
                        false
                    } else {
                        it.isFavorite
                    }
                )
            }
    }

    private fun addFavorite(article: Article) = viewModelScope.launch {
        localRepository.addArticle(article.copy(isFavorite = true))
    }
}