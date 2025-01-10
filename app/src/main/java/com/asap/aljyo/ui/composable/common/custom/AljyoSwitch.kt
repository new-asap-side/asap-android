package com.asap.aljyo.ui.composable.common.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.White
import kotlin.math.roundToInt

private const val duration = 100

@Composable
fun AljyoSwitch(
    modifier: Modifier,
    checked: Boolean,
    onCheckChanged: () -> Unit,
    indicatorSize: Dp = 24.dp,
    indicatorColor: Color = White,
    shape: Shape = CircleShape
) {
    val color by animateColorAsState(
        targetValue = if (checked) MaterialTheme.colorScheme.primary else Grey03,
        animationSpec = tween(
            durationMillis = duration,
            easing = FastOutSlowInEasing
        ),
        label = "aljyo switch color"
    )

    Box(
        modifier = modifier
            .then(
                Modifier
                    .background(color)
                    .clickable { onCheckChanged() }
            )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterStart)
        ) {
            val density = LocalDensity.current
            val targetValue = with(density) { (maxWidth - indicatorSize).toPx() }
            val transition by animateFloatAsState(
                targetValue = if (checked) targetValue else 0f,
                label = "aljyo switch transition"
            )
            Box(
                modifier = Modifier
                    .offset { IntOffset(transition.roundToInt(), 0) }
                    .size(indicatorSize)
                    .clip(shape)
                    .background(color = indicatorColor)
            )
        }
    }
}

@Preview
@Composable
private fun AljyoSwitch_Preview() {
    AljyoTheme {
        var checked by remember { mutableStateOf(false) }
        AljyoSwitch(
            modifier = Modifier
                .size(52.dp, 28.dp)
                .clip(CircleShape),
            checked = checked,
            onCheckChanged = { checked = !checked }
        )
    }
}