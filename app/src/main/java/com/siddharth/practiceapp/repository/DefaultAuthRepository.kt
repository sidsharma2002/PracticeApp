package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.Auth.AuthResponse
import com.siddharth.practiceapp.util.Response


interface DefaultAuthRepository {
    suspend fun authenticateUser(accessToken : String) : Response<String?>
}