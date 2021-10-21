package com.siddharth.practiceapp.repository


import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.util.Constants
import javax.inject.Inject
import android.util.Log
import androidx.room.Database
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall


class Repository @Inject constructor(
    private val api: NewsApi,
    private val homeDataDao: HomeDataDao
) : DefaultRepository {

    private val TAG = this.javaClass.toString()

    suspend fun getTopNewsUsingCoroutine() = api.getTopNewsUsingCoroutine(Constants.API_KEY, "us")
    override suspend fun getTopNews() =
        safeCall {
            val news = api.getTopNewsUsingCoroutine(Constants.API_KEY, "in")
            val newsList: MutableList<HomeData> = mutableListOf()
            Log.d(TAG, "news body size : " + news.body()!!.articles.size)
            for((counter, element) in news.body()!!.articles.withIndex()){
                val homeData = HomeData(
                    1,
                    element.url,
                    element.author,
                    element.content,
                    element.description,
                    element.title,
                    element.url,
                    element.urlToImage
                )
                newsList.add(homeData)
            }
           homeDataDao.deleteAndInsertTransaction(newsList)
            Response.Success(news.body())
        }

    override suspend fun getAllHomeDataList(): Response<List<HomeData>> {
        val size = homeDataDao.getAllHomeDataList().size
        Log.d(TAG, "2. size from db $size")
        return Response.Success(homeDataDao.getAllHomeDataList())
    }

    fun getTopNewsUsingThread() = api.getTopNewsUsingThread(Constants.API_KEY, "us")

    override suspend fun fetchLikes(uid: Long) {

    }

}