package com.asap.aljyo.ui.composable.main.home.main

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.main.AlarmSuccessRateViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.custom.BubbleBox
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

private val stops = arrayOf(
    0.28f to Color(0xFFFFEEF3),
    1.0f to Color(0xFFCBE7FF)
)

@Composable
fun SuccessRateCard(
    modifier: Modifier = Modifier
) {
    val viewModel: AlarmSuccessRateViewModel = hiltViewModel()
    val successRateState by viewModel.successRateState.collectAsState()
    val user by viewModel.user.collectAsState()

    when (successRateState) {
        is UiState.Error -> Unit
        UiState.Loading -> SuccessRateCardShimmer(modifier = modifier)
        is UiState.Success -> {
            val successRate = (successRateState as UiState.Success).data
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(320f / 254f)
                        .dropShadow(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF003870).copy(alpha = 0.06f),
                            offsetX = 4.dp, offsetY = 4.dp,
                            blur = 8.dp
                        )
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            brush = Brush.linearGradient(
                                colorStops = stops,
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                            )
                        )
                        .padding(vertical = 28.dp, horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    val rate = successRate?.offRate ?: 0f
                    SuccessRate(
                        modifier = Modifier.fillMaxWidth(),
                        nickname = user?.nickname ?: "",
                        rate = rate
                    )

                    SuccessRateProgress(
                        modifier = Modifier.fillMaxSize(),
                        rate = rate
                    )
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                    ) {

                    }

                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
private fun SuccessRate(
    modifier: Modifier = Modifier,
    nickname: String,
    rate: Float
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111111)
                        )
                    ) { append(nickname) }
                    append(stringResource(R.string.your_alarm_off_success_rate))
                },
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.fsp,
                    color = Black01
                )
            )

            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.this_month))

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.fsp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) { append(" ${stringResource(R.string.rate, rate)} ") }

                    append(stringResource(R.string.success))
                },
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 23.fsp,
                )
            )
        }

        TextButton(
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = White,
                contentColor = Color(0xFF666666)
            )
        ) {
            Text(
                text = stringResource(R.string.use_information),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 13.fsp,
                )
            )
        }
    }
}

@Composable
private fun SuccessRateProgress(
    modifier: Modifier = Modifier,
    rate: Float
) {
    Log.d("Progress", "composed")
    var trigger by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    val animateProgress by animateFloatAsState(
        progress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "animated progress value",
        finishedListener = { trigger = true }
    )
    val update = updateTransition(trigger, label = "observer")
    val alpha by update.animateFloat(label = "alpha") {
        if (it) 1f else 0f
    }

    val tranistion by update.animateDp(label = "transition") {
        if (it) 0.dp else 20.dp
    }


    LaunchedEffect(LocalLifecycleOwner.current) {
        progress = if (rate == 0f) 20f else rate
    }

    Box(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(20.dp)
                .clip(shape = CircleShape)
                .background(White)
                .drawBehind {
                    drawLine(
                        color = Color(0xFFEFB1C7),
                        start = Offset(30f, size.height / 2),
                        end = Offset(size.width - 30f, size.height / 2),
                        strokeWidth = 5f,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(10f, 10f), 0f
                        )
                    )
                }
        ) {
            val progressWith = constraints.maxWidth * (animateProgress / 100f)
            var progressDp: Dp
            with(LocalDensity.current) {
                progressDp = progressWith.toDp()
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(progressDp)
                    .background(
                        shape = CircleShape, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFF639B), MaterialTheme.colorScheme.primary
                            )
                        )
                    )
            )
        }

        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(69.dp)
                .offset(y = (-20).dp),
            painter = painterResource(R.drawable.ic_park),
            contentDescription = "park",
            contentScale = ContentScale.FillHeight
        )

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .offset(y = tranistion)
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            val tailHeight = LocalDensity.current.run {
                10.dp.toPx()
            }

            BubbleBox(
                modifier = Modifier.height(50.dp),
                tailHeight = tailHeight,
                containerColor = Color(0xFF330315)
            ) { modifier ->
                Box(modifier = modifier.fillMaxHeight()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(R.string.fighting),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Image(
                modifier = Modifier
                    .width(60.dp)
                    .weight(1f),
                painter = painterResource(R.drawable.img_aljyo_mascot),
                contentDescription = "aljyo illust",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

// preview
@Composable
private fun SuccessRateCardShimmer(modifier: Modifier) {
    val shape = RoundedCornerShape(4.dp)
    Column(modifier = modifier) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(254.dp)
                .clip(shape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerBox(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .clip(shape)
            )

            ShimmerBox(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .clip(shape)
            )
        }
    }
}

@Preview
@Composable
private fun SuccessRateCard_Shimmer_Preview() {
    AljyoTheme {
        SuccessRateCardShimmer(modifier = Modifier.size(320.dp))
    }
}