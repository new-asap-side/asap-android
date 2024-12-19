package com.asap.aljyo.ui.composable.common.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.White

private val defaultButtonSize = 24.dp
private const val duration = 100

@Composable
internal fun AljyoToggleButton(
    selected: Boolean,
    onSelect: (Boolean) -> Unit,
    size: Dp = defaultButtonSize,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = White,
    borderColor: Color = Grey03
) {
    val color by animateColorAsState(
        targetValue = if (selected) selectedColor else unselectedColor,
        animationSpec = tween(
            durationMillis = duration,
            easing = FastOutSlowInEasing
        ),
        label = "Animate color"
    )

    Canvas(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onSelect(selected) }
    ) {
        if (!selected) {
            drawCircle(
                color = borderColor,
                radius = this.size.minDimension / 2
            )
            drawCircle(
                color = color,
                radius = this.size.minDimension / 2 - 3
            )
        } else {
            drawCircle(
                color = color,
                radius = this.size.minDimension / 2
            )
            drawCircle(
                color = unselectedColor,
                radius = this.size.minDimension / 4
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        var selected = false
        AljyoToggleButton(
            size = 24.dp,
            selected = selected,
            onSelect = { selected = !selected }
        )
    }
}