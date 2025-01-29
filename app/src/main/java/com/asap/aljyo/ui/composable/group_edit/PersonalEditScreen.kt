package com.asap.aljyo.ui.composable.group_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.edit.PersonalEditViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.group_form.group_alarm.AlarmSoundSlider
import com.asap.aljyo.ui.composable.group_form.group_alarm.AlarmTypeBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalEditScreen(
    viewModel: PersonalEditViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToAlarmMusicScreen: (String?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.complete.collect {
            onBackClick()
        }
    }

    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = if (state.isEditMode) "개인 설정 수정" else "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Black01,
                                fontSize = 16.sp,
                                fontWeight = Bold
                            ),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(R.drawable.ic_top_back),
                                contentDescription = "BACK"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
                )
            },
            bottomBar = {
                CustomButton(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 6.dp),
                    text = "완료",
                    enable = state.buttonState,
                    onClick = if (state.isEditMode) viewModel::onCompleteClick else viewModel::joinGroup
                )
            }
        ) {innerPadding ->
            if (!state.isEditMode) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(),
                    color = Grey01,
                    thickness = 2.dp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                if (!state.isEditMode) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = "${state.nickName}님만의 알람 방식을\n선택해주세요!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Black,
                            fontSize = 22.fsp,
                            fontWeight = Bold,
                        )
                    )
                }

                AlarmTypeBox(
                    selectedAlarmType = state.alarmType,
                    onSelected = { viewModel.onAlarmTypeSelected(it) }
                )

                if (state.alarmType == "SOUND" || state.alarmType == "ALL") {
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
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { navigateToAlarmMusicScreen(state.musicTitle) }
                            )
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
                                text = state.musicTitle ?: "노래를 선택해주세요!",
                                style = MaterialTheme.typography.labelMedium.copy(
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
                        sliderPosition = state.alarmVolume ?: 10f,
                        onValueChange = { viewModel.onAlarmVolumeSelected(it) }
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 28.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "잠깐",
                        style = MaterialTheme.typography.headlineMedium.copy(
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
                        style = MaterialTheme.typography.headlineMedium.copy(
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
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Black02,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPersonalEditScreen() {
    PersonalEditScreen(
        onBackClick = {},
        navigateToAlarmMusicScreen = {}
    )
}