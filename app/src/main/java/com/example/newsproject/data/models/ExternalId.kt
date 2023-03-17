package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class ExternalId(
    @ColumnInfo(name = "imdbExt")
    val imdb: String?,
    @ColumnInfo(name = "kpHDExt")
    val kpHD: String?,
    @ColumnInfo(name = "tmdbExt")
    val tmdb: Int?
)