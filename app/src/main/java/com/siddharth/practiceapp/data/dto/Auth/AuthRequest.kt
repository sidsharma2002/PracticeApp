package com.siddharth.practiceapp.data.dto.Auth

import java.io.Serializable

data class AuthRequest(
    val provider : String = "Google",
    val accessToken : String
) : Serializable