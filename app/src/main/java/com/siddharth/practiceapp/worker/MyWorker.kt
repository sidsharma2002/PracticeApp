package com.siddharth.practiceapp.worker


import android.app.NotificationManager
import  com.siddharth.practiceapp.util.sendNotification
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext,workerParams) {

    private val TAG = "MyWorker : "

    override suspend fun doWork(): Result {
        return try {
            uploadToServer()
            Log.d(TAG,"success")
            showNotification()
            Result.success()
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error")
            Result.failure()
        }
    }

    private fun showNotification() {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            "Workmanager fired",
            applicationContext
        )
    }

    private fun uploadToServer() {
        // TODO : Do long running work here
    }
}