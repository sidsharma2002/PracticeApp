package com.siddharth.practiceapp.repository


import javax.inject.Inject
import android.util.Log
import com.siddharth.practiceapp.api.HomeFeedApi
import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.data.dto.feed.HomeFeedDto
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.data.mapper.Mappers
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class HomeFeedRepository @Inject constructor(
    private val api: HomeFeedApi,
    private val homeFeedDao: HomeFeedDao,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : DefaultHomeFeedRepository {

    private val TAG = this.javaClass.toString()

    override suspend fun getAndInsertHomeFeed(forFirstPage: Boolean) = withContext(dispatcherIO) {
        safeCall {
            val feedDtoList = api.getHomeFeedData()
            val homeFeedList: MutableList<HomeFeed> = mutableListOf()
            logResponse(feedDtoList)
            val mappedHomeFeed =
                Mappers.homeFeedDTOtoEntity(feedDtoList.body()!!)
            if (forFirstPage)
                homeFeedDao.deleteAllHomeFeed()
            homeFeedDao.insertHomeFeedList(mappedHomeFeed)
            Response.Success(homeFeedList)
        }
    }

    private fun logResponse(feedDtoList: retrofit2.Response<HomeFeedDto>) {
        if (feedDtoList.body() == null) {
            Log.d(TAG, " NULL")
        }
        for (i in feedDtoList.body()!!.feed) {
            Log.d(TAG, "homeFeed BODY : $i")
        }
        Log.d(TAG, "homeFeed body size : " + feedDtoList.body()!!.feed.size)
    }

    override suspend fun getAllHomeFeedList(): Response<MutableList<HomeFeed>> =
        withContext(dispatcherIO) {
            Response.Success(homeFeedDao.getAllHomeFeedList())
        }
}