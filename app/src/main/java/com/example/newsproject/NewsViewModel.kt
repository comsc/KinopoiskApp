package com.example.newsproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.api.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel: ViewModel() {

    var repo = Repository()
    val myNewsList: MutableLiveData<Response<NewsList>> = MutableLiveData()

    fun getNewsData(){
        viewModelScope.launch {
            myNewsList.value = repo.getNews()
        }
    }
}