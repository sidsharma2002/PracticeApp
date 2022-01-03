package com.siddharth.practiceapp.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.siddharth.practiceapp.MainCoroutineRule
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.getOrAwaitValueTest
import com.siddharth.practiceapp.repository.DefaultHomeFeedRepository
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.Response
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*


@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var viewmodel: HomeViewModel

    @MockK
    lateinit var repository: FakeHomeFeedRepository
    private lateinit var testMainDispatcher: TestCoroutineDispatcher
    private lateinit var testIODispatcher: TestCoroutineDispatcher

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        testMainDispatcher = TestCoroutineDispatcher()
        testIODispatcher = TestCoroutineDispatcher()
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `data is fetched from db in correct sequence`() = runBlockingTest {
        // arrange
        val forFirstPage = true
        coEvery {
            repository.getAndInsertHomeFeed(forFirstPage)
        } returns forFirstPage

        coEvery {
            repository.getAllHomeFeedList()
        } returns Response.Success(mutableListOf(HomeFeed(Constants.HomeFeedNaming.ANIME)))

        // action
        viewmodel = HomeViewModel(repository, testIODispatcher, testMainDispatcher)

        // assert
        coVerifySequence {
            repository.getAllHomeFeedList()
            repository.getAndInsertHomeFeed(forFirstPage)
            repository.getAllHomeFeedList()
        }
    }
}


open class FakeHomeFeedRepository : DefaultHomeFeedRepository {
    override suspend fun getAndInsertHomeFeed(forFirstPage: Boolean): Any {
        return forFirstPage
    }

    override suspend fun getAllHomeFeedList(): Response<MutableList<HomeFeed>> {
        return Response.Success(mutableListOf(HomeFeed(Constants.HomeFeedNaming.ANIME)))
    }
}