package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.custom.AljyoSwitch
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.White
import com.asap.data.utility.DateTimeManager
import com.asap.data.utility.DateTimeManager.sortByDay
import com.asap.domain.entity.remote.AlarmInfomation
import com.asap.domain.entity.remote.AlarmSummary

@Composable
internal fun AlarmCard(
    modifier: Modifier = Modifier,
    alarm: AlarmSummary,
    onCheckChanged: (Boolean, AlarmSummary, () -> Unit) -> Unit,
    isDeactivated: Boolean,
) {
    var activate by remember { mutableStateOf(!isDeactivated) }

    val containerColor = if (activate) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        White
    }

    val contentColor = if (activate) {
        MaterialTheme.colorScheme.primary
    } else {
        Grey03
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = contentColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = alarm.group.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.fsp,
                    color = if (!activate) Black01 else contentColor
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val parsed = DateTimeManager.parseToAmPm(alarm.group.alarmTime).split(" ")
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = MaterialTheme.typography.labelMedium.fontFamily,
                                fontSize = 14.fsp,
                                baselineShift = BaselineShift(0.135f)
                            )
                        ) {
                            append("${parsed[0]} ")
                        }
                        append(parsed[1])
                    },
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.fsp,
                    ),
                )

                AljyoSwitch(
                    modifier = Modifier
                        .size(52.dp, 28.dp)
                        .clip(CircleShape),
                    checked = activate,
                    onCheckChanged = {
                        onCheckChanged(activate, alarm) {
                            activate = !activate
                        }
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val alarmDays = alarm.group.alarmDays.sortByDay()
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = "Clock icon"
                )
                Text(
                    alarmDays.joinToString(separator = " "),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmCard(
            modifier = Modifier
                .width(320.dp)
                .wrapContentHeight(),
            alarm = AlarmSummary(
                groupId = -1,
                group = AlarmInfomation(
                    title = "Title",
                    description = "",
                    alarmTime = "21:30",
                    alarmEndDate = "",
                    alarmDays = listOf("월", "화", "수")
                )
            ),
            onCheckChanged = { _, _, _ -> },
            isDeactivated = true
        )
    }
}