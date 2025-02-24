package com.asap.aljyo.ui.composable.alarm_off

import android.widget.Toast
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.White
import kotlin.math.max

data class Slide(
    override val id: Int,
    override val title: String
) : AlarmContent(id, title, "옆으로 밀어서 알람 해제!"),
    AlarmOffViewModelProvider<AlarmOffViewModel> by GeneralProvider() {
    private val radius = RoundedCornerShape(topStart = 50f)
    private val colorStops = arrayOf(
        0.0f to Color(0xFFFFE69B).copy(alpha = 0.8f),
        0.2f to Color(0xFFFFFFFF).copy(alpha = 0.0f),
        0.3f to Color(0xFFFF82BA).copy(alpha = 0.8f),
        0.5f to Color(0xFFFF83BB).copy(alpha = 0.8f),
        0.8f to Color(0xFFFF86BC).copy(alpha = 0.8f),
    )

    @Composable
    override fun Effect(navigateToResult: () -> Unit) {
        val viewModel = provide()
        val context = LocalContext.current
        val requestState by viewModel.state.collectAsState()

        LaunchedEffect(requestState) {
            when (requestState) {
                RequestState.Initial, RequestState.Loading -> Unit

                is RequestState.Success -> {
                    val result = (requestState as RequestState.Success).data
                    if (result) {
                        // 알람 서비스 종료
                        AlarmService.stopAlarmService(context)
                        navigateToResult()
                    }
                }

                is RequestState.Error -> {
                    Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Composable
    override fun Title(title: String) {
        val viewModel = provide()
        val time = viewModel.currentTime

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = time,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 86.fsp,
                color = White
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(White.copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "clock",
                tint = White
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.fsp,
                    color = White
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.fsp,
                color = White
            ),
            textAlign = TextAlign.Center
        )

    }

    @Composable
    override fun Content() {
        val viewModel = provide()

        Box(modifier = modifier) {
            var completed by remember { mutableStateOf(false) }
            var position by remember { mutableFloatStateOf(0f) }

            val minimumOffset = LocalDensity.current.run { 180.dp.toPx() }
            val completeAnimation = animateIntAsState(
                if (position >= minimumOffset) (position + 200).toInt() else 0,
                animationSpec = tween(durationMillis = 300),
                label = "drag completed",
                finishedListener = {
                    completed = true
                    viewModel.alarmOff(id)
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
