package com.example.newsproject.presentation.first

import com.example.newsproject.data.models.Doc

interface Listener {

    fun onClick(item: Doc)
//    fun addFavorite(item: Doc)
//    fun deleteFavorite(item: Doc)
}