package com.example.newsproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsproject.data.models.Doc

@Dao
interface MovieDao {

    @Insert( entity = Doc::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Doc)

    @Delete(entity = Doc::class)
    suspend fun delete(item: Doc)

    @Query("SELECT * FROM movies")
    fun getFavoriteMovie(): LiveData<List<Doc>>

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :id)")
    fun isFavoriteMovie(id: Int?): Boolean

//    @Query("SELECT * FROM articles WHERE title = :title")
//    fun getArticle(title: String?): Article
//
//    @Query("SELECT COUNT(*) FROM articles")
//    fun countArticles(): Int
}