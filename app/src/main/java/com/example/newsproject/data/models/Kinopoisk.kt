package com.example.newsproject.data.models

data class Kinopoisk(
    var docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)