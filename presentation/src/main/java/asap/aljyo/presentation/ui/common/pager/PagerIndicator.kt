package asap.aljyo.presentation.ui.common.pager

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import asap.aljyo.presentation.theme.AljyoColor
import asap.aljyo.presentation.theme.AljyoTheme

private const val PagerIndicatorDuration = 300

@Composable
internal fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerCount: Int,
    currentPage: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerCount) { page ->
            IndicatorUnit(isSelected = page == currentPage)
        }
    }
}

@Composable
private fun IndicatorUnit(
    modifier: Modifier = Modifier,
    selectedWidth: Dp = 16.dp,
    unselectedWidth: Dp = 8.dp,
    height: Dp = 8.dp,
    selectedColor: Color = AljyoColor.Black01,
    unselectedColor: Color = AljyoColor.Grey02,
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
            .width(width = width)
            .height(height = height)
            .background(color = color)
    )
}

@Preview(showBackground = true)
@Composable
private fun IndicatorUnitPreview() {
    AljyoTheme {
        Row(
            modifier = Modifier.wrapContentWidth()
        ) {
            IndicatorUnit(isSelected = true)

            IndicatorUnit(isSelected = false)
        }
    }
}