package com.siddharth.practiceapp.data.entities.News

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)