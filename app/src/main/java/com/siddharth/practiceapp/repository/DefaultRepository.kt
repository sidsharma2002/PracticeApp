package com.siddharth.practiceapp.repository

interface DefaultRepository {
    suspend fun fetchLikes(uid : Long)
}