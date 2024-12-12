package com.asap.aljyo.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.asap.aljyo.R

fun createNotificationChannel(context: Context) {
    NotificationChannel(
        context.getString(R.string.default_notification_channel_id),
        "Aljo alarm",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "Default Notification Channel"
    }.also { notificationChannel ->
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
            createNotificationChannel(notificationChannel)
        }
    }
}