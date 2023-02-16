package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import com.example.newsproject.data.db.Dao
import com.example.newsproject.data.db.MainDb
import com.example.newsproject.data.models.Articles

class RepositoryRoom(private val dao: Dao) {


    fun getAllFavoriteArticles(): LiveData<List<Articles>> = dao.getAll()


    suspend fun addFavoriteArticle(item: Articles){
        dao.insert(item)
    }

}