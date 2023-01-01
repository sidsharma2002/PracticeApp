package com.siddharth.practiceapp.util

import retrofit2.Response
import com.siddharth.practiceapp.util.Response as Response1

class ErrorHandler {
    inline fun <T> safeCall(action: () -> Response1<T>): Response1<T> {
        return try {
            action.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            Response1.Error(e.localizedMessage ?: "Some unknown error occurred")
        }
    }

    fun <T> isResultInValid(response: retrofit2.Response<T>) : Boolean {
        return response.code() != 200 || response.body() == null
    }
}