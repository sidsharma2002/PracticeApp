package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.api.NewsApi
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.data.dto.News.News
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.ErrorHandler
import com.siddharth.practiceapp.util.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class HomeFeedRepositoryTest {

    private lateinit var newsApi: NewsApi
    private lateinit var homeDataMapper: HomeDataMapper
    private lateinit var homeDataDao: HomeDataDao
    private lateinit var errorHandler: ErrorHandler

    private lateinit var SUT: HomeFeedRepository

    @Test
    fun fetchNewsFromServerAndInsertInLocalDb_hitsNewsApi() = runBlocking {
        // arrange
        coEvery {
            newsApi.getTopNews(Constants.NEWS_API_KEY)
        } returns retrofit2.Response.success(null)

        // act
        SUT.fetchNewsFromServerAndInsertInLocalDb()

        // assert
        coVerify {
            newsApi.getTopNews(Constants.NEWS_API_KEY)
        }
    }

    @Test
    fun newsApiThrowsException_fetchNewsFromServerAndInsertInLocalDb_returnsError() = runBlocking {
        // arrange
        coEvery {
            newsApi.getTopNews(any(), any())
        } throws Exception()

        // act
        val result = SUT.fetchNewsFromServerAndInsertInLocalDb()

        // assert
        assert(result is Response.Error)
    }

    @Test
    fun newsApiReturnsErrorCode_fetchNewsFromServerAndInsertInLocalDb_returnsError() = runBlocking {
        // arrange
        coEvery {
            newsApi.getTopNews(any(), any())
        } returns retrofit2.Response.error(500, ResponseBody.create(null, ""))

        // act
        val result = SUT.fetchNewsFromServerAndInsertInLocalDb()

        // assert
        assert(result is Response.Error)
    }

    @Test
    fun newsApiReturnsSuccessButDataIsNull_fetchNewsFromServerAndInsertInLocalDb_returnsError() =
        runBlocking {
            // arrange
            coEvery {
                newsApi.getTopNews(any(), any())
            } returns retrofit2.Response.success(/* body = */ null)

            // act
            val result = SUT.fetchNewsFromServerAndInsertInLocalDb()

            // assert
            assert(result is Response.Error)
        }

    @Test
    fun newsApiReturnsSuccess_fetchNewsFromServerAndInsertInLocalDb_parsesArticlesToHomeDataList() =
        runBlocking {
            // arrange
            val news = getNews()
            coEvery {
                newsApi.getTopNews(Constants.NEWS_API_KEY)
            } returns retrofit2.Response.success(news)

            // act
            SUT.fetchNewsFromServerAndInsertInLocalDb()

            // assert
            news.articles.forEach { article ->
                coVerify(exactly = 1) {
                    homeDataMapper.getHomeDataFromArticle(article)
                }
            }
        }

    @Test
    fun newsApiReturnsSuccess_fetchNewsFromServerAndInsertInLocalDb_performsDeleteInsertTransactionOnParsedData() =
        runBlocking {
            // arrange
            val news = getNews()
            coEvery {
                newsApi.getTopNews(Constants.NEWS_API_KEY)
            } returns retrofit2.Response.success(news)

            // act
            SUT.fetchNewsFromServerAndInsertInLocalDb()

            // assert
            val list = mutableListOf<HomeData>()
            news.articles.forEach { article ->
                list.add(homeDataMapper.getHomeDataFromArticle(article))
            }

            coVerify {
                homeDataDao.deleteAndInsertTransaction(list)
            }
        }

    @Test
    fun newsApiReturnsSuccess_daoThrowsException_fetchNewsFromServerAndInsertInLocalDb_returnsError() =
        runBlocking {
            // arrange
            val news = getNews()
            coEvery {
                newsApi.getTopNews(Constants.NEWS_API_KEY)
            } returns retrofit2.Response.success(news)

            coEvery {
                homeDataDao.deleteAndInsertTransaction(any())
            } throws Exception()

            // act
            val result = SUT.fetchNewsFromServerAndInsertInLocalDb()

            // assert
            assert(result is Response.Error)
        }

    @Test
    fun everyServiceReturnsSuccess_fetchNewsFromServerAndInsertInLocalDb_returnsSuccess() =
        runBlocking {
            // arrange
            val news = getNews()
            coEvery {
                newsApi.getTopNews(Constants.NEWS_API_KEY)
            } returns retrofit2.Response.success(news)

            // act
            val result = SUT.fetchNewsFromServerAndInsertInLocalDb()

            // assert
            assert(result is Response.Success)
        }

    @Test
    fun newsApiReturnsEmptyListOfArticles_fetchNewsFromServerAndInsertInLocalDb_doesNotPerformDeleteInsertTransaction() =
        runBlocking {
            // arrange
            val news = getNews(size = 0)
            coEvery {
                newsApi.getTopNews(Constants.NEWS_API_KEY)
            } returns retrofit2.Response.success(news)

            // act
            val result = SUT.fetchNewsFromServerAndInsertInLocalDb()

            // assert
            coVerify(exactly = 0) {
                homeDataDao.deleteAndInsertTransaction(any())
            }
        }

    @Test
    fun daoReturnsHomeDataList_getAllHomeDataList_returnsTheSameList() = runBlocking {
        // arrange
        val homeDataList = getHomeDataList()
        coEvery {
            homeDataDao.getAllHomeDataList()
        } returns homeDataList

        // act
        val result = SUT.getAllHomeDataList()

        // assert
        assert(result is Response.Success)
        assert(result.data == homeDataList)
    }

    @Test
    fun homeDaoThrowsException_getAllHomeDataList_returnsError() = runBlocking {
        // arrange
        coEvery {
            homeDataDao.getAllHomeDataList()
        } throws Exception("")

        // act
        val result = SUT.getAllHomeDataList()

        // assert
        assert(result is Response.Error)
    }


    private fun getHomeDataList(): List<HomeData> {
        return List(5) {
            HomeData(0, it)
        }
    }


    private fun getNews(size: Int = 5): News {
        return News(
            List(size) {
                Article("author $it", "content", "description", "title", "url", "urlToImage")
            }, size
        )
    }

    @Before
    fun setUp() {
        newsApi = mockk()
        homeDataDao = mockk(relaxed = true)
        homeDataMapper = mockk(relaxed = true)
        errorHandler = ErrorHandler()

        SUT = HomeFeedRepository(
            newsApi = newsApi,
            homeDataDao = homeDataDao,
            homeDataMapper = homeDataMapper,
            errorHandler = errorHandler
        )
    }
}