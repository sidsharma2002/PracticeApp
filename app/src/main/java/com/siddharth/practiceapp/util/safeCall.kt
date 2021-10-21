package com.siddharth.practiceapp.util

import android.util.Log


inline fun <T> safeCall(action: () -> Response<T?>): Response<T?> {
    return try {
        action()
    } catch (e: Exception) {
        Log.d("safeCall", e.toString())
        Response.Error(e.localizedMessage ?: "Some unknown error occurred")
    }
}