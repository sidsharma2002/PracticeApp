package com.siddharth.practiceapp.ui.home

import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.home.HomeFeedController
import com.siddharth.practiceapp.home.HomeFeedViewMvc
import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.Response
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeFeedControllerTest {

    private lateinit var homeFeedRepository: HomeFeedRepository
    private lateinit var homeFeedViewMvc: HomeFeedViewMvc
    private lateinit var SUT: HomeFeedController

    private val homeDataList = List(5) {
        HomeData(type = 1)
    }

    /////////////////////////////// Tests Region ///////////////////////////////

    @Test
    fun repositoryReturnsSuccessfully_onStart_fetchesDataFromLocalDbAndBindsItToView() =
        runBlocking {
            // arrange
            getAllHomeDataListReturnsSuccessData()
            fetchAndInsertTopNewsPerformsSuccessfully()

            // act
            SUT.bindView(homeFeedViewMvc)
            SUT.onStart()

            // assert
            coVerify(ordering = Ordering.ORDERED) {
                homeFeedRepository.getAllHomeDataList()
                homeFeedViewMvc.bindHomeDataListToView(homeDataList)
            }
        }

    @Test
    fun repositoryReturnsError_onStart_doesNotBindItToView() = runBlocking {
        // arrange
        coEvery {
            homeFeedRepository.getAllHomeDataList()
        } returns Response.Error("")

        fetchAndInsertTopNewsPerformsSuccessfully()

        // act
        SUT.bindView(homeFeedViewMvc)
        SUT.onStart()

        // assert
        coVerify(atLeast = 1) {
            homeFeedRepository.getAllHomeDataList()
        }

        coVerify(exactly = 0) {
            homeFeedViewMvc.bindHomeDataListToView(any())
        }
    }

    @Test
    fun repositoryReturnsSuccess_onStart_fetchesDataFromRepositoryInCorrectSequence() =
        runBlocking {
            // arrange
            getAllHomeDataListReturnsSuccessData()
            fetchAndInsertTopNewsPerformsSuccessfully()

            // act
            SUT.bindView(homeFeedViewMvc)
            SUT.onStart()

            // assert
            coVerify(ordering = Ordering.ORDERED) {
                homeFeedRepository.getAllHomeDataList()
                homeFeedViewMvc.bindHomeDataListToView(homeDataList)

                homeFeedRepository.fetchNewsFromServerAndInsertInLocalDb()

                homeFeedRepository.getAllHomeDataList()
                homeFeedViewMvc.bindHomeDataListToView(homeDataList)
            }
        }

    /////////////////////////////// Methods Region ///////////////////////////////

    private fun getAllHomeDataListReturnsSuccessData() {
        coEvery {
            homeFeedRepository.getAllHomeDataList()
        } returns Response.Success(homeDataList)
    }

    private fun fetchAndInsertTopNewsPerformsSuccessfully() {
        coEvery {
            homeFeedRepository.fetchNewsFromServerAndInsertInLocalDb()
        } returns Response.Success(null)
    }

    @Before
    fun setUp() {
        homeFeedRepository = mockk()
        homeFeedViewMvc = mockk(relaxed = true)

        SUT = HomeFeedController(homeFeedRepository = homeFeedRepository)
    }
}