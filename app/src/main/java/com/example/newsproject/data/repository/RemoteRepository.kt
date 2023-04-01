package com.example.newsproject.data.repository

import android.content.Context
import com.example.newsproject.ObjectConst
import com.example.newsproject.data.api.ApiService
import com.example.newsproject.data.models.Kinopoisk
import com.example.newsproject.data.models.movie.Movie
import okhttp3.Cache
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.addHeaderLenient
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RemoteRepository(context: Context) {
    private val cacheSize:Long = 10 * 1024 * 1024 // 10 MB
    private val cache: Cache = Cache(File(context.cacheDir,"http-cash"), cacheSize)
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.kinopoisk.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().apply {
                addInterceptor(getLoggingInterceptor())
                addInterceptor{
                        chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader(name = "accept", value = ObjectConst.ACCEPT)
                        .addHeader(name = "X-API-KEY", value = ObjectConst.API_KEY)
                        .build()
                    chain.proceed(newRequest)
                }
            }.cache(cache).build())
            .build()
    private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    suspend fun getMovie(page:Int, limit:Int, type:String): Response<Kinopoisk> {
        return api.getMovieApi(page = page, limit = limit, type = type)
    }
//    suspend fun getSerials(): Response<Kinopoisk> {
//        return api.getSerialsApi()
//    }
//    suspend fun getCartoons(): Response<Kinopoisk> {
//        return api.getCartoonsApi()
//    }
    suspend fun searchMovie(name:String): Response<Kinopoisk>{
        return api.searchMovieApi(name = name)
    }
    suspend fun movieId(movieId:String):Movie{
        return api.searchMovieById(movieId = movieId)
    }

}


