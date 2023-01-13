package com.siddharth.practiceapp.worker

import androidx.work.ListenableWorker
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.notifications.NewsNotificationUseCase
import com.siddharth.practiceapp.repository.HomeDataMapper
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NewsWorkerControllerTest {

    private lateinit var SUT: NewsWorkerController
    private lateinit var newsNotificationUseCase: NewsNotificationUseCase
    private lateinit var repository: HomeFeedRepository
    private lateinit var homeDataMapper: HomeDataMapper

    private val fetchedHomeDataList = List(10) {
        HomeData(idKey = 0L, type = 1, id = it.toString())
    }

    @Test
    fun doWork_fetchesNewsFromServerAndInsertsInLocalDb() = runBlocking {
        // arrange
        onSuccess()

        // act
        SUT.doWork()

        // assert
        coVerify(exactly = 1) {
            repository.fetchNewsFromServerAndInsertInLocalDb()
        }
    }

    @Test
    fun onSuccess_doWork_returnsSuccessWorkerResult() = runBlocking {
        // arrange
        onSuccess()

        // act
        val result = SUT.doWork()

        // assert
        assert(result is ListenableWorker.Result.Success)
    }

    @Test
    fun onFailure_doWork_returnsFailureWorkerResult() = runBlocking {
        // arrange
        onFailure()

        // act
        val result = SUT.doWork()

        // assert
        assert(result is ListenableWorker.Result.Failure)
    }

    @Test
    fun onSuccess_doWork_getsNewsFromDbAndSendsNotificationOfAnyRandomElement() = runBlocking {
        // arrange
        onSuccess()

        // act
        SUT.doWork()

        // assert
        coVerify(exactly = 1) {
            repository.getAllHomeDataList()
            newsNotificationUseCase.showNewsNotification(any())
        }
    }

    @Test
    fun fetchNewsFromServerSuccess_getHomeDataListFromDbFailure_doWork_returnsRetryWorkerResult() = runBlocking {
        // arrange
        coEvery {
            repository.fetchNewsFromServerAndInsertInLocalDb()
        } returns Response.Success(Unit)

        coEvery {
            repository.getAllHomeDataList()
        } returns Response.Error("some error")

        // act
        val result = SUT.doWork()

        // assert
        assert(result is ListenableWorker.Result.Retry)

        coVerify(exactly = 0) {
            newsNotificationUseCase.showNewsNotification(any())
        }
    }

    @Test
    @Throws(NullPointerException::class)
    fun onSuccess_getAllHomeDataListReturnsEmptyList_doWork_doesNothing() = runBlocking {
        // arrange
        onSuccess()

        coEvery {
            repository.getAllHomeDataList()
        } returns Response.Success(listOf())

        // act
        SUT.doWork()

        // assert
        coVerify(exactly = 0) {
            newsNotificationUseCase.showNewsNotification(any())
        }
    }

    ///////////////////// helper methods region /////////////////////

    private fun onSuccess() {
        coEvery {
            repository.fetchNewsFromServerAndInsertInLocalDb()
        } returns Response.Success(Unit)

        coEvery {
            repository.getAllHomeDataList()
        } returns Response.Success(fetchedHomeDataList)
    }

    private fun onFailure() {
        coEvery {
            repository.fetchNewsFromServerAndInsertInLocalDb()
        } returns Response.Error("some error occurred")
    }

    @Before
    fun setup() {
        newsNotificationUseCase = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        homeDataMapper = HomeDataMapper()

        SUT = NewsWorkerController(newsNotificationUseCase, repository, homeDataMapper)
    }
}