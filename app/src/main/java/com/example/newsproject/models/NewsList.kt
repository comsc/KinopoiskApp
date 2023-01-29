package com.example.newsproject.models

data class NewsList(
    val articles: List<Articles>?,
    val status: String?,
    val totalResults: Int?
)