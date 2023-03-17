package com.example.newsproject.data.models

import androidx.room.ColumnInfo

data class Votes(
    @ColumnInfo(name = "awaitVotes")
    val await: Int?,
    @ColumnInfo(name = "filmCriticsVotes")
    val filmCritics: Int?,
    @ColumnInfo(name = "imdbVotes")
    val imdb: Int?,
    @ColumnInfo(name = "kpVotes")
    val kp: Int?,
    @ColumnInfo(name = "russianFilmCriticsVotes")
    val russianFilmCritics: Int?
)