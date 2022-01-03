package com.siddharth.practiceapp.repository


import com.google.common.truth.Truth.assertThat
import com.siddharth.practiceapp.api.HomeFeedApi
import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.data.dto.feed.Feed
import com.siddharth.practiceapp.data.dto.feed.HomeFeedDto
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeFeedRepositoryTest {

    private lateinit var homeFeedRepository: HomeFeedRepository
    private lateinit var dispatcherIO: TestCoroutineDispatcher

    @MockK(relaxUnitFun = true, relaxed = true)
    private lateinit var apiService: HomeFeedApi

    @MockK(relaxUnitFun = true, relaxed = true)
    private lateinit var feed: Feed

    @MockK(relaxUnitFun = true, relaxed = true)
    private lateinit var dao: HomeFeedDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dispatcherIO = TestCoroutineDispatcher()
        homeFeedRepository = HomeFeedRepository(apiService, dao, dispatcherIO)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getAllHomeFeedList returns correct value`() = dispatcherIO.runBlockingTest {
        // arrange
        coEvery {
            dao.getAllHomeFeedList()
        } returns mutableListOf(
            HomeFeed(dataType = Constants.HomeFeedNaming.MARVEL),
            HomeFeed(dataType = Constants.HomeFeedNaming.ANIME)
        )
        // action
        val result = homeFeedRepository.getAllHomeFeedList()
        dispatcherIO.advanceTimeBy(1000)
        // assert
        assertThat(result.data?.size).isEqualTo(2)
    }
}