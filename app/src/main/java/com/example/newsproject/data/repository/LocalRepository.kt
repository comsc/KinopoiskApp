package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import com.example.newsproject.data.db.ArticleDao
import com.example.newsproject.data.models.Doc

class LocalRepository(private val dao: ArticleDao) {

    fun getFavoiteGiphyFromDb(): LiveData<List<Doc>> = dao.getFavoriteGiphy()

    suspend fun addArticle(item: Doc) {
        dao.insert(item)
    }

    suspend fun removeArticle(item: Doc) {
        dao.delete(item)
    }


}