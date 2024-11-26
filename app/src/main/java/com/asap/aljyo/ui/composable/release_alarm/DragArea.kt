package com.asap.aljyo.ui.composable.release_alarm

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import kotlin.math.max
import kotlin.math.roundToInt

private val arrowWingLength = 8.dp
private val arrowStroke = 4.dp

@Composable
internal fun DragArea(
    modifier: Modifier,
    resourceId: Int,
    navigateToResult: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var draggedOffset by remember { mutableFloatStateOf(0f) }
        val minimumOffset = LocalDensity.current.run { 180.dp.toPx() }

        val blur = animateDpAsState(
            if (draggedOffset == 0f) 30.dp else 0.dp,
            label = "Blur"
        )

        Box(
            modifier = Modifier.size(180.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .blur(blur.value)
                    .padding(30.dp),
                painter = painterResource(resourceId),
                contentDescription = "Fortune image"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragCancel = {
                                draggedOffset = 0f
                            },
                            onDragEnd = {
                                if (draggedOffset >= minimumOffset) {
                                    navigateToResult()
                                    return@detectDragGestures
                                }
                                draggedOffset = 0f
                            }
                        ) { _, dragAmount ->
                            draggedOffset = max(
                                0f,
                                draggedOffset + dragAmount.x
                            )
                        }
                    }
                    .offset {
                        IntOffset(
                            x = draggedOffset.roundToInt(),
                            y = 0
                        )
                    }
                    .border(
                        2.dp,
                        brush = Brush.linearGradient(
                            listOf(White.copy(alpha = 0.4f), White.copy(alpha = 0.0f))
                        ),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(White.copy(alpha = 0.4f), White.copy(alpha = 0.0f))
                        )
                    )

            ) {}
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
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
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
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight(),
            resourceId = R.drawable.img_illust_water_drop,
            navigateToResult = {}
        )
    }
}