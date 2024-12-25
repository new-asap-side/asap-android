package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_details.GroupDetailsViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White

@Composable
fun NextAlarmTimer(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailsViewModel
) {
    val groupDetailsState by viewModel.groupDetails.collectAsState()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_bell),
            contentDescription = "Next alarm timer icon",
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(R.string.next_alarm_timer_title),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Black02
            )
        )

        when (groupDetailsState) {
            is UiState.Error -> Unit

            UiState.Loading -> ShimmerBox(modifier = Modifier.size(100.dp, 14.dp))

            is UiState.Success -> {
                val remainTime by viewModel.nextAlarmTime.collectAsState()

                if (remainTime.isEmpty()) {
                    ShimmerBox(modifier = Modifier.size(100.dp, 14.dp))
                } else {
                    Text(
                        text = remainTime,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun NextAlarmTimerPreview() {
    AljyoTheme {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(color = White.copy(alpha = 0.88f))
                .padding(
                    vertical = 6.dp,
                    horizontal = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bell),
                contentDescription = "Next alarm timer icon",
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(R.string.next_alarm_timer_title),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = Black02
                )
            )
            Text(
                text = "05:00:00",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}