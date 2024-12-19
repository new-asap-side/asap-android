package com.asap.aljyo.ui.composable.common.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.asap.aljyo.ui.theme.ShimmerBase
import com.asap.aljyo.ui.theme.White


private val shimmerColors = listOf(
    White.copy(alpha = 0.3f),
    White.copy(alpha = 0.5f),
    White.copy(alpha = 1.0f),
    White.copy(alpha = 0.5f),
    White.copy(alpha = 0.3f)
)

@Composable
internal fun ShimmerBox(
    modifier: Modifier,
    baseColor: Color = ShimmerBase,
    duration: Int = 1500,
) {
    val transition = rememberInfiniteTransition(label = "shimmer transition")
    val transitionAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, 200),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer transition animation"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = transitionAnim, y = transitionAnim)
    )

    Box(modifier = modifier.background(baseColor)) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        )
    }
}