package com.example.newsproject.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.repository.LocalRepository
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val localRepository: LocalRepository = DataObject.localRepository

    val favoriteArticles = localRepository.getFavoiteGiphyFromDb()

    fun deleteFavorite(article: Doc) = viewModelScope.launch {
        localRepository.removeArticle(article)
    }
}