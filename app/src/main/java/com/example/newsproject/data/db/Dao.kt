package com.example.newsproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsproject.data.models.Articles

@androidx.room.Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:Articles)

    @Delete
    suspend fun delete(item: Articles)

    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<Articles>>

    @Query("SELECT EXISTS (SELECT 1 FROM articles WHERE title = :title)")
    fun getTitle(title:String?): Boolean

    @Query("SELECT * FROM articles WHERE title = :title")
    fun searchTitleItem(title: String?):Articles
}