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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.Alarm

@Composable
internal fun AlarmCard(
    modifier: Modifier = Modifier,
    alarm: Alarm
) {
    var checked by remember { mutableStateOf(true) }

    val containerColor = if (checked) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        White
    }

    val contentColor = if (checked) {
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
                text = alarm.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.fsp,
                    color = if (!checked) Black01 else contentColor
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = MaterialTheme.typography.labelMedium.fontFamily,
                                fontSize = 14.fsp,
                                baselineShift = BaselineShift(0.2f)
                            )
                        ) {
                            append("${stringResource(R.string.morning)} ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                                fontSize = 24.fsp,
                            ),
                        ) {
                            append(alarm.alarmTime)
                        }
                    },
                )

                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = SwitchDefaults.colors(
                        uncheckedTrackColor = Grey03,
                        uncheckedThumbColor = White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        checkedThumbColor = White,
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = "Clock icon"
                )
                Text(
                    alarm.alarmDay,
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
            alarm = Alarm()
        )
    }
}