package com.example.newsproject.data.api


import com.example.newsproject.ObjectConst
import com.example.newsproject.data.models.Kinopoisk
import com.example.newsproject.data.models.movie.Movie
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    //  @GET("/v1/movie?page=1&limit=10&type=movie&year=2023")
    @GET("/v1/movie")
    suspend fun getMovieApi(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = ObjectConst.API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("type") type: String = "movie",
        @Query("year") year: Int = 2022,
    ):Response<Kinopoisk>
    @GET("/v1/movie")
    suspend fun getSerialsApi(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = ObjectConst.API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("type") type: String = "tv-series",
        @Query("year") year: Int = 2022,
    ):Response<Kinopoisk>
    @GET("/v1/movie")
    suspend fun getCartoonsApi(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = ObjectConst.API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("type") type: String = "cartoon",
        @Query("year") year: Int = 2022,
    ):Response<Kinopoisk>
    //v1/movie?page=1&limit=10&name=spider'
    @GET("/v1/movie")
    suspend fun searchMovieApi(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = ObjectConst.API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("name") name: String,
    ):Kinopoisk

    @GET("/v1/movie/{movieId}")
    suspend fun searchMovieById(
        @Header("accept") accept: String = "application/json",
        @Header("X-API-KEY") apikey: String = ObjectConst.API_KEY,
        @Path("movieId") movieId:String
    ):Movie
}




