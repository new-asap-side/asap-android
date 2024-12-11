package com.asap.aljyo.ui.composable.group_form.group_alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingScreen(
    onBackClick: () -> Unit
) {
    var selectedAlarmType by remember { mutableIntStateOf(0) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var openAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = White,
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_top_back),
                            contentDescription = "BACK"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        }
    ) { innerPadding ->
        GroupProgressbar(
            modifier = Modifier.padding(innerPadding),
            startProgress = 0.75f,
            endProgress = 1.0f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
        ) {
            Text(
                text = "\"닉네임\"님만의 알람 방식을\n선택해주세요!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Black,
                    fontSize = 22.sp,
                    fontWeight = Bold,
                )
            )

            AlarmTypeBox(
                selectedAlarmType = selectedAlarmType,
                onSelected = { type, value -> selectedAlarmType = value }
            )

            if (selectedAlarmType != 2) {
                Text(
                    modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                    text = "알람음",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Black02,
                        fontSize = 14.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Grey02,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "노래 선택",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Black01,
                                fontSize = 15.sp,
                            )
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 96.dp, end = 4.dp)
                                .weight(1f),
                            text = "Text",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Black03,
                                fontSize = 15.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End
                        )

                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = "Arrow Right Icon"
                        )
                    }
                }

                AlarmSoundSlider(
                    sliderPosition = sliderPosition,
                    onValueChange = { sliderPosition = it }
                )
            }

            Row(
                modifier = Modifier.padding(top = 28.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "잠깐",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Red01,
                        fontSize = 16.sp,
                        fontWeight = Bold
                    )
                )
                Icon(
                    modifier = Modifier.padding(end = 4.dp),
                    painter = painterResource(R.drawable.ic_wait),
                    contentDescription = "Wait Icon",
                    tint = Color.Unspecified
                )
                Text(
                    text = "알람을 설정하기 전 꼭 확인해 주세요!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Black01,
                        fontSize = 16.sp,
                        fontWeight = Bold
                    )
                )
            }

            listOf(
                "기기에서 무음으로 설정하면 알람 방식이 소리 또는 진동이어도 무음으로 울릴 수 있습니다.",
                "원활한 알람을 위하여 알람 관련 권한을 허용해 주세요."
            ).forEach {
                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 7.dp, end = 6.dp)
                            .size(4.dp)
                            .background(color = Black03, shape = CircleShape)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Black02,
                            fontSize = 12.sp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                modifier = Modifier.padding(bottom = 6.dp),
                text = "완료",
                enable = selectedAlarmType != 0,
                onClick = { openAlertDialog = true }
            )
            if (openAlertDialog) {
                CustomAlertDialog(
                    title = "그룹 생성 완료!",
                    content = "6시간 후부터 알람이 울려요",
                    onClick =  {
                        openAlertDialog = false
                        // 화면 이동
                    },
                    dialogImg = R.drawable.group_dialog_img
                )
            }
        }
    }
}

@Composable
fun AlarmTypeBox(
    selectedAlarmType: Int = 0,
    onSelected: (String, Int) -> Unit,
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
                type = "sound",
                value = 1,
                isSelected = selectedAlarmType == 1,
                onSelected = onSelected
            )

            SelectBox(
                modifier = Modifier.weight(1f),
                text = "진동",
                type = "vibration",
                value = 2,
                isSelected = selectedAlarmType == 2,
                onSelected = onSelected
            )

            SelectBox(
                modifier = Modifier.weight(1f),
                text = "소리, 진동",
                type = "all",
                value = 3,
                isSelected = selectedAlarmType == 3,
                onSelected = onSelected
            )

        }
    }
}

@Composable
fun SelectBox(
    modifier: Modifier = Modifier,
    text: String,
    type: String,
    value: Int,
    isSelected: Boolean,
    onSelected: (String, Int) -> Unit
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
                onClick = { onSelected(type, value) }
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

@Composable
@Preview
fun PreviewAlarmSettingScreen() {
    AljyoTheme {
        AlarmSettingScreen {  }
    }
}
