package com.siddharth.practiceapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "foregroundServiceChannel";
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
                        "My Foreground Service Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                        );
                manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(serviceChannel);
            }
    }
}
