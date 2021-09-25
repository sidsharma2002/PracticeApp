package com.siddharth.practiceapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Used to perform heavy long running operation in background
 */
class MyService : Service() {
    private val TAG = "MyService, "

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"service has been created")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     *  To perform heavy operations we need to create a separate thread
     *  IntentService automatically handles this issue but it gets destroyed
     *  as soon as the work is completed.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dataString  = intent?.getStringExtra("DATA_STRING")
        dataString?.let{
                Log.d(TAG,"the data string is : $dataString")
        }
        performHeavyTask()
        /**
         * START_STICKY means when the service will be
         * recreated but the intent will not be redelivered, Hence
         * will be null. To redeliver the intent we must use START_REDELIVER
         */
        return START_STICKY
    }

    private fun performHeavyTask() {
        Thread{
            Log.d(TAG,"performing heavy task")
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"service has been destroyed")
    }
}