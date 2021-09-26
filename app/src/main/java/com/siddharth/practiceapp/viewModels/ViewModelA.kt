package com.siddharth.practiceapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.siddharth.practiceapp.worker.MyWorker
import java.util.concurrent.TimeUnit

class ViewModelA(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)
    private val TAG = "viewmodelA : "

     fun performWork(){
        //workManager.enqueue(OneTimeWorkRequest.from(MyWorker::class.java))
        val saveRequest =
            PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
                .build()
        workManager.enqueue(saveRequest)
    }
}