package com.siddharth.practiceapp.worker


import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters


import com.siddharth.practiceapp.data.dto.News.Article

import com.siddharth.practiceapp.repository.HomeFeedRepository
import com.siddharth.practiceapp.util.sendNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class NewsWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val TAG = "MyWorker : "
    @Inject lateinit var controller : NewsWorkerController

    override suspend fun doWork(): Result {
        return controller.doWork()
    }
}