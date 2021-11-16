package com.siddharth.practiceapp.api


import com.siddharth.practiceapp.data.dto.feed.HomeFeedDto
import retrofit2.Response
import retrofit2.http.GET


interface HomeFeedApi {
    @GET("home/feed")
    suspend fun getHomeFeedData(
    ): Response<HomeFeedDto>
}