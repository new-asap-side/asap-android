package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Red02


@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val cardState by viewModel.cardState.collectAsState()

    when (cardState) {
        is UiState.Error -> Unit

        UiState.Loading -> ResultCardShimmer(modifier = modifier.height(192.dp))

        is UiState.Success -> {
            val card = (cardState as UiState.Success).data
            Column(modifier = modifier) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Black01,
                                        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(viewModel.nickname)
                                }
                                append("${stringResource(R.string.of)}\n")
                                append("${stringResource(R.string.release_alarm_succes_rate)}\n")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily
                                    )
                                ) {
                                    append("${card?.offRate ?: 0.0f}%")
                                }
                                append(stringResource(R.string.`is`))
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 20.fsp,
                                color = Black01
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(R.string.start_lively_today),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Black03,
                                fontSize = 15.fsp
                            )
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.img_result_card_thumbnail),
                        contentDescription = "result card thumbnail"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 64.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            brush = Brush.linearGradient(
                                listOf(Red02, Color(0xFFFFE2EB))
                            )
                        )
                        .padding(
                            horizontal = 24.dp,
                            vertical = 20.dp
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.participating_group),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Black01,
                                fontSize = 15.fsp
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_group),
                                contentDescription = "group icon",
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(
                                    R.string.counting,
                                    card?.joinedGroupCount ?: 0
                                ),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 16.fsp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultCardShimmer(modifier: Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                val shape = RoundedCornerShape(4.dp)

                ShimmerBox(
                    modifier = Modifier
                        .size(68.dp, 20.dp)
                        .clip(shape)
                )

                ShimmerBox(
                    modifier = Modifier
                        .size(145.dp, 20.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                ShimmerBox(
                    modifier = Modifier
                        .size(90.dp, 20.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                ShimmerBox(
                    modifier = Modifier
                        .size(179.dp, 24.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

            }

            ShimmerBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(90.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )
    }
}

@Preview
@Composable
private fun ResultCard_Shimmer_Preview() {
    AljyoTheme {
        ResultCardShimmer(modifier = Modifier.size(320.dp, 192.dp))
    }
}