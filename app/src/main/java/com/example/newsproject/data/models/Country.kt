package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Country(
    @ColumnInfo(name = "nameCountry")
    val name: String?
)