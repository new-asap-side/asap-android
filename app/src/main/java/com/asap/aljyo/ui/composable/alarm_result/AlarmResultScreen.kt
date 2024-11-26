package com.asap.aljyo.ui.composable.alarm_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    AljyoTheme {
        Scaffold { paddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors))
                    .padding(paddingValues)
            ) {
                FortuneCard(
                    modifier = Modifier.fillMaxWidth(),
                    index = illustIndex,
                    rank = 1
                )

                Spacer(modifier = Modifier.height(20.dp))

                AlarmTitle(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16))
                        .background(Color(0xFFDB7797).copy(alpha = 0.5f))
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    title = "Title"
                )

            }
        }
    }
}