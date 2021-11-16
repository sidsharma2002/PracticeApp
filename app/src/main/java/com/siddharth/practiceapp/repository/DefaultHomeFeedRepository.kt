package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.util.Response

interface DefaultHomeFeedRepository {
    suspend fun getAndInsertHomeFeed() : Any
    suspend fun getAllHomeFeedList() : Response<MutableList<HomeFeed>>
}