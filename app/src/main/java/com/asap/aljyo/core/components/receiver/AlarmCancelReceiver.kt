package com.asap.aljyo.core.components.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.asap.aljyo.core.components.service.AlarmService

const val STOP_ALJYO_ALARM = "com.asap.aljyo.STOP_ALARM"

class AlarmCancelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive - context: $context intent: $intent")
        val action = intent?.action ?: ""
        when (action) {
            STOP_ALJYO_ALARM -> {
                context?.stopService(Intent(context, AlarmService::class.java))
            }
            else -> Unit
        }
    }

    companion object {
        const val TAG = "AlarmCancelReceiver"
    }
}