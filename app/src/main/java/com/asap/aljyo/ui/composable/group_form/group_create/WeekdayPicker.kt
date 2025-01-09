package com.asap.aljyo.ui.composable.group_form.group_create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White

@Composable
fun WeekdayPicker(
    selectedDays: List<String>,
    onDaySelected: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            text = "알람 요일",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 14.fsp
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("월", "화", "수", "목", "금", "토", "일").forEach { day ->
                val isSelected = day in selectedDays

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(color = if (isSelected) Red02 else White)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Red01 else Grey02,
                            shape = CircleShape,
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onDaySelected(day) }
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = day,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.fsp,
                            color = if (isSelected) Red01 else Black01
                        )
                    )
                }
            }
        }
    }
}