package com.siddharth.practiceapp.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.siddharth.practiceapp.R;
import com.siddharth.practiceapp.broadcastReceiver.MyBroadcastReceiver;

import static com.siddharth.practiceapp.App.CHANNEL_ID;

/**
 * This foreground service helps in running endless service
 * and listen to broadcasts
 */
public class MyForegroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerBroadcastReceiver();
        startForeground(1,getNotification());
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("started from onCreate")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    private void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
