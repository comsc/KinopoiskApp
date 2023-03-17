package com.example.newsproject.data.models

import androidx.room.Entity

//@Entity(tableName = "articles", primaryKeys = ["title"])
data class Article(
    val title: String = "",
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val url: String?,
    val urlToImage: String?,
    var isFavorite: Boolean = false
) : java.io.Serializable
