package com.example.newsproject.presentation.first

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.repository.Repository
import com.example.newsproject.data.models.NewsList
import kotlinx.coroutines.Dispatchers
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