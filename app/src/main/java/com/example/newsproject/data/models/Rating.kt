package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Rating(
    @ColumnInfo(name = "awaitRating")
    val await: Double?,
    @ColumnInfo(name = "filmCriticsRating")
    val filmCritics: Double?,
    @ColumnInfo(name = "imdbRating")
    val imdb: Double?,
    @ColumnInfo(name = "kpRating")
    val kp: Double?,
    @ColumnInfo(name = "russianFilmCriticsRating")
    val russianFilmCritics: Double?
)