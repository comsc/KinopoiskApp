package com.example.newsproject.presentation.second

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newsproject.R
import com.example.newsproject.data.db.MainDb
import com.example.newsproject.data.models.Articles
import com.example.newsproject.data.repository.RepositoryRoom
import kotlinx.coroutines.*
import java.util.Observable

class DetailViewModel(application: Application): AndroidViewModel(application) {

//    var allArticles = MutableLiveData<Articles>()

   //lateinit var allArt: LiveData<List<Articles>>
   var allArticles: LiveData<List<Articles>>? = null
   val context = application


    private val repo: RepositoryRoom
    //var exists:Boolean = false


    init {
        val dao = MainDb.getDb(context).getDao()
        repo = RepositoryRoom(dao)
        //allArticles = repo.getAllFavoriteArticles()
        getSavedArticles()
        //Log.d("MyTag", "init ${DetailViewModel::class.java.simpleName}")
    }

    private fun getSavedArticles() = viewModelScope.launch{
       val response = repo.getAllFavoriteArticles()
        allArticles = response
    }
    fun saveFavoriteArticle(item:Articles) = viewModelScope.launch (Dispatchers.IO){
        repo.addFavoriteArticle(item)
    }

    fun deleteFavoriteArticle(item: Articles) = viewModelScope.launch (Dispatchers.IO){
        repo.delFromFavorite(item)
    }


    //Функция сравнения на наличие в базе данных новости по загаловку, возвращает true/false
     suspend fun boolTitleFavorite(title:String?): Boolean = viewModelScope.async(Dispatchers.IO){
        return@async repo.getTitleArticle(title)
    }.await()

    fun showToastContext(toast:Boolean){

        if (toast){ Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_SHORT).show()}
        else {Toast.makeText(context, "Удалено из избранного", Toast.LENGTH_SHORT).show()}
    }
    //Поиск в БД по загалоку и удаление из общего списка новостей
    fun searchItem(title: String?) = viewModelScope.launch(Dispatchers.IO){
        repo.delFromFavorite(repo.getSearchItem(title))
    }

    //fun boolTitleFavorite(title:String?) = repo.getTitleArticle(title)
}