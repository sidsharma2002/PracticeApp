package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.ErrorHandler
import com.siddharth.practiceapp.util.Response
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeFeedRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val homeDataDao: HomeDataDao,
    private val homeDataMapper: HomeDataMapper,
    private val errorHandler: ErrorHandler
) {

    suspend fun fetchNewsFromServerAndInsertInLocalDb(): Response<Unit> = errorHandler.safeCall {
        val newsResult = newsApi.getTopNews(Constants.NEWS_API_KEY)

        if (errorHandler.isResultInValid(newsResult)) {
            return@safeCall Response.Error(newsResult.message() ?: "some error occurred")
        }

        // make this operation non cancellable, we don't want to loose the fetched data in any case.
        withContext(NonCancellable) {
            val homeDataList = parseAndGetHomeDataListFromNewsResponse(newsResult)

            if (homeDataList.isNotEmpty())
                homeDataDao.deleteAndInsertTransaction(homeDataList)
        }

        return@safeCall Response.Success(Unit)
    }

    private fun parseAndGetHomeDataListFromNewsResponse(newsResult: retrofit2.Response<News>): List<HomeData> {
        val homeDataList = mutableListOf<HomeData>()

        newsResult.body()?.articles?.forEach { article ->
            val homeData = homeDataMapper.getHomeDataFromArticle(article)
            homeDataList.add(homeData)
        }

        return homeDataList
    }

    suspend fun getAllHomeDataList(): Response<List<HomeData>> = errorHandler.safeCall {
        val result = homeDataDao.getAllHomeDataList()
        return@safeCall Response.Success(result)
    }
}