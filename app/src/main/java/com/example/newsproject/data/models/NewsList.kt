package com.example.newsproject.data.models

data class NewsList(
    val articles: List<Articles>?,
    val status: String?,
    val totalResults: Int?
)