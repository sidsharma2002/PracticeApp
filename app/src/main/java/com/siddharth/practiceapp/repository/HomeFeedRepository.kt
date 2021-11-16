package com.siddharth.practiceapp.repository


import javax.inject.Inject
import android.util.Log
import android.widget.Toast
import com.siddharth.practiceapp.api.HomeFeedApi
import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.data.mapper.Mappers
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class HomeFeedRepository @Inject constructor(
    private val api: HomeFeedApi,
    private val homeFeedDao: HomeFeedDao
) : DefaultHomeFeedRepository {

    private val TAG = this.javaClass.toString()

    override suspend fun getAndInsertHomeFeed() = withContext(Dispatchers.IO) {
        safeCall {
            val feedDtoList = api.getHomeFeedData()
            val homeFeedList: MutableList<HomeFeed> = mutableListOf()
            if (feedDtoList.body() == null) {
                Log.d(TAG, " NULL")
            }
            for(i in feedDtoList.body()!!.feed){
                Log.d(TAG, "homeFeed BODY : $i")
            }
            Log.d(TAG, "homeFeed body size : " + feedDtoList.body()!!.feed.size)
            val mappedHomeFeed =
                Mappers.homeFeedDTOtoEntity(feedDtoList.body()!!)
            homeFeedDao.deleteAllHomeFeed()
            homeFeedDao.insertHomeFeedList(mappedHomeFeed)
            Response.Success(homeFeedList)
        }
    }

    override suspend fun getAllHomeFeedList(): Response<MutableList<HomeFeed>> {
        val size = homeFeedDao.getAllHomeFeedList().size
        Log.d(TAG, "2. size from db $size")
        return Response.Success(homeFeedDao.getAllHomeFeedList())
    }

}