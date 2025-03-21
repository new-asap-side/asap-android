package com.asap.aljyo.ui.composable.common.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.shape.SpeechBubble
import kotlin.math.roundToInt

@Composable
fun BubbleBox(
    modifier: Modifier,
    radius: Float = 100f,
    containerColor: Color,
    tailHeight: Float,
    content: @Composable (Modifier) -> Unit
) {
    Box(
        modifier = modifier
            .clip(
                SpeechBubble(
                    bodyRadius = radius,
                    tailSize = tailHeight
                )
            )
            .background(containerColor)
            .padding(horizontal = 10.dp)
    ) {
        content(
            Modifier.layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth,
                        maxHeight = constraints.maxHeight - tailHeight.roundToInt()
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
        )
    }
}