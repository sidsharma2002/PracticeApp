package com.siddharth.practiceapp.repository


import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.util.Constants
import javax.inject.Inject
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class Repository @Inject constructor(private val api: NewsApi) : DefaultRepository {

    private val TAG = this.toString()

    suspend fun getTopNewsUsingCoroutine() = api.getTopNewsUsingCoroutine(Constants.API_KEY, "us")
    override suspend fun getTopNews() =
        safeCall {
            val news = api.getTopNewsUsingCoroutine(Constants.API_KEY, "in")
            Response.Success(news.body())
        }

    fun getTopNewsUsingThread() = api.getTopNewsUsingThread(Constants.API_KEY, "us")

    override suspend fun fetchLikes(uid: Long) {

    }

}