package com.siddharth.practiceapp.util



inline fun <T> safeCall(action: () -> Response<T?>): Response<T?> {
    return try {
        action()
    } catch (e: Exception) {
        Response.Error(e.localizedMessage ?: "Some unknown error occurred")
    }
}