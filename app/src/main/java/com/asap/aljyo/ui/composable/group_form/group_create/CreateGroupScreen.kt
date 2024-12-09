package com.asap.aljyo.ui.composable.group_form.group_create

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.White
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    onBackClick: () -> Unit
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->

        }
    )

    var selectedYear by remember { mutableIntStateOf(LocalDate.now().year) }
    var selectedMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedHour by remember { mutableStateOf("12") }
    var selectedMinutes by remember { mutableStateOf("00") }
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var groupValue by remember { mutableIntStateOf(1) }
    val selectedDays = remember { mutableStateListOf<String>() }
    var isShowBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()



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
            startProgress = 0.3f,
            endProgress = 0.6f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
        ) {
            Text(
                text = "대표 이미지",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black02,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            GroupImagePicker(
                onImagePickerClick = {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )

            GroupInputField(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(min = 50.dp, max = 80.dp),
                label = "그룹명",
                value = descriptionText,
                onValueChange = { descriptionText = it },
                type = GROUP_TITLE,
                placeHolder = {
                    Text(
                        text = "그룹명을 입력해주세 (최대 30자 이내)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                            color = Black04
                        )
                    )
                },
            )

            GroupInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                label = "그룹 소개글",
                value = titleText,
                onValueChange = { titleText = it },
                type = GROUP_DESCRIPTION,
                placeHolder = {
                    Text(
                        text = "내용을 입력해세요",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                            color = Black04
                        )
                    )
                },
            )

            MemberPicker(
                value = groupValue,
                onPlusClick = { groupValue = it },
                onMinusClick = { groupValue = it }
            )

            WeekdayPicker(
                selectedDays = selectedDays,
                onDaySelected = { day ->
                    selectedDays.run {
                        if (day in this) remove(day) else add(day)
                    }
                }
            )

            TimePicker(
                selectedHour = selectedHour,
                selectedMinutes = selectedMinutes,
                onClick = { isShowBottomSheet = true }
            )

            if (isShowBottomSheet) {
                BottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { isShowBottomSheet = false },
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "시간 선택",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 18.sp,
                                    color = Black01
                                )
                            )
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "close",
                                modifier = Modifier
                                    .clickable {
                                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                            if (!sheetState.isVisible) isShowBottomSheet = false
                                        }
                                    }
                            )
                        }
                    },
                    content = {
                        Column {
                            var tempHour = ""
                            var tempMinutes = ""

                            AlarmTimePicker(
                                onHourSelected = { tempHour = it },
                                onMinutesSelected = { tempMinutes = it }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CustomButton(
                                text = "확인",
                                enable = true,
                                onClick = {
                                    selectedHour = tempHour
                                    selectedMinutes = tempMinutes
                                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) isShowBottomSheet = false
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                )
            }
            CalendarView(
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                selectedDate = selectedDate,
                onMonthSelected = { month ->
                    when (month) {
                        13 -> {
                            selectedMonth = 1
                            selectedYear += 1
                        }
                        0 -> {
                            selectedMonth = 12
                            selectedYear -= 1
                        }
                        else -> selectedMonth = month
                    }
                },
                onDateSelected = {selectedDate = it},
            )
        }
    }
}


@Composable
fun GroupImagePicker(
    onImagePickerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 2f)
            .padding(top = 8.dp)
    ) {
        AsyncImage(
            model = R.drawable.group_default_img,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        )
        Box(
            modifier = Modifier
                .padding(bottom = 14.dp, end = 14.dp)
                .size(40.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(color = White)
                .padding(12.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable { onImagePickerClick() },
                painter = painterResource(R.drawable.ic_select_photo),
                contentDescription = "Group Image Picker Icon",
                tint = Color.Unspecified,
            )
        }
    }
    Text(
        text = "사진을 등록하지 않는 경우 랜덤 이미지로 보여집니다",
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Black03,
            fontSize = 12.sp
        ),
        modifier = Modifier
            .padding(top = 8.dp)
    )
}

@Composable
@Preview
fun PreviewCreateGroupScreen() {
    AljyoTheme {
        CreateGroupScreen(
            onBackClick = {}
        )
    }
}
