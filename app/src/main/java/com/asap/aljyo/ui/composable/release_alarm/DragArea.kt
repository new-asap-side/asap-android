package com.asap.aljyo.ui.composable.release_alarm

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

private val arrowWingLength = 8.dp
private val arrowStroke = 4.dp

@Composable
internal fun DragArea(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var draggedOffset by remember { mutableFloatStateOf(0f) }
        var dragAreaWidth by remember { mutableFloatStateOf(0f) }

        val density = LocalDensity.current
        val initialBlurRadius = density.run { 20.dp.toPx() }

        val scaledValue = if (dragAreaWidth == 0f) 0f
            else (draggedOffset / dragAreaWidth) * initialBlurRadius

        val blurRadius: Float by animateFloatAsState(
            (initialBlurRadius - scaledValue),
            label = "blur"
        )

        val arrowAlpha: Float by animateFloatAsState(
            if (draggedOffset == 0f) 1.0f else 0.0f,
            animationSpec = tween(100, easing = FastOutSlowInEasing),
            label = "Arrow alph"
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .border(
                    2.dp,
                    brush = Brush.linearGradient(
                        listOf(White.copy(alpha = 0.4f), White.copy(alpha = 0.0f))
                    ),
                    shape = CircleShape
                )
                .blur(density.run { blurRadius.toDp() })
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.img_water_drop),
                contentDescription = "Fortune image"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(
                            maxWidth = constraints.maxWidth / 3,
                            maxHeight = 42.dp
                                .toPx()
                                .toInt()
                        )
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                }
                .onGloballyPositioned { coordinates ->
                    dragAreaWidth = coordinates.size.width.toFloat()
                }
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
                    .alpha(arrowAlpha)
            ) {
                drawLine(
                    color = White,
                    start = Offset(
                        x = arrowStroke.toPx() / 2 + 30.dp.toPx(),
                        y = arrowStroke.toPx() / 2 + arrowWingLength.toPx()
                    ),
                    end = Offset(
                        x = size.width - arrowStroke.toPx() / 2,
                        y = arrowStroke.toPx() / 2 + arrowWingLength.toPx()
                    ),
                    strokeWidth = arrowStroke.toPx(),
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = White,
                    start = Offset(
                        x = size.width - arrowWingLength.toPx(),
                        y = arrowStroke.toPx() / 2
                    ),
                    end = Offset(
                        x = size.width - arrowStroke.toPx() / 2,
                        y = arrowStroke.toPx() / 2 + arrowWingLength.toPx()
                    ),
                    strokeWidth = arrowStroke.toPx(),
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = White,
                    start = Offset(
                        x = size.width - arrowWingLength.toPx(),
                        y = arrowWingLength.toPx() * 2 + arrowStroke.toPx() / 2
                    ),
                    end = Offset(
                        x = size.width - arrowStroke.toPx() / 2,
                        y = arrowStroke.toPx() / 2 + arrowWingLength.toPx()
                    ),
                    strokeWidth = arrowStroke.toPx(),
                    cap = StrokeCap.Round
                )
            }
            Icon(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { _, offset ->
                                draggedOffset = min(
                                    dragAreaWidth, max(0f, draggedOffset + offset.x)
                                )
                            },
                            onDragCancel = { draggedOffset = 0f },
                            onDragEnd = { draggedOffset = 0f },
                        )
                    }
                    .offset {
                        IntOffset(
                            x = draggedOffset.roundToInt(),
                            y = 0
                        )
                    },
                tint = Color.Unspecified,
                painter = painterResource(R.drawable.ic_hand),
                contentDescription = "Drag icon",
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        DragArea(
            modifier = Modifier.size(108.dp, 42.dp)
        )
    }
}