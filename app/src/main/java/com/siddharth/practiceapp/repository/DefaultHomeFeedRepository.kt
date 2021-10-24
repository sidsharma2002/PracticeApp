package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Response

interface DefaultHomeFeedRepository {
    suspend fun fetchLikes(uid : Long)
    suspend fun getTopNews() : Response<News?>
    suspend fun getAllHomeDataList() : Response<List<HomeData>>
}