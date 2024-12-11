package com.asap.aljyo.core.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.asap.aljyo.R
import com.asap.aljyo.core.components.MainActivity
import com.asap.aljyo.core.components.navigation.ScreenRoute
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NotificationHandler {
    fun showNotification(data: Map<String, String>)
}

class AlarmNotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationHandler {
    private val uri = "aljyo://${ScreenRoute.ReleaseAlarm.route}"

    override fun showNotification(data: Map<String, String>) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val json = gson.toJson(data)

        val deeplinkIntent = Intent(
            Intent.ACTION_VIEW,
            "$uri/${json}".toUri(),
            context, MainActivity::class.java
        )

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deeplinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val notificationId = context.getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(
            context,
            notificationId
        )
            .setSmallIcon(R.drawable.ic_aljo)
            .setContentTitle("${data["title"]}")
            .setContentText("${data["alarm_time"]}")
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            val granted = checkPermission()
            if (granted) {
                notify(0, notificationBuilder.build())
            }
        }
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val check = ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            )

            return check == PackageManager.PERMISSION_GRANTED
        }

        return true
    }
}