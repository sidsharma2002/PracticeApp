package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.data.entities.HomeData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HomeDataMapperTest {

    private lateinit var SUT: HomeDataMapper

    @Test
    fun convertsArticleToHomeDataCorrectly() {
        // arrange
        val article = Article(
            author = "author",
            content = "content",
            description = "desc",
            title = "title",
            url = "url",
            urlToImage = "urlToImage"
        )

        // act
        val mappedHomeData = SUT.getHomeDataFromArticle(article)

        // assert
        assert(mappedHomeData.author == article.author)
        assert(mappedHomeData.content == article.content)
        assert(mappedHomeData.description == article.description)
        assert(mappedHomeData.title == article.title)
        assert(mappedHomeData.url == article.url)
        assert(mappedHomeData.urlToImage == article.urlToImage)
    }

    @Test
    fun convertsHomeDataToArticleCorrectly() {
        // arrange
        val homeData = HomeData(
            idKey = 0L,
            type = 1,
            id = "id",
            author = "author",
            content = "content",
            description = "desc",
            title = "title",
            url = "url",
            urlToImage = "urlToImage",
            extraString = "extraString"
        )

        // act
        val mappedArticle = SUT.getArticleFromHomeData(homeData)

        // assert
        assert(mappedArticle.author == homeData.author)
        assert(mappedArticle.url == homeData.url)
        assert(mappedArticle.description == homeData.description)
        assert(mappedArticle.content == homeData.content)
        assert(mappedArticle.title == homeData.title)
        assert(mappedArticle.urlToImage == homeData.urlToImage)
    }

    @Before
    fun setup() {
        SUT = HomeDataMapper()
    }
}