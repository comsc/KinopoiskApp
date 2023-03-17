package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Poster(
    @ColumnInfo(name = "previewUrlPoster")
    val previewUrl: String?,
    @ColumnInfo(name = "urlPoster")
    val url: String?
)