package com.siddharth.computation.splitwise.customDataStructure

import android.util.SparseArray
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 *  Pool for hashGap, pop hashGaps from it and
 *  reallocate them after use.
 *
 *  @author Siddharth Sharma
 */
class HashGapPool<T, K> {

    private var mainPool: ArrayList<HashGap<T, K>> = ArrayList()
    private var maxPoolSize: Int = 10     // Default MaxSize

    /**
     *  HashMap extended Custom Data Structure,
     *  Handles logEvents to send in firebase analytics or any other service.
     *  Object Initialisation is limited by HashGapPool.
     */
    open class HashGap<T, K> : HashMap<T, K>() {
        /**
         * contains timeLogs when the HashGap is accessed
         */
        private var logs: ArrayList<LogType<*>> = ArrayList()

        init {
            logs.add(LogType.TimeAccess(System.currentTimeMillis()))
        }

        override fun put(key: T, value: K): K? {
            logs.add(LogType.TimeAccess(System.currentTimeMillis()))
            return value
        }

        sealed class LogType<T>(val data: T? = null) {
            class TimeAccess<T>(data: T) : LogType<T>(data)
        }
    }

    fun addInitialObjectsInPool() {
        if (mainPool.size != 0) {
            throw(IllegalAccessError())
        }
        for (i in 0..maxPoolSize) {
            mainPool.add(HashGap())
        }
    }

    fun pop(): Response<HashGap<T, K>> {
        return if (mainPool.size <= 0) {
            Response.Error.NoObjectAvailable()
        } else if (mainPool.size > maxPoolSize) {
            Response.Error.SizeLimitExceed()
        } else {
            val hashGapPopped = mainPool[mainPool.lastIndex]
            mainPool.removeAt(mainPool.lastIndex)
            Response.Success(hashGapPopped)
        }
    }

    sealed class Response<T>(val data: T? = null, val message: String? = null) {

        open class Error<T>(message: String? = null) : Response<T>(data = null, message) {
            class NoObjectAvailable<T>(message: String? = null) :
                Error<T>(message)

            class SizeLimitExceed<T>(message: String? = null) :
                Error<T>(message)
        }

        class Success<T>(data: T?) : Response<T>(data, message = null)
    }
}
