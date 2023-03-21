package com.example.newsproject.data.repository

import androidx.lifecycle.LiveData
import com.example.newsproject.data.db.MovieDao
import com.example.newsproject.data.models.Doc

class LocalRepository(private val dao: MovieDao) {

    fun getFavoriteMoviesFromDb(): LiveData<List<Doc>> = dao.getFavoriteMovie()

    suspend fun addMovieToDb(item: Doc) {
        dao.insert(item)
    }

    suspend fun removeMovieFromDb(item: Doc) {
        dao.delete(item)
    }


}