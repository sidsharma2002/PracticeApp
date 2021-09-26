package com.siddharth.practiceapp.api

import com.siddharth.practiceapp.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
       private val retrofit  = {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api = retrofit.invoke().create(NewsApi::class.java)
    }
}