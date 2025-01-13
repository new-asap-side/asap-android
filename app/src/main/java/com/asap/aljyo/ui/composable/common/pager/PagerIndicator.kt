package com.asap.aljyo.ui.composable.common.pager

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey02

const val PagerIndicatorDuration = 300

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    selectedWidth: Dp = 16.dp,
    unselectedWidth: Dp = 8.dp,
    height: Dp = 8.dp,
    selectedColor: Color = Black01,
    unselectedColor: Color = Grey02,
    isSelected: Boolean,
    duration: Int = PagerIndicatorDuration,
) {
    val color: Color by animateColorAsState(
        targetValue = if (isSelected) selectedColor else unselectedColor,
        animationSpec = tween(durationMillis = duration),
        label = "indicator color animation"
    )

    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) selectedWidth else unselectedWidth,
        animationSpec = tween(durationMillis = duration),
        label = "indicator width animation"
    )

    Box(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .clip(CircleShape)
            .size(width = width, height = height)
            .background(color = color)
    )
}