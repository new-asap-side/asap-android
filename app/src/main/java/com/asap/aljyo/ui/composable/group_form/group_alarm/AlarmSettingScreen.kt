package com.asap.aljyo.ui.composable.group_form.group_alarm

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02

@Composable
fun AlarmTypeBox(
    selectedAlarmType: Int = 0,
    onSelected: (String) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(top = 30.dp, bottom = 8.dp),
            text = "알람 방식",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 14.sp
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SelectBox(
                modifier = Modifier.weight(1f),
                text = "소리",
                isSelected = selectedAlarmType == 1,
                onSelected = { onSelected("sound") }
            )

            SelectBox(
                modifier = Modifier.weight(1f),
                text = "진동",
                isSelected = selectedAlarmType == 2,
                onSelected = { onSelected("vibration") }
            )

            SelectBox(
                modifier = Modifier.weight(1f),
                text = "소리, 진동",
                isSelected = selectedAlarmType == 3,
                onSelected = { onSelected("all") }
            )

        }
    }
}

@Composable
fun SelectBox(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = if (isSelected) Red01 else Grey02,
                shape = RoundedCornerShape(6.dp)
            )
            .background(
                color = if (isSelected) Red02 else White
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Red01),
                onClick = onSelected
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isSelected) Red01 else Black01,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
        )
    }
}

@Composable
fun AlarmSoundSlider(
    sliderPosition: Float,
    onValueChange: (Float) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(top = 28.dp, bottom = 4.dp),
            text = "음량",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 14.sp
            )
        )
        Text(
            text = "최저 음량 10 이상부터 설정이 가능합니다.",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Black03,
                fontSize = 12.sp
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(R.drawable.ic_volume_off),
                contentDescription = "Volume Off Icon",
                tint = Color.Unspecified
            )
            Slider(
                modifier = Modifier.weight(1f),
                value = sliderPosition,
                valueRange = 1f..100f,
                onValueChange = onValueChange,
                colors = SliderDefaults.colors(
                    thumbColor = White,
                    activeTrackColor = Red01,
                    inactiveTickColor = Grey03
                ),
            )
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                painter = painterResource(R.drawable.ic_volume_max),
                contentDescription = "Volume Max Icon",
                tint = Color.Unspecified
            )
        }
    }
}

