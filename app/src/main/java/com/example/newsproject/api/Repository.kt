package com.example.newsproject.api

import com.example.newsproject.NewsList
import retrofit2.Response

class Repository {
    suspend fun getNews(): Response<NewsList>{
        return RetrofitInstance.api.getNews()
    }
}