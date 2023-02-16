package com.example.newsproject.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles")
data class Articles(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) :java.io.Serializable
