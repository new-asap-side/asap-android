package com.asap.aljyo.core.notification

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.asap.aljyo.R
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NotificationHandler {
    fun showNotification(data: Map<String, String>)
}

class AlarmNotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) : NotificationHandler {
    override fun showNotification(data: Map<String, String>) {
        Log.d("Notification Handler", "notification handler moshi - $moshi")

        val notificationId = context.getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(
            context,
            notificationId
        )
            .setSmallIcon(R.drawable.ic_aljo)
            .setContentTitle("Hi")
            .setContentText("aljo alarm !")
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
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