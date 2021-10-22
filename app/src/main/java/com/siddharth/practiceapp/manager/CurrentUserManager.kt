package com.siddharth.practiceapp.manager

import com.siddharth.practiceapp.data.entities.User

object CurrentUserManager {
    val currentUser = User()
    var isLoggedIn : Boolean = true
}