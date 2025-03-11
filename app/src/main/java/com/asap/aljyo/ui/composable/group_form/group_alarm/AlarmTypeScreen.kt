package com.asap.aljyo.ui.composable.group_form.group_alarm

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_form.GroupFormViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.composable.group_form.group_create.AlarmTimePicker
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02
import kotlinx.coroutines.launch
import org.checkerframework.common.subtyping.qual.Bottom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmTypeScreen(
    viewModel: GroupFormViewModel,
    onBackClick: () -> Unit,
    navigateToAlarmSetting: () -> Unit
) {
    val alarmState by viewModel.alarmScreenState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var isShowTimeBottomSheet by remember { mutableStateOf(Pair(false, "")) }

    AljyoTheme {
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
            },
            bottomBar = {
                CustomButton(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .navigationBarsPadding(),
                    text = "다음",
                    enable = alarmState.alarmUnlockContents.isNotEmpty(),
                    onClick = navigateToAlarmSetting
                )
            }
        ) { innerPadding ->
            GroupProgressbar(
                modifier = Modifier.padding(innerPadding),
                startProgress = 0.5f,
                endProgress = 0.75f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(start = 20.dp, end = 20.dp, top = 16.dp)
            ) {
                Text(
                    text = "${alarmState.nickName}님만의 알람 방식을\n선택해주세요!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Black,
                        fontSize = 22.fsp,
                        fontWeight = FontWeight.Bold,
                    )
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "그룹 생성 후에도 변경이 가능해요!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Black03,
                        fontSize = 15.fsp,
                    )
                )
//                Spacer(modifier = Modifier.weight(1f))
//
//                Image(
//                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                    painter = painterResource(R.drawable.alarm_bell),
//                    contentDescription = "Alarm Bell Image"
//                )

                Spacer(modifier = Modifier.height(30.dp))

                BoxWithIcon(
                    icon = R.drawable.ic_slide,
                    text = "밀어서 알람 해제",
                    isSelected = alarmState.alarmUnlockContents == "SLIDE",
                    onCheckedChange = { viewModel.onAlarmUnlockContentsSelected("SLIDE") },
                    onPreviewClick = { isShowTimeBottomSheet = Pair(true, "SLIDE") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                BoxWithIcon(
                    icon = R.drawable.ic_card,
                    text = "카드를 터치하여 알람 해제",
                    isSelected = alarmState.alarmUnlockContents == "CARD",
                    onCheckedChange = { viewModel.onAlarmUnlockContentsSelected("CARD") },
                    onPreviewClick = { isShowTimeBottomSheet = Pair(true, "CARD") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                BoxWithIcon(
                    icon = R.drawable.ic_calculator,
                    text = "3번 계산하고 알람 해제",
                    isSelected = alarmState.alarmUnlockContents == "CALCULATION",
                    onCheckedChange = { viewModel.onAlarmUnlockContentsSelected("CALCULATION") },
                    onPreviewClick = { isShowTimeBottomSheet = Pair(true, "CALCULATION") }
                )

//                Spacer(modifier = Modifier.height(36.dp))
                if (isShowTimeBottomSheet.first) {
                    BottomSheet(
                        modifier = Modifier.padding(
                            horizontal = 20.dp,
                            vertical = 24.dp
                        ),
                        sheetState = sheetState,
                        onDismissRequest = { isShowTimeBottomSheet = Pair(false, "") },
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 28.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "알람해제 컨텐츠 미리보기",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontSize = 18.fsp,
                                        color = Black01
                                    )
                                )
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "close",
                                    modifier = Modifier
                                        .clickable {
                                            coroutineScope.launch { sheetState.hide() }
                                                .invokeOnCompletion {
                                                    if (!sheetState.isVisible) isShowTimeBottomSheet =
                                                        Pair(false, "")
                                                }
                                        }
                                )
                            }
                        },
                        content = {
                            Column {
                                // TODO: GIF
                                Box(
                                    modifier = Modifier
                                        .size(320.dp)
                                        .background(color = Red01)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                CustomButton(
                                    text = "선택하기",
                                    enable = true,
                                    onClick = {
                                        coroutineScope.launch { sheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!sheetState.isVisible) isShowTimeBottomSheet =
                                                    Pair(false, "")
                                            }
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BoxWithIcon(
    @DrawableRes icon: Int,
    text: String,
    isSelected: Boolean,
    onCheckedChange: () -> Unit,
    onPreviewClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isSelected) Red01 else Grey02,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
//                color = if (isSelected) Red02 else White,
                color = White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Red01),
                onClick = onCheckedChange
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 20.dp,
                    start = 20.dp
                ),
                painter = painterResource(icon),
                contentDescription = "IconPublic",
                tint = Color.Unspecified
            )

            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isSelected) Red01 else Black01,
                    fontSize = 16.fsp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .clickable { onPreviewClick() }
                    .padding(end = 7.dp)
                    .background(color = Grey01, shape = RoundedCornerShape(6.dp))
                    .padding(vertical = 3.dp, horizontal = 7.dp),
                text = "미리보기",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black02,
                    fontSize = 14.fsp
                )
            )
        }
    }
}


@Preview
@Composable
fun PreviewBoxWithIcon() {
    AljyoTheme {
        BoxWithIcon(
            icon = R.drawable.ic_card_touch,
            text = "카드를 터치하여 알람 해제",
            isSelected = true,
            onCheckedChange = {},
            onPreviewClick = {}
        )

    }
}