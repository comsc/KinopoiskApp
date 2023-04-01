package com.example.newsproject.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.models.Doc
import com.example.newsproject.utils.Resource
import com.example.newsproject.utils.extensions.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private val remoteRepository = DataObject.remoteRepository

    private val _movies = MutableLiveData<Resource<List<Doc>>>()
    val movies = _movies.asLiveData()

     fun searchMovie(name:String) = viewModelScope.launch{
         _movies.postValue(Resource.Loading())
       try {
           val response = withContext(Dispatchers.IO) {
               remoteRepository.searchMovie(name)
           }
           _movies.postValue(Resource.Success(response.body()?.docs))
       }catch (e:Exception){
           _movies.postValue(Resource.Error(message = null))
       }

    }

}