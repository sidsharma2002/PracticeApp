package com.siddharth.practiceapp.repository

import android.util.Log
import com.siddharth.practiceapp.api.AuthApi
import com.siddharth.practiceapp.api.retrofitInstances.AuthApiInstance
import com.siddharth.practiceapp.data.dto.Auth.AuthRequest
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import dagger.Provides
import javax.inject.Inject

class AuthRepository() : DefaultAuthRepository {

    companion object {
        private const val TAG = "AuthRepository"
    }

    override suspend fun authenticateUser(accessToken: String): Response<String?> =
        safeCall {
            Log.d(TAG,"accessToken is = $accessToken")
            val result = AuthApiInstance.api.sendUserData(accessToken = accessToken)
            val req = AuthRequest(accessToken = accessToken)
            Log.d(TAG, req.toString())
            Log.d(TAG, "response is : ${result.body()}")
            if (result.isSuccessful && result.body()?.success == true) {
                Log.d(TAG, "response is : ${result.body()}")
                return Response.Success(result.body()!!.token)
            }
            Response.Error("error", null)
        }
}