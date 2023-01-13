package com.siddharth.practiceapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;
import androidx.work.WorkManager;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    public static final String CHANNEL_ID = "MainNotificationChannel";
    public NotificationManager manager;

    @Inject
    HiltWorkerFactory hiltWorkerFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        initWorker();
    }

    private void initWorker() {
        Configuration configuration = new Configuration.Builder()
                .setWorkerFactory(hiltWorkerFactory).build();
        WorkManager.initialize(this, configuration);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Main Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
