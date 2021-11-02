package com.siddharth.practiceapp.api

import com.siddharth.practiceapp.data.dto.Auth.AuthRequest
import com.siddharth.practiceapp.data.dto.Auth.AuthResponse
import com.siddharth.practiceapp.util.Constants
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject


interface AuthApi {
    @POST("user/auth")
    suspend fun sendUserData(
        @Query("provider")
        provider: String = "Google",
        @Query("accessToken")
        accessToken: String
         ): Response<AuthResponse>
}