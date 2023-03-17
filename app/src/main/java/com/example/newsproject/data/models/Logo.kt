package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Logo(
    @ColumnInfo(name = "urlLogo")
    val url: String?
)