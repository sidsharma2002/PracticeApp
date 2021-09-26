package com.siddharth.practiceapp.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.siddharth.practiceapp.MainActivity
import com.siddharth.practiceapp.R

// Notification ID.
private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0
private const val FLAGS = 0
private const val notificationChannelId = "MainNotificationChannel"

/**
 * Builds and delivers the notification.
 *
 * @param applicationContext, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        notificationChannelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("notification")
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())
}