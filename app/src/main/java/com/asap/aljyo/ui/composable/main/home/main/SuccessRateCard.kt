package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.core.components.viewmodel.main.AlarmSuccessRateViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme

private val stops = arrayOf(
    0.28f to Color(0xFFFFEEF3),
    1.0f to Color(0xFFCBE7FF)
)

@Composable
fun SuccessRateCard(
    modifier: Modifier = Modifier
) {
    val viewModel: AlarmSuccessRateViewModel = hiltViewModel()
    val successRate by viewModel.successRateState.collectAsState()

    when (successRate) {
        is UiState.Error -> Unit
        UiState.Loading -> SuccessRateCardShimmer(modifier = modifier)
        is UiState.Success -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
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
                        ),
                ) {

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
private fun ResultCard_Shimmer_Preview() {
    AljyoTheme {
        SuccessRateCardShimmer(modifier = Modifier.size(320.dp))
    }
}