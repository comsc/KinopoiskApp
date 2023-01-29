package com.example.newsproject.api

import com.example.newsproject.models.NewsList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().apply { addInterceptor(getLoggingInterceptor()) }.build())
            .build()
    private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    suspend fun getNews(): NewsList  {
        return api.getNewsApi()
    }
}


