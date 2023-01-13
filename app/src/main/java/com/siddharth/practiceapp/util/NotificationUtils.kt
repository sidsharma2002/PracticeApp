package com.siddharth.practiceapp.util

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
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
fun NotificationManager.sendNotification(
    title: String,
    contentText: String,
    messageBody: String,
    bitmap: Bitmap?,
    applicationContext: Context
) {
    // Build and show the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        notificationChannelId
    )
        .setSmallIcon(R.drawable.ic_baseline_email_24)
        .setContentTitle(title)
        .setContentText(contentText)
        .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
        .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())
}