package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import com.example.newsproject.data.db.Dao
import com.example.newsproject.data.models.Articles

class RepositoryRoom(private val dao: Dao) {


    fun getAllFavoriteArticles(): LiveData<List<Articles>> = dao.getAll()


    suspend fun addFavoriteArticle(item: Articles){
        dao.insert(item)
    }

    suspend fun delFromFavorite(item: Articles){
        dao.delete(item)
    }

    fun getTitleArticle(title:String?):Boolean = dao.getTitle(title)

    fun getSearchItem(title: String?) = dao.searchTitleItem(title)

}