package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import com.example.newsproject.data.db.ArticleDao
import com.example.newsproject.data.models.Article

class LocalRepository(private val dao: ArticleDao) {

    fun getAllFavoriteArticles(): LiveData<List<Article>> = dao.getAllArticles()

    suspend fun addArticle(item: Article) {
        dao.insert(item)
    }

    suspend fun updateArticle(item: Article) {
        dao.update(item)
    }

    suspend fun removeArticle(item: Article) {
        dao.delete(item)
    }

    fun isNotEmptyArticle(title: String?): Boolean = dao.isNoteEmptyArticle(title)

    fun getArticle(title: String?) = dao.getArticle(title)

    fun getCountArticles(): Int = dao.countArticles()

}