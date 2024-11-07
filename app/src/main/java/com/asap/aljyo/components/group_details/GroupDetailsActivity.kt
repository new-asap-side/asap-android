package com.asap.aljyo.components.group_details

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.asap.aljyo.components.ExtrasKey
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
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
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.copy(alpha = 0f).toArgb(),
                White.toArgb()
            )
        )
        setContent {
            AljyoTheme {
                Scaffold { padding ->
                    Surface(
                        modifier = Modifier
                            .padding(bottom = padding.calculateBottomPadding())
                            .fillMaxSize(),
                    ) {
                    }
                }
            }
        }
    }
}