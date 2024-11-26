package com.asap.aljyo.ui.composable.alarm_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme

private val colors = listOf(
    Color(0xFFFFEDBE),
    Color(0xFFFFB3B6),
    Color(0xFFFF98C7),
    Color(0xFFFF98C7),
)

@Composable
internal fun AlarmResultScreen(
    illustIndex: Int
) {
    LaunchedEffect(illustIndex) {

    }

    AljyoTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors))
                    .padding(paddingValues)
            ) {

                FortuneCard(
                    modifier = Modifier.fillMaxWidth(),
                    index = illustIndex
                )
            }
        }
    }
}