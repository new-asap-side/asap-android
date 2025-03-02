package com.asap.aljyo.ui.composable.alarm_off

import android.widget.Toast
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.components.viewmodel.CalculatorViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.shape.TailArrangement
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.White

data class Calculator(
    override val id: Int, override val title: String
) : AlarmContent(id, title, "계산기의 답을 맞추면\n알람 해제!"),
    AlarmOffViewModelProvider<CalculatorViewModel> by CalculatorViewModelProvider() {
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
                lineHeight = 32.fsp,
                color = White
            ),
            textAlign = TextAlign.Center
        )

    }

    @Composable
    override fun Content() {
        val viewModel = provide()
        val enable by viewModel.enable.collectAsState()
        val selectedIndex by viewModel.selectedIndex.collectAsState()
        val operation by viewModel.operation.collectAsState()

        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .layout { measurable, constraint ->
                        val placeable = measurable.measure(
                            Constraints(
                                maxWidth = (constraint.maxWidth * 0.7222).toInt(),
                            )
                        )

                        layout(placeable.width, placeable.height) {
                            placeable.place(
                                0,
                                (constraint.maxHeight * 0.1096).toInt()
                            )
                        }
                    }
                    .offset(y = 50.dp)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_calculator),
                        contentDescription = "calculator",
                        contentScale = ContentScale.FillWidth
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 40.dp),
                ) {
                    Text(
                        text = "${operation.expression}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 38.fsp,
                            color = White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Box(
                modifier = Modifier
                    .layout { measurable, constraint ->
                        val placeable = measurable.measure(constraint)
                        val y = constraint.maxHeight * 0.5482
                        layout(constraint.maxWidth, placeable.height) {
                            placeable.place(0, y.toInt())
                        }
                    }
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    operation.choice.forEachIndexed { index, number ->
                        val iconSize by animateDpAsState(
                            targetValue = if (index == selectedIndex) 66.dp else 0.dp,
                            animationSpec = tween(200, easing = EaseOutBounce),
                            label = "choice effect"
                        )

                        TextButton(
                            modifier = Modifier
                                .size(90.dp)
                                .dropShadow(
                                    shape = RoundedCornerShape(24.dp),
                                    blur = 12.dp,
                                    offsetX = 0.dp, offsetY = 0.dp,
                                    color = Color(0xFFFFD3E4)
                                ),
                            shape = RoundedCornerShape(24.dp),
                            onClick = { viewModel.emit(id, index, number) },
                            enabled = enable,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                disabledContainerColor = White,
                                contentColor = Black01,
                                disabledContentColor = Black04
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(iconSize),
                                    painter = painterResource(R.drawable.ic_wrong),
                                    tint = Color.Unspecified,
                                    contentDescription = "effect"
                                )

                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "$number",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 32.fsp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
