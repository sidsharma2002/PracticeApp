package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.data.entities.HomeData

class HomeDataMapper {
    fun getHomeDataFromArticle(article: Article): HomeData {
        return HomeData(
            idKey = 0,
            type = 1,
            id = article.url,
            author = article.author,
            content = article.content,
            description = article.description,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )
    }
}
