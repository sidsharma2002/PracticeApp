package com.siddharth.computation.splitwise.customDataStructure

import android.util.SparseArray
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HashGapPool<T, K> {

    private var mainPool: ArrayList<HashGap<T, K>> = ArrayList()
    private var maxPoolSize: Int = 10     // Default MaxSize

    open class HashGap<T, K> : HashMap<T, K>() {
        private var timeLogs: ArrayList<LogType.TimeAccess<Long>> = ArrayList()

        init {
            timeLogs.add(LogType.TimeAccess(System.currentTimeMillis()))
        }

        sealed class LogType<T>(val data: T? = null) {
            class TimeAccess<T>(data: T) : LogType<T>(data)
        }
    }

    @Suppress
    fun addInitialObjectsInPool() {
        if (mainPool.size != 0) {
            throw(IllegalAccessError())
        }
        for (i in 0..maxPoolSize) {
            mainPool.add(HashGap())
        }
    }

    fun pop(): HashGapPoolResponse<HashGap<T, K>> {
        return if (mainPool.size <= 0) {
            HashGapPoolResponse.NoObjectAvailable()
        } else if (mainPool.size > maxPoolSize) {
            HashGapPoolResponse.SizeExceededLimit()
        } else {
            val hashGapPopped = mainPool[mainPool.lastIndex]
            mainPool.removeAt(mainPool.lastIndex)
            HashGapPoolResponse.Success(hashGapPopped)
        }
    }

    sealed class HashGapPoolResponse<T>(val data: T? = null, val message: String? = null) {
        class NoObjectAvailable<T>(message: String? = null) :
            HashGapPoolResponse<T>(data = null, message)

        class Success<T>(data: T?) : HashGapPoolResponse<T>(data, message = null)

        class SizeExceededLimit<T>(message: String? = null) :
            HashGapPoolResponse<T>(data = null, message)
    }
}
