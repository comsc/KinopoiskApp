package com.example.newsproject.presentation.second

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.db.MainDb
import com.example.newsproject.data.models.Articles
import com.example.newsproject.data.repository.Repository
import com.example.newsproject.data.repository.RepositoryRoom
import com.example.newsproject.presentation.first.NewsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class DetailViewModel(application: Application): AndroidViewModel(application) {

//    var allArticles = MutableLiveData<Articles>()

    private val allArt: LiveData<List<Articles>>
    val allArticles: LiveData<List<Articles>>



    private val repo: RepositoryRoom
    val context = application
    //var exists:Boolean = false


    init {
        val dao = MainDb.getDb(context).getDao()
        repo = RepositoryRoom(dao)
        allArt = repo.getAllFavoriteArticles()
        Log.d("MyTag", "init ${DetailViewModel::class.java.simpleName}")
//        getSavedArticles()
        allArticles = allArt
    }

    private fun getSavedArticles() = viewModelScope.launch ( Dispatchers.IO ){
       repo.getAllFavoriteArticles()

    }
    fun saveFavoriteArticle(item:Articles) = viewModelScope.launch (Dispatchers.IO){
        repo.addFavoriteArticle(item)
    }

    fun deleteFavoriteArticle(item: Articles) = viewModelScope.launch (Dispatchers.IO){
        repo.delFromFavorite(item)
    }



     suspend fun boolTitleFavorite(title:String?): Boolean = viewModelScope.async(Dispatchers.IO){
        return@async repo.getTitleArticle(title)
    }.await()

//    fun boolTitleFavorite(title:String?): Boolean = repo.getTitleArticle(title)
}