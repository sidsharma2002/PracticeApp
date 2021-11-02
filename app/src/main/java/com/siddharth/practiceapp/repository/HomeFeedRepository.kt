package com.siddharth.practiceapp.repository


import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.util.Constants
import javax.inject.Inject
import android.util.Log
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall


class HomeFeedRepository @Inject constructor(
    private val api: NewsApi,
    private val homeDataDao: HomeDataDao
) : DefaultHomeFeedRepository {

    private val TAG = this.javaClass.toString()

    suspend fun getTopNewsUsingCoroutine() = api.getTopNewsUsingCoroutine(Constants.NEWS_API_KEY, "us")
    override suspend fun getAndInsertTopNews() =
        safeCall {
            val news = api.getTopNewsUsingCoroutine(Constants.NEWS_API_KEY, "in")
            val newsList: MutableList<HomeData> = mutableListOf()
            Log.d(TAG, "news body size : " + news.body()!!.articles.size)
            for(element in news.body()!!.articles){
                val homeData = HomeData(
                    0,
                    1,
                    element.url,
                    element.author,
                    element.content,
                    element.description,
                    element.title,
                    element.url,
                    element.urlToImage
                )
                Log.d(TAG, "news list size : " + newsList.size)
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

    fun getTopNewsUsingThread() = api.getTopNewsUsingThread(Constants.NEWS_API_KEY, "us")

    override suspend fun fetchLikes(uid: Long) {

    }

}