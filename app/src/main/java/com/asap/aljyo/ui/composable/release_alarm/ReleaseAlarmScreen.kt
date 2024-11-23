package com.asap.aljyo.ui.composable.release_alarm

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.asap.aljyo.ui.theme.AljyoTheme
import androidx.compose.ui.graphics.Color as compose

private val colors = listOf(
    compose(0xFFFFDF8F),
    compose(0xFFFF999E),
    compose(0xFFFF5FA9),
    compose(0xFFFF5FA9),
)

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

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    AljyoTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DragArea(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }

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
            .background(brush = Brush.linearGradient(colors))
    )
}