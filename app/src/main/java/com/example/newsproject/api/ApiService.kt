package com.example.newsproject.api


import com.example.newsproject.models.NewsList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    suspend fun getNewsApi(
        @Query("country") country: String = "ru",
        @Query("apiKey") apiKey: String = "33639cc62af54d0dbcea5230d9c9894b"
    ): NewsList
}

