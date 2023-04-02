package com.example.newsproject.presentation.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.models.movie.Movie
import com.example.newsproject.data.models.movie.Trailer
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel: ViewModel() {

    private val localRepository = DataObject.localRepository
    private val remoteRepository = DataObject.remoteRepository
    private val _detailMovie = MutableLiveData<Doc>()
    val detailMovie = _detailMovie.asLiveData()

    private val _movies = MutableLiveData<Movie>()
    val movies = _movies.asLiveData()

    fun movieId(movieId:String) = viewModelScope.launch{
        try {
            val response = withContext(Dispatchers.IO) {
                remoteRepository.movieId(movieId)
            }
            _movies.value = response
        }catch (e:Exception){
            return@launch
        }
    }

    fun isFavoriteMovie(id:Int?) = viewModelScope.launch(Dispatchers.IO) {
        _detailMovie.value?.isFavorite = localRepository.isFavoriteMovie(id)
    }


    fun setDetail(movie: Doc) {
        _detailMovie.value = movie
    }

    fun handleFavorite() {
        val movie = _detailMovie.value ?: return
        if (movie.isFavorite) {
            deleteFavorite(movie)
        } else {
            addToFavorite(movie)
        }
    }
    private fun addToFavorite(movie: Doc) {
        val actualMovie = movie.copy(isFavorite = true)
        setDetail(actualMovie)
        viewModelScope.launch {
            localRepository.addMovieToDb(actualMovie)
        }
    }


    private fun deleteFavorite(movie: Doc) {
        val actualMovie = movie.copy(isFavorite = false)
        setDetail(actualMovie)
        viewModelScope.launch {
            localRepository.removeMovieFromDb(movie.copy(isFavorite = true))
        }
    }
    //@SuppressLint("SuspiciousIndentation")
    fun getUrlTrailers(list:List<Trailer>):String? {
        val url = list.last().url
        return if (url?.contains("embed") == true){
            url.substringAfterLast("embed/")
        } else url?.substringAfterLast("=")
    }

}