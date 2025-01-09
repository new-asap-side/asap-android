package com.asap.aljyo.core.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.asap.aljyo.R
import com.asap.aljyo.core.components.MainActivity
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.data.utility.mapToJson
import com.asap.domain.usecase.alarm.GetDeactivatedAlarmListUseCase
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NotificationHandler {
    val tag: String?

    fun showNotification(data: Map<String, String>)
}

class AlarmNotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,
    private val getDeactivatedAlarmListUseCase: GetDeactivatedAlarmListUseCase
) : NotificationHandler {
    override val tag: String?
        get() = this::class.simpleName

    private val uri = "aljyo://${ScreenRoute.ReleaseAlarm.route}"

    override fun showNotification(data: Map<String, String>) {
        val groupId = (data["group_id"] ?: "-1").toInt()

        CoroutineScope(Dispatchers.IO).launch {
            val deactivated = getDeactivatedAlarmListUseCase()
            val isDeactivated = deactivated.map { it.groupId }.contains(groupId)
            if (isDeactivated) {
                Log.d(tag, "비활성화 알람입니다.")
                return@launch
            }

            val json = moshi.mapToJson(
                key = String::class.java,
                value = Any::class.java,
                raw = data
            )

            withContext(Dispatchers.Main) {
                val deeplinkIntent = Intent(
                    Intent.ACTION_VIEW,
                    "$uri/${json}".toUri(),
                    context, MainActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(deeplinkIntent)
                    getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
                }

                val notificationId = context.getString(R.string.default_notification_channel_id)
                val notificationBuilder = NotificationCompat.Builder(
                    context,
                    notificationId
                )
                    .setSmallIcon(R.drawable.img_app_icon)
                    .setColor(context.getColor(R.color.ic_launcher_background))
                    .setContentTitle(context.getString(R.string.notification_alarm_title))
                    .setContentText(context.getString(R.string.notification_alarm_description))
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