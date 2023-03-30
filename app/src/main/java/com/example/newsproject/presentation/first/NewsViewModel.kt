package com.example.newsproject.presentation.first

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.map
import androidx.paging.*
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.paging.MoviePagingSource
import com.example.newsproject.data.paging.MovieSourceFactory
import com.example.newsproject.data.repository.RemoteRepository
import com.example.newsproject.utils.Resource
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel: ViewModel() {

    private val remoteRepository = DataObject.remoteRepository
    private val localRepository = DataObject.localRepository
//    private val movieFactory = MovieSourceFactory()

    private var _movies = MutableLiveData<Resource<List<Doc>>>()
    val movies = getMovie()

    val favoriteArticles = localRepository.getFavoriteMoviesFromDb()

    init {
        getMovie()
    }

//    fun handleAllArticles(list: List<Article>?) { // 2 favorite
//        _articles.value?.map { it.copy(isFavorite = false) }?.toMutableList()
//            ?.onEach { article ->
//                list?.any { it.title == article.title }?.let { article.isFavorite = it }
//            }?.let { _articles.value = it }
//    }

//    fun getMovieData(list: List<Doc>?) = viewModelScope.launch {
//        _movies.postValue(Resource.Loading())
//        val response = withContext(Dispatchers.IO) {
//            remoteRepository.getMovie()
//        }
//
//        if (response.isSuccessful){
//            response.body()?.docs?.map { it.copy(isFavorite = false) }?.toMutableList()
//                ?.onEach { article ->
//                    list?.any { it.id == article.id }?.let { article.isFavorite = it }
//                }?.let { _movies.postValue(Resource.Success(it))  }
//        }else {
//            _movies.postValue(Resource.Error(message = response.message()))
//        }
//
//    }
//fun getMovieData(): LiveData<PagingData<Doc>> {
//    return movieFactory.getMovie()
//}
    private fun getMovie()=  Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(remoteRepository) }
    ).liveData.cachedIn(viewModelScope)



    fun handleFavorites(article: Doc) {
        if (article.isFavorite) {
            deleteFavorite(article)
        } else {
            addFavorite(article)
        }
    }

    private fun deleteFavorite(article: Doc) = viewModelScope.launch {
        localRepository.removeMovieFromDb(article)
        val oldList = _movies.value?.data?.toMutableList()
        _movies.value?.data =
            oldList?.map {
                it.copy(
                    isFavorite = if (article.id == it.id) {
                        false
                    } else {
                        it.isFavorite
                    }
                )
            }
    }

    private fun addFavorite(article: Doc) = viewModelScope.launch {
        localRepository.addMovieToDb(article.copy(isFavorite = true))
    }
}