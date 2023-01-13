package com.siddharth.practiceapp.worker


import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.assisted.AssistedInject

@HiltWorker
class NewsWorker @AssistedInject constructor(
    @dagger.assisted.Assisted appContext: Context,
    @dagger.assisted.Assisted workerParams: WorkerParameters,
    private val controller: NewsWorkerController
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return controller.doWork()
    }

    companion object {
        const val INTERVAL_HOURS = 5L
    }
}