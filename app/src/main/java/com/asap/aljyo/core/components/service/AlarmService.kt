package com.asap.aljyo.core.components.service

import android.annotation.SuppressLint
import android.app.ForegroundServiceStartNotAllowedException
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.asap.aljyo.R
import com.asap.aljyo.core.components.MainActivity
import com.asap.aljyo.core.notification.AlarmMessageHandler
import com.asap.domain.entity.remote.alarm.AlarmPayload
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmService : Service() {
    private lateinit var player: MediaPlayer

    @SuppressLint("DiscouragedApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val uri = (intent?.extras?.getString(DEEP_LINK_URI) ?: "").toUri()
        Log.d(TAG, "uri: $uri")

        val payload = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.extras?.getParcelable(ALARM_PAYLOAD, AlarmPayload::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.extras?.getParcelable(ALARM_PAYLOAD)
        }

        val music = resources.getIdentifier(
            payload?.musicTitle ?: "alarm01",
            "raw",
            packageName
        )

        player = MediaPlayer.create(this, music).apply {
            val volume = payload?.musicVolume ?: 10f
            isLooping = true
            setVolume(volume, volume)
            start()
        }

        startForeground(uri = uri)
        return START_STICKY
    }

    private fun createNotificationChannel() {
        NotificationChannel(
            getString(R.string.default_notification_channel_id),
            "Aljo alarm",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Default Notification Channel"
        }.also { notificationChannel ->
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
                createNotificationChannel(notificationChannel)
            }
        }
    }

    private fun startForeground(uri: Uri) {
        try {
            val deeplinkIntent = Intent(
                Intent.ACTION_VIEW,
                uri,
                this, MainActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            createNotificationChannel()

            val pendingIntent = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(deeplinkIntent)
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }
            val notificationId = getString(R.string.default_notification_channel_id)
            val notificationBuilder = NotificationCompat.Builder(
                this,
                notificationId
            )
                .setSmallIcon(R.drawable.img_app_icon)
                .setColor(getColor(R.color.ic_launcher_background))
                .setContentTitle(getString(R.string.notification_alarm_title))
                .setContentText(getString(R.string.notification_alarm_description))
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setAutoCancel(false)

            ServiceCompat.startForeground(
                this, AlarmMessageHandler.CHANNEL_ID,
                notificationBuilder.build(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                else 0
            )

        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (e is ForegroundServiceStartNotAllowedException) {
                    Log.e(TAG, "background called $e")
                }
            }

            Log.e(TAG, "$e")
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d(TAG, "onTaskRemoved")
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        player.stop()
        player.release()
        super.onDestroy()
    }

    companion object Key {
        const val TAG = "AlarmService::Component"
        const val ALARM_PAYLOAD = "ALARM_PAYLOAD"
        const val DEEP_LINK_URI = "DEEP_LINK_URI"

        fun stopAlarmService(context: Context) {
            context.stopService(Intent(context, AlarmService::class.java))
        }
    }
}