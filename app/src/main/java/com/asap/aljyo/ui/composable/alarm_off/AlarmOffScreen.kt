package com.asap.aljyo.ui.composable.alarm_off

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.domain.entity.remote.alarm.AlarmPayload
import com.asap.domain.entity.remote.alarm.AlarmUnlockContent
import androidx.compose.ui.graphics.Color as compose

private val gradient = listOf(
    compose(0xFFFFDF8F),
    compose(0xFFFF999E),
    compose(0xFFFF5FA9),
    compose(0xFFFF5FA9),
)

@Composable
fun AlarmOffScreen(
    alarm: AlarmPayload,
    navigateToResult: () -> Unit,
) {
    val context = LocalContext.current
    SideEffect {
        val activity = context as ComponentActivity
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    AljyoTheme {
        Scaffold { paddingValues ->
            val (groupId, groupTitle) = alarm
            val alarmContent: AlarmContent = when (alarm.content) {
                AlarmUnlockContent.Slide -> Slide(groupId, groupTitle)

                AlarmUnlockContent.Card -> Card(groupId, groupTitle)

                AlarmUnlockContent.Calculator -> Calculator(groupId, groupTitle)
            }

            BackHandler {
                val activity = context as ComponentActivity
                activity.finish()
            }

            alarmContent.Effect(navigateToResult = navigateToResult)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(gradient))
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                alarmContent.Title(groupTitle)

                alarmContent.Content()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(gradient))
    )
}