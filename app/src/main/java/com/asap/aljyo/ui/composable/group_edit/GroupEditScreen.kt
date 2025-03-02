package com.asap.aljyo.ui.composable.group_edit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.edit.GroupEditViewModel
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.composable.group_form.group_create.GROUP_DESCRIPTION
import com.asap.aljyo.ui.composable.group_form.group_create.GROUP_TITLE
import com.asap.aljyo.ui.composable.group_form.group_create.GroupImagePicker
import com.asap.aljyo.ui.composable.group_form.group_create.GroupInputField
import com.asap.aljyo.ui.composable.group_form.group_create.MemberPicker
import com.asap.aljyo.ui.composable.group_form.group_type.UnderlineTextField
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.util.PictureUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupEditScreen(
    onBackClick: () -> Unit,
    viewModel: GroupEditViewModel = hiltViewModel()
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::onGroupImageSelected
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    var isShowPhotoBottomSheet by remember { mutableStateOf(false) }
    val photoSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val focusPasswordField = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.complete.collect {
            onBackClick()
        }
    }

    LaunchedEffect(state.isPublic) {
        if (!state.isPublic) {
            focusPasswordField.requestFocus()
        }
    }

    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "그룹 수정",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Black01,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
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
                        .padding(top = 40.dp, bottom = 6.dp),
                    text = "완료",
                    enable = state.buttonState,
                    onClick = viewModel::onCompleteClick
                )
            }
        ) { innerPadding ->
            HorizontalDivider(
                modifier = Modifier.padding(innerPadding),
                thickness = 1.dp,
                color = Grey01
            )
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                AlarmContentSelector(
                    alarmContent = state.alarmUnlockContents,
                    onContentClick = viewModel::onAlarmTypeSelected
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "대표 이미지",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Black02,
                        fontSize = 14.sp,
                    )
                )

                GroupImagePicker(
                    groupImage = state.groupImage?.toUri(),
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
                                                    if (!photoSheetState.isVisible) isShowPhotoBottomSheet =
                                                        false
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
                                                if (!photoSheetState.isVisible) isShowPhotoBottomSheet =
                                                    false
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
                                        PictureUtil.groupRandomImage.filterNot { it == state.groupImage?.toUri() }
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
                    value = state.title,
                    onValueChange = viewModel::onGroupTitleChanged,
                    type = GROUP_TITLE,
                    placeHolder = {
                        Text(
                            text = "그룹명을 입력해주세요 (최대 30자 이내)",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                                color = Black04
                            )
                        )
                    }
                )

                GroupInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp),
                    label = "그룹 소개글",
                    value = state.description,
                    onValueChange = viewModel::onGroupDescriptionChanged,
                    type = GROUP_DESCRIPTION,
                    placeHolder = {
                        Text(
                            text = "내용을 입력해세요",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                                color = Black04
                            )
                        )
                    }
                )

                MemberPicker(
                    value = state.currentPerson,
                    onPlusClick = viewModel::onGroupPersonSelected,
                    onMinusClick = viewModel::onGroupPersonSelected
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "현재 인원 이상으로만 변경 가능합니다",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Black03,
                        fontSize = 12.sp
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))

                PublicSelector(
                    isPublic = state.isPublic,
                    onTypeClick = viewModel::onGroupTypeSelected
                )

                if (state.isPublic.not()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    UnderlineTextField(
                        modifier = Modifier
                            .focusRequester(focusPasswordField),
                        value = state.groupPassword ?: "",
                        onValueChange = viewModel::onGroupPasswordChanged
                    )
                }
            }
        }
    }
}

@Composable
fun PublicSelector(
    isPublic: Boolean,
    onTypeClick: (Boolean) -> Unit
) {
    Column {
        Text(
            text = "공개 설정",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Black02
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        RadioButtonWithText(
            text = "공개",
            isSelected = isPublic,
            onClick = { onTypeClick(true) }
        )
        Spacer(modifier = Modifier.height(14.dp))

        RadioButtonWithText(
            text = "비공개",
            isSelected = !isPublic,
            onClick = { onTypeClick(false) }
        )
    }
}

@Composable
fun AlarmContentSelector(
    alarmContent: String,
    onContentClick: (String) -> Unit
) {
    Column {
        Text(
            text = "알람 컨텐츠",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Black02
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        RadioButtonWithText(
            text = "밀어서 알람 해제",
            icon = R.drawable.ic_hand,

            isSelected = alarmContent == "SLIDE",
            onClick = { onContentClick("SLIDE") }
        )
        Spacer(modifier = Modifier.height(14.dp))

        RadioButtonWithText(
            text = "캐릭터를 터치하여 알람 해제",
            icon = R.drawable.ic_card_touch,
            isSelected = alarmContent == "CARD",
            onClick = { onContentClick("CARD") }
        )
    }
}

@Composable
fun RadioButtonWithText(
    text: String,
    @DrawableRes icon: Int? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(22.dp)
                .background(color = White, shape = CircleShape)
                .border(
                    width = if (isSelected) 6.dp else 1.dp,
                    color = if (isSelected) Red01 else Grey03,
                    shape = CircleShape
                )
        )

        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black01,
                fontSize = 15.sp,
            )
        )

        icon?.let {
            Icon(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .size(24.dp),
                painter = painterResource(icon),
                contentDescription = "SLIDE ICON",
                tint = Color.Unspecified
            )
        }
    }
}


@Preview
@Composable
fun PreviewGroupEditScreen() {
    GroupEditScreen(
        onBackClick = {}
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewPublicSelector() {
    AljyoTheme {
        PublicSelector(
            isPublic = true,
            onTypeClick = {}
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewAlarmContentSelector() {
    AljyoTheme {
        AlarmContentSelector(
            alarmContent = "SLIDE",
            onContentClick = {}
        )
    }
}