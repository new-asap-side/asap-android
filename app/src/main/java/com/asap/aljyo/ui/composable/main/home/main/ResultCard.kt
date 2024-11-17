package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Red02


@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    successRate: Float?,
    participatingGroup: Int?
) {
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
                                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily
                            )
                        ) {
                            append("알죠")
                        }
                        append("${stringResource(R.string.of)}\n")
                        append("${stringResource(R.string.release_alarm_succes_rate)}\n")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily
                            )
                        ) {
                            append("${successRate ?: 0.0f}%")
                        }
                        append(stringResource(R.string.`is`))
                    },
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        color = Black01
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.start_lively_today),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Black03,
                        fontSize = 15.sp
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.participating_group),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Black01,
                        fontSize = 15.sp
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
                            participatingGroup ?: 0
                        ),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }
}