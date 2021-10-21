package com.siddharth.practiceapp.data.converter

import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.siddharth.practiceapp.data.dto.News.Article
import java.lang.reflect.Type


open class ArticleConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToArticle(articleEncoded: String): Article {
        Log.d("ArticleConverter",articleEncoded)
        val x = articleEncoded.split('$').map { it }
        Log.d("ArticleConverted",x.toString())
        return Article(x[0], x[1], x[2], x[3], x[4], x[5])
    }

    @TypeConverter
    fun articleToString(article: Article): String {
        val x =
            article.author + "$" + article.content + "$" + article.description + "$" + article.title + "$" + article.url + "$" + article.urlToImage
        Log.d("ArticleConvertedToString",x)
        return x
    }

}