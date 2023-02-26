package com.example.newsproject.presentation.first

import com.example.newsproject.data.models.Article

interface Listener {

    fun onClick(item: Article)
    fun addFavorite(item: Article)
    fun deleteFavorite(item: Article)

}