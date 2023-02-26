package com.example.newsproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsproject.data.models.Article

@androidx.room.Dao
interface ArticleDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Article)

    @Delete
    suspend fun delete(item: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT EXISTS (SELECT 1 FROM articles WHERE title = :title)")
    fun isNoteEmptyArticle(title: String?): Boolean

    @Query("SELECT * FROM articles WHERE title = :title")
    fun getArticle(title: String?): Article

    @Query("SELECT COUNT(*) FROM articles")
    fun countArticles(): Int
}