package com.siddharth.practiceapp.repository


import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.util.Constants
import javax.inject.Inject
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class Repository @Inject constructor(private val api: NewsApi) : DefaultRepository {

    private val TAG = this.toString()
    val likesCount = MutableLiveData<Int>()
    val shouldCancel = MutableLiveData<Boolean>()

    suspend fun getTopNewsUsingCoroutine() = api.getTopNewsUsingCoroutine(Constants.API_KEY, "in")
    fun getTopNewsUsingThread() = api.getTopNewsUsingThread(Constants.API_KEY, "in")

    override suspend fun fetchLikes(uid: Long) {
        if (shouldCancel.value == true) return
        withContext(Dispatchers.IO) {
            val likes = fetchLikesFromServer(uid)
            likesCount.postValue(likes)
            delay(2000)
            Log.d(TAG, "fired")
            fetchLikes(uid + 1)
        }
    }

    private fun fetchLikesFromServer(uid: Long): Int {
        return uid.toInt()
    }
}