package com.asap.aljyo.ui.composable.release_alarm

import android.app.Activity
import android.graphics.Color
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
internal fun ReleaseAlarmScreen() {
    val context = LocalContext.current
    SideEffect {
        val activity = context as ComponentActivity
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
    }

    AljyoTheme {

    }
}