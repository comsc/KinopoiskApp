package com.example.newsproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.api.Repository
import com.example.newsproject.models.NewsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsViewModel : ViewModel() {

    private val repo = Repository()
    private val mList = MutableLiveData<NewsList?>()
    val myNewsList: LiveData<NewsList?> = mList

    init {
        Log.d("MyTag", "init ${NewsViewModel::class.java.simpleName}")
        getNewsData()
    }

    fun getNewsData() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.getNews()
                }
                mList.value = response

            } catch (error: Throwable) {
                Log.d("MyTag", error.message.orEmpty(), error)
            }

        }
    }
}