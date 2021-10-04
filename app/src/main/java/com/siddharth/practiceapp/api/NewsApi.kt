package com.siddharth.practiceapp.api

import com.siddharth.practiceapp.data.entities.News.News
import com.siddharth.practiceapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopNewsUsingCoroutine(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("country")
        country: String = "us"
    ): Response<News>

    @GET("top-headlines")
    fun getTopNewsUsingThread(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("country")
        country: String = "in"
    ): Response<News>
}