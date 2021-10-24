package com.siddharth.practiceapp.api.retrofitInstances

import com.siddharth.practiceapp.api.AuthApi
import com.siddharth.practiceapp.util.Constants.AUTH_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthApiInstance {
    val api: AuthApi =  Retrofit.Builder()
        .baseUrl(AUTH_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthApi::class.java)
}