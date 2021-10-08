package com.siddharth.practiceapp.repository

import android.provider.SyncStateContract
import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.util.Constants
import javax.inject.Inject


class Repository @Inject constructor(private val api: NewsApi) {

    suspend fun getTopNewsUsingCoroutine() = api.getTopNewsUsingCoroutine(Constants.API_KEY, "in")

    fun getTopNewsUsingThread() = api.getTopNewsUsingThread(Constants.API_KEY, "in")
}