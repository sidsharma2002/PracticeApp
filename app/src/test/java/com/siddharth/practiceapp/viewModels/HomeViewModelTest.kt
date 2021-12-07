package com.siddharth.practiceapp.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.siddharth.practiceapp.MainCoroutineRule
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.getOrAwaitValueTest
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit


class HomeViewModelTest {
    private lateinit var viewmodel: HomeViewModel
    private lateinit var repository: DefaultHomeFeedRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = FakeHomeFeedRepository()
        viewmodel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `database should be single source of truth`() {
        val result = viewmodel.homeFeedList.getOrAwaitValueTest()
        assertThat(result.data).hasSize(1)
        assertThat(result.data?.get(0)).isEqualTo(HomeFeed(Constants.HomeFeedNaming.ANIME))
    }
}

class FakeHomeFeedRepository : DefaultHomeFeedRepository {
    override suspend fun getAndInsertHomeFeed(forFirstPage: Boolean): Any {
        return forFirstPage
    }

    override suspend fun getAllHomeFeedList(): Response<MutableList<HomeFeed>> {
        return Response.Success(mutableListOf(HomeFeed(Constants.HomeFeedNaming.ANIME)))
    }
}