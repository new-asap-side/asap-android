package com.asap.aljyo.components.group_details

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.asap.aljyo.components.ExtrasKey
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.domain.entity.remote.AlarmGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailsActivity : ComponentActivity() {
    private val alarmGroupItem: AlarmGroup? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(ExtrasKey.ALARM_GROUP_KEY, AlarmGroup::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(ExtrasKey.ALARM_GROUP_KEY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("GroupDetailsActivity", "$alarmGroupItem")
        setContent {
            AljyoTheme {

            }
        }
    }
}