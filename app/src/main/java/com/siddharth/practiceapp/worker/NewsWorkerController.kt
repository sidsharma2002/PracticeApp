package com.siddharth.practiceapp.worker

import androidx.work.ListenableWorker
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.notifications.NewsNotificationUseCase
import com.siddharth.practiceapp.repository.HomeDataMapper
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Response
import javax.inject.Inject

class NewsWorkerController @Inject constructor(
    private val newsNotificationUseCase: NewsNotificationUseCase,
    private val repository: HomeFeedRepository,
    private val homeDataMapper: HomeDataMapper
) {
    suspend fun doWork(): ListenableWorker.Result {
        val result = repository.fetchNewsFromServerAndInsertInLocalDb()

        if (result is Response.Error) {
            return ListenableWorker.Result.failure()
        }

        val homeDataListResult = repository.getAllHomeDataList()

        if (homeDataListResult is Response.Error) {
            return ListenableWorker.Result.retry()
        }

        val isDataUseable =
            homeDataListResult is Response.Success && homeDataListResult.data?.isNotEmpty() == true

        if (isDataUseable) {
            showNewsNotification(latestHomeData = homeDataListResult.data!!.random())
        }

        return ListenableWorker.Result.success()
    }

    private suspend fun showNewsNotification(latestHomeData: HomeData) {
        val mappedArticle =
            homeDataMapper.getArticleFromHomeData(latestHomeData)
        newsNotificationUseCase.showNewsNotification(mappedArticle)
    }
}