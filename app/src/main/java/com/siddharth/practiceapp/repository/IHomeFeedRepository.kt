package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Response

interface IHomeFeedRepository {
    suspend fun fetchAndInsertTopNewsInDb() : Response<News?>
    suspend fun getHomeDataList() : Response<List<HomeData>>
}