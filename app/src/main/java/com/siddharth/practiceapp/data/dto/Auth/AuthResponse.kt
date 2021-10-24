package com.siddharth.practiceapp.data.dto.Auth

data class AuthResponse(
    val success : Boolean,
    val message : String,
    val token : String
)