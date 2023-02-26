package com.example.newsproject.data.models

data class NewsList(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)