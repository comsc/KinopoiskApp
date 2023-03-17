package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Name(
    @ColumnInfo(name = "languageName")
    val language: String?,
    @ColumnInfo(name = "nameName")
    val name: String?,
    @ColumnInfo(name = "typeName")
    val type: String?
)