package com.siddharth.practiceapp.repository

import android.util.Log
import com.siddharth.practiceapp.api.retrofitInstances.AuthApiInstance
import com.siddharth.practiceapp.data.dto.Auth.AuthRequest
import com.siddharth.practiceapp.util.ErrorHandler
import com.siddharth.practiceapp.util.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val errorHandler: ErrorHandler
) : DefaultAuthRepository {

    companion object {
        private const val TAG = "AuthRepository"
    }

    override suspend fun authenticateUser(accessToken: String): Response<String?> =
        errorHandler.safeCall {

            val result = AuthApiInstance.api.sendUserData(accessToken = accessToken)
            val req = AuthRequest(accessToken = accessToken)

            if (result.isSuccessful && result.body()?.success == true) {
                Log.d(TAG, "response is : ${result.body()}")
                return@safeCall Response.Success(result.body()!!.token)
            }
            Response.Error("error", null)
        }
}