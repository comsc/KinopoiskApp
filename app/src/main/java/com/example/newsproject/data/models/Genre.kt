package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Genre(
    @ColumnInfo(name = "nameGenre")
    val name: String?
)