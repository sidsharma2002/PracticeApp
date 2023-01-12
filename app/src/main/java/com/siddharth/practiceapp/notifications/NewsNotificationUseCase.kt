package com.siddharth.practiceapp.notifications

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.util.sendNotification
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import javax.inject.Inject

interface NewsNotificationUseCase {
    suspend fun showNewsNotification(article: Article)
}

class NewsNotificationUseCaseImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : NewsNotificationUseCase {

    override suspend fun showNewsNotification(article: Article) = withContext(Dispatchers.IO) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        val url = URL(article.urlToImage)

        try {
            val bitmap: Bitmap? = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            notificationManager.sendNotification(
                "Here's your Daily News",
                article.title,
                article.content,
                bitmap,
                applicationContext
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}