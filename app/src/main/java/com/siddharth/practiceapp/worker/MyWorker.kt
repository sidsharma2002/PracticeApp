package com.siddharth.practiceapp.worker

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.siddharth.practiceapp.App
import com.siddharth.practiceapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext,workerParams) {

    private val TAG = "MyWorker : "

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO){
                uploadToServer()
            }
            Log.d(TAG,"success")
            // showNotification()
            Result.success()
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error applying blur")
            Result.failure()
        }
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(applicationContext, App.CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("started from onCreate")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun uploadToServer() {
        // TODO : Do long running work here
    }
}