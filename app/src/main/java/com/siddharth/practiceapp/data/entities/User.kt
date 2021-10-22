package com.siddharth.practiceapp.data.entities

data class User(
    var name: String = "unknownUser",
    var uid: String = "",
    var phoneNumber: String? = null,
    var address: String? = null,
    var isProUser: Boolean = false,
)
