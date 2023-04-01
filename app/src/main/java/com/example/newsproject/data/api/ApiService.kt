package com.example.newsproject.data.api

import com.example.newsproject.data.models.Kinopoisk
import com.example.newsproject.data.models.movie.Movie
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    //  @GET("/v1/movie?page=1&limit=10&type=movie&year=2023")
    @GET("/v1/movie")
    suspend fun getMovieApi(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String,
        @Query("year") year: Int = 2023,
    ):Response<Kinopoisk>

    //v1/movie?page=1&limit=10&name=spider'
    @GET("/v1/movie")
    suspend fun searchMovieApi(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("name") name: String,
    ):Response<Kinopoisk>

    @GET("/v1/movie/{movieId}")
    suspend fun searchMovieById(
        @Path("movieId") movieId:String
    ):Movie
}




