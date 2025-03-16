package com.asap.aljyo.core.notification

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.data.utility.mapToJson
import com.asap.domain.entity.remote.alarm.AlarmPayload
import com.asap.domain.usecase.alarm.GetDeactivatedAlarmListUseCase
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MessageHandler {
    val tag: String

    fun handleMessage(data: Map<String, String>)
}

class AlarmMessageHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,
    private val getDeactivatedAlarmListUseCase: GetDeactivatedAlarmListUseCase
) : MessageHandler {
    override val tag: String
        get() = this::class.simpleName ?: "AlarmMessageHandler"

    private val uri = "aljyo://${ScreenRoute.AlarmOff.route}"

    override fun handleMessage(data: Map<String, String>) {
        Log.d(tag, "$data")
        val groupId = (data["group_id"] ?: "-1").toInt()

        CoroutineScope(Dispatchers.Default).launch {
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

            val alarmPayload = moshi.adapter(AlarmPayload::class.java).fromJson(json)

            withContext(Dispatchers.Main) {
                val deeplinkUri = "$uri/$json"
                val granted = checkPermission()
                if (granted) {
                    context.startForegroundService(
                        Intent(context, AlarmService::class.java).apply {
                            putExtra(AlarmService.ALARM_PAYLOAD, alarmPayload)
                            putExtra(AlarmService.DEEP_LINK_URI, deeplinkUri)
                        }
                    )
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val check = ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            )

            return check == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    companion object {
        const val CHANNEL_ID = 100
    }
}