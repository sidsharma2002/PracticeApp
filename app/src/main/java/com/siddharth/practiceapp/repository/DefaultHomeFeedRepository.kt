package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.util.Response

interface DefaultHomeFeedRepository {
    suspend fun getAndInsertHomeFeed(forFirstPage: Boolean): Any
    suspend fun getAllHomeFeedList() : Response<MutableList<HomeFeed>>
}