package com.asap.aljyo.ui.composable.group_form.group_create

import android.net.Uri
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_form.GroupFormViewModel
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.composable.group_form.GroupProgressbar
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.util.PictureUtil
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: GroupFormViewModel = hiltViewModel()
) {
    val groupState by viewModel.groupScreenState.collectAsStateWithLifecycle()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.onGroupImageSelected(uri)
        }
    )

    var selectedYear by remember { mutableIntStateOf(LocalDate.now().year) }
    var selectedMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }
    var isShowTimeBottomSheet by remember { mutableStateOf(false) }
    val timeSheetState = rememberModalBottomSheetState()
    var isShowPhotoBottomSheet by remember { mutableStateOf(false) }
    val photoSheetState = rememberModalBottomSheetState()
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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            GroupProgressbar(
                modifier = Modifier
                    .zIndex(1f),
                startProgress = 0.25f,
                endProgress = 0.5f
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    groupImage = groupState.groupImage,
                    onImagePickerClick = { isShowPhotoBottomSheet = true }
                )

                if (isShowPhotoBottomSheet) {
                    BottomSheet(
                        modifier = Modifier.padding(
                            horizontal = 20.dp,
                            vertical = 24.dp
                        ),
                        sheetState = photoSheetState,
                        onDismissRequest = { isShowPhotoBottomSheet = false },
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 28.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "이미지 변경",
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
                                            coroutineScope.launch { photoSheetState.hide() }
                                                .invokeOnCompletion {
                                                    if (!photoSheetState.isVisible) isShowPhotoBottomSheet = false
                                                }
                                        }
                                )
                            }
                        },
                        content = {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        imagePickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                        coroutineScope
                                            .launch { photoSheetState.hide() }.invokeOnCompletion {
                                                if (!photoSheetState.isVisible) isShowPhotoBottomSheet = false
                                            }
                                    }
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_album),
                                    modifier = Modifier
                                        .padding(end = 10.dp),
                                    contentDescription = "Album Icon",
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "앨범에서 선택하기",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp,
                                        color = Black02
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(28.dp))

                            Row(
                                modifier = Modifier
                                    .clickable {
                                        PictureUtil.groupRandomImage.filterNot { it == groupState.groupImage }
                                            .random()
                                            .also { viewModel.onGroupImageSelected(it) }
                                        coroutineScope.launch { photoSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!photoSheetState.isVisible) isShowPhotoBottomSheet =
                                                    false
                                            }
                                    }
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_random),
                                    modifier = Modifier
                                        .padding(bottom = 26.dp, end = 10.dp),
                                    contentDescription = "Random Icon",
                                    tint = Color.Unspecified
                                )
                                Text(
                                    text = "랜덤으로 바꾸기",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp,
                                        color = Black02
                                    )
                                )
                            }
                        }
                    )
                }

                GroupInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .heightIn(min = 50.dp, max = 80.dp),
                    label = "그룹명",
                    value = groupState.title,
                    onValueChange = { viewModel.onGroupTitleChanged(it) },
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
                    value = groupState.description,
                    onValueChange = { viewModel.onGroupDescriptionChanged(it) },
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
                    value = groupState.maxPerson,
                    onPlusClick = { viewModel.onGroupPersonSelected(it) },
                    onMinusClick = { viewModel.onGroupPersonSelected(it) }
                )

                WeekdayPicker(
                    selectedDays = groupState.alarmDays,
                    onDaySelected = { day -> viewModel.onAlarmDaysSelected(day) }
                )

                TimePicker(
                    selectedTime = groupState.alarmTime,
                    onClick = { isShowTimeBottomSheet = true }
                )

                if (isShowTimeBottomSheet) {
                    BottomSheet(
                        sheetState = timeSheetState,
                        onDismissRequest = { isShowTimeBottomSheet = false },
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
                                            coroutineScope.launch { timeSheetState.hide() }
                                                .invokeOnCompletion {
                                                    if (!timeSheetState.isVisible) isShowTimeBottomSheet =
                                                        false
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
                                        viewModel.onAlarmTimeSelected(tempHour, tempMinutes)
                                        coroutineScope.launch { timeSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!timeSheetState.isVisible) isShowTimeBottomSheet =
                                                    false
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
                    selectedDate = groupState.alarmEndDate,
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
                    onDateSelected = { viewModel.onAlarmEndDateSelected(it) },
                )

                CustomButton(
                    modifier = Modifier
                        .padding(bottom = 6.dp, top = 40.dp),
                    text = "다음",
                    enable = groupState.buttonState,
                    onClick = onNextClick
                )
            }
        }
    }
}


@Composable
fun GroupImagePicker(
     groupImage: Uri?,
    onImagePickerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 2f)
            .padding(top = 8.dp)
    ) {
        AsyncImage(
            model = groupImage ?: R.drawable.group_default_img,
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
                .clickable { onImagePickerClick() }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center),
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
//    AljyoTheme {
//        CreateGroupScreen(
//            onBackClick = {}
//        )
//    }
}
