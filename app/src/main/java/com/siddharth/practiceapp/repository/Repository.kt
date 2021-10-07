package com.siddharth.practiceapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Repository @Inject constructor() : DefaultRepository {

    private val TAG = this.toString()

    val likesCount = MutableLiveData<Int>()
    val shouldCancel = MutableLiveData<Boolean>()

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