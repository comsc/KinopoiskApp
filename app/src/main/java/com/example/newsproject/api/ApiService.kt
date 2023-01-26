package com.example.newsproject.api


import com.example.newsproject.NewsList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/v2/top-headlines?country=ru&apiKey=33639cc62af54d0dbcea5230d9c9894b")
    suspend fun getNews(): Response<NewsList>
}