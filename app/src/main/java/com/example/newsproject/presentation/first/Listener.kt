package com.example.newsproject.presentation.first

import com.example.newsproject.data.models.Articles

interface Listener {

    fun onClick(item: Articles)

    fun adFavoriteOnRc(item: Articles)

    fun delFavoriteOnRc(item: Articles)

    suspend fun boolInTitle(title: String?):Boolean

    fun showToast()

    fun searchItem(title: String?)

}