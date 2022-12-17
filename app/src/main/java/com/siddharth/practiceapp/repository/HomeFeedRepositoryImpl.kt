package com.siddharth.practiceapp.repository


import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.util.Constants
import javax.inject.Inject
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response as Response1


class HomeFeedRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val homeDataDao: HomeDataDao
) : IHomeFeedRepository {

    private val TAG = this.javaClass.toString()

    override suspend fun fetchAndInsertTopNewsInDb() = withContext(Dispatchers.IO) {
        safeCall {
            TODO("fetch from server, handle errors, parse and insert in db")
        }
    }


    private suspend fun getTopNewsFromServer(): Response1<News> {
        return api.getTopNews(Constants.NEWS_API_KEY, "in")
    }

    private suspend fun getErrorIfNewsFetchWasUnSuccessful(news : Response1<News>) : String? {
        return if (news.isSuccessful.not() || news.body() == null)
            news.message()
        else
            null
    }

    private suspend fun parseHomeDataFromNewsAndInsertInDb(news: News) {
        val homeDataList = getParsedHomeDataFromNews(news)
        homeDataDao.deleteAndInsertTransaction(homeDataList)
    }

    private suspend fun getParsedHomeDataFromNews(news : News) : List<HomeData> {
        val homeDataList: MutableList<HomeData> = mutableListOf()

        for (element in news.articles) {
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
            homeDataList.add(homeData)
        }

        return homeDataList
    }

    override suspend fun getHomeDataList(): Response<List<HomeData>> {
        TODO()
    }
}