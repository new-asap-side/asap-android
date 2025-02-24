package com.asap.aljyo.ui.composable.alarm_off

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel
import kotlin.math.max

data class Slide(override val id: Int, override val title: String) :
    AlarmContent(id, title, "옆으로 밀어서 알람 해제!") {
    private val radius = RoundedCornerShape(topStart = 50f)
    private val colorStops = arrayOf(
        0.0f to Color(0xFFFFE69B).copy(alpha = 0.8f),
        0.2f to Color(0xFFFFFFFF).copy(alpha = 0.0f),
        0.3f to Color(0xFFFF82BA).copy(alpha = 0.8f),
        0.5f to Color(0xFFFF83BB).copy(alpha = 0.8f),
        0.8f to Color(0xFFFF86BC).copy(alpha = 0.8f),
    )

    @Composable
    override fun Content(viewModel: AlarmOffViewModel) {
        Box(modifier = modifier) {
            var completed by remember { mutableStateOf(false) }
            val minimumOffset = LocalDensity.current.run { 180.dp.toPx() }

            var position by remember { mutableFloatStateOf(0f) }
            val completeAnimation = animateIntAsState(
                if (position >= minimumOffset) (position + 200).toInt() else 0,
                animationSpec = tween(durationMillis = 300),
                label = "drag is completed",
                finishedListener = {
                    completed = true
                }
            )

            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = if (completed)
                    painterResource(R.drawable.img_unlock)
                else
                    painterResource(R.drawable.img_lock),
                contentDescription = "Lock/Unlock image"
            )

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .layout { measurables, constraints ->
                        val placeable = measurables.measure(constraints)
                        val xOffset = (placeable.width * 0.1167) + position
                        val yOffset = (placeable.height * 0.1339).toInt()

                        layout(placeable.width, placeable.height) {
                            placeable.place(xOffset.toInt() + completeAnimation.value, yOffset)
                        }
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragCancel = {
                                position = 0f
                            },
                            onDragEnd = {
                                if (position >= minimumOffset) {
                                    return@detectDragGestures
                                }
                                position = 0f
                            }
                        ) { _, dragAmount ->
                            position = max(0f, position + dragAmount.x)
                        }
                    }
                    .clip(radius)
                    .border(
                        width = 1.dp,
                        shape = radius,
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFFFDDE8),
                                Color(0xFFFFFFFF).copy(alpha = 0.1f)
                            )
                        ),
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colorStops = colorStops,
                            start = Offset(Float.POSITIVE_INFINITY, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY),
                        )
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFA9C4).copy(alpha = 0.4f),
                ),
            ) {

            }
        }
    }
}
