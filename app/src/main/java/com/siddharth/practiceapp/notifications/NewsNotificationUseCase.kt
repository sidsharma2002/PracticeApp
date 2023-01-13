package com.siddharth.practiceapp.notifications

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import com.siddharth.practiceapp.bitmapModifiers.BitmapModifier
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
    @ApplicationContext private val applicationContext: Context,
    private val bitmapModifier: BitmapModifier
) : NewsNotificationUseCase {

    override suspend fun showNewsNotification(article: Article) = withContext(Dispatchers.IO) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        val url = URL(article.urlToImage)

        try {
            val bitmap: Bitmap? = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val copyBitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)

            copyBitmap?.let {
                bitmapModifier.applyFilter(copyBitmap, BitmapModifier.GREY_FILTER)
            }

            notificationManager.sendNotification(
                title = article.title,
                contentText = article.description,
                messageBody = article.content,
                bitmap = copyBitmap,
                applicationContext = applicationContext
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}