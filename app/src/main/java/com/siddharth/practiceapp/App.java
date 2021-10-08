package com.siddharth.practiceapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    public static final String CHANNEL_ID = "MainNotificationChannel";
    public NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    public void createNotificationChannel() {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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
