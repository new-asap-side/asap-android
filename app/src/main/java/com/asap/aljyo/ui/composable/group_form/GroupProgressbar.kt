package com.asap.aljyo.ui.composable.group_form

import android.widget.ProgressBar
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Red01

@Composable
fun GroupProgressbar(
    modifier: Modifier = Modifier,
    startProgress: Float,
    endProgress: Float
) {
    val strokeWidth = 8f
    val height = with(LocalDensity.current) { strokeWidth.toDp() }
    var targetValue by remember { mutableFloatStateOf(startProgress) }
    val animatedProgress by animateFloatAsState(
        targetValue = targetValue,
        label = "",
        animationSpec = tween(
            durationMillis = 600,
        )
    )

    LaunchedEffect(Unit) {
        targetValue = endProgress
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val width = size.width

        drawLine(
            color = Grey01,
            start = Offset(0f, 0f),
            end = Offset(width, 0f),
            strokeWidth = 8f,
            cap = StrokeCap.Butt
        )

        drawLine(
            color = Red01,
            start = Offset(0f, 0f),
            end = Offset(width * startProgress, 0f),
            strokeWidth = 8f,
            cap = StrokeCap.Butt
        )

        drawLine(
            color = Red01,
            start = Offset(width * startProgress, 0f),
            end = Offset(width * animatedProgress, 0f),
            strokeWidth = 8f,
            cap = StrokeCap.Butt
        )
    }
}


@Composable
@Preview
fun PreviewGroupProgressbar() {
    AljyoTheme {
        GroupProgressbar(
            startProgress = 0.3f,
            endProgress = 0.6f
        )
    }
}