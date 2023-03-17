package com.example.newsproject.data.api


import com.example.newsproject.data.models.Kinopoisk
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {
    //  @GET("/v1/movie?page=1&limit=10&type=movie&year=2023")
    @GET("/v1/movie")
    suspend fun getMovieApi(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = "M5063AJ-P0G41K7-N39KGY2-7NTX3VD",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("type") type: String = "movie",
        @Query("year") year: Int = 2023,
    ):Kinopoisk
    //v1/movie?page=1&limit=10&name=spider'
    @GET("/v1/movie")
    suspend fun searchMovieApi(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = "M5063AJ-P0G41K7-N39KGY2-7NTX3VD",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("name") name: String,
    ):Kinopoisk
}




