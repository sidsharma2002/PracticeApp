package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.util.Response

interface DefaultRepository {
    suspend fun fetchLikes(uid : Long)
    suspend fun getTopNews() : Response<News?>
}