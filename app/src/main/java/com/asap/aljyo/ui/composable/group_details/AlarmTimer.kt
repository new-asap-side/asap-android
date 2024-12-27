package com.asap.aljyo.ui.composable.group_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White

@Composable
fun AlarmTimer(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailsViewModel
) {
    val remainTime by viewModel.nextAlarmTime.collectAsState()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(remainTime.isNotEmpty()) {
        visible = remainTime.isNotEmpty()
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight ->
                -fullHeight
            },
            animationSpec = tween(durationMillis = 200)
        )
    ) {
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