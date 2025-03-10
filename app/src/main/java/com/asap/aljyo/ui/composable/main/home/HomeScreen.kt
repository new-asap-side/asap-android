package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.composable.common.dialog.DialogButtonType
import com.asap.aljyo.ui.composable.common.dialog.PrecautionsDialog
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Error
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDescript: () -> Unit,
    navigateToMyAlarm: () -> Unit,
    navigateToGroupDetails: (Int) -> Unit,
    navigateToPersonalSetting: (Int) -> Unit,
    onCreateButtonClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        val coroutineScope = rememberCoroutineScope()

        val privateGroupState = viewModel.privateGroupState
        var password by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var isError by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        val requestJoinState by viewModel.joinResponseState.collectAsState()

        val hideSheet = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    viewModel.clearPrivateGroupState()
                }
            }
        }

        LaunchedEffect(requestJoinState) {
            when (requestJoinState) {
                is RequestState.Error -> {
                    isError = true
                    isLoading = false
                }
                val privateGroupState = viewModel.privateGroupState
                var password by remember { mutableStateOf("") }
                var isLoading by remember { mutableStateOf(false) }
                var isError by remember { mutableStateOf(false) }
                val sheetState = rememberModalBottomSheetState()
                val requestJoinState by viewModel.joinResponseState.collectAsState()
                var showDialog by remember { mutableStateOf(false) }

                is RequestState.Success -> {
                    isLoading = false
                    val groupId = viewModel.selectedGroupId.value!!
                    coroutineScope.launch {
                        hideSheet()
                    }.invokeOnCompletion {
                        viewModel.joinStateClear()
                        navigateToGroupDetails(groupId)
                    }
                }

                RequestState.Initial -> Unit
                RequestState.Loading -> {
                    isLoading = true

                LaunchedEffect(requestJoinState) {
                    when (requestJoinState) {
                        is RequestState.Error -> {
                            isError = true
                            isLoading = false
                        }
                        is RequestState.Success -> {
                            isLoading = false
                            val groupId = viewModel.selectedGroupId.value!!
                            coroutineScope.launch {
                                hideSheet()
                            }.invokeOnCompletion {
                                viewModel.joinStateClear()
                                navigateToPersonalSetting(groupId)
                            }
                        }

                        RequestState.Initial -> Unit
                        RequestState.Loading -> {
                            isLoading = true
                        }
                    }

                }
            }
        }

        LaunchedEffect(privateGroupState) {
            if (privateGroupState.isJoinedGroup == true) {
                navigateToGroupDetails(viewModel.selectedGroupId.value ?: 0)
                viewModel.clearPrivateGroupState()
            }
        }

        if (privateGroupState.showPasswordSheet) {
            BottomSheet(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 24.dp
                ),
                sheetState = sheetState,
                onDismissRequest = { viewModel.clearPrivateGroupState() },
                arrangement = Arrangement.SpaceBetween,
                title = {
                    Text(
                        text = stringResource(R.string.private_alarm_input_password),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Black01,
                            fontSize = 18.fsp
                        )
                    )
                }
            ) {
                val interactionSource = remember { MutableInteractionSource() }

                LaunchedEffect(Unit) {
                    viewModel.showDialog.collect {
                        showDialog = it
                    }
                }

                if (privateGroupState.showPasswordSheet) {
                    BottomSheet(
                        modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 24.dp
                            ),
                        sheetState = sheetState,
                        onDismissRequest = { viewModel.clearPrivateGroupState() },
                        arrangement = Arrangement.SpaceBetween,
                        title = {
                            Text(
                                text = stringResource(R.string.private_alarm_input_password),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = Black01,
                                    fontSize = 18.fsp
                                )
                            )
                        }
                    ) {
                        val interactionSource = remember { MutableInteractionSource() }


                Spacer(modifier = Modifier.height(20.dp))

                BasicTextField(
                    value = password,
                    onValueChange = { value ->
                        if (value.length > 4) {
                            return@BasicTextField
                        }

                        password = value
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .indicatorLine(
                            enabled = true,
                            isError = isError,
                            interactionSource = interactionSource,
                            focusedIndicatorLineThickness = 3.dp,
                            unfocusedIndicatorLineThickness = 3.dp,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Black01,
                                unfocusedIndicatorColor = Black01,
                                errorIndicatorColor = Error
                            )
                        ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Black01,
                        fontSize = 18.fsp
                    ),
                ) { innerTextField ->
                    password.ifEmpty {
                        Text(
                            text = stringResource(R.string.password_hint),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Black03,
                                fontSize = 18.fsp
                            )
                        )
                    }

                    TextFieldDefaults.DecorationBox(
                        value = password,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        singleLine = true,
                        enabled = true,
                        label = if (password.isEmpty()) null else {
                            {
                                Text(
                                    text = stringResource(R.string.password),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Black03,
                                        fontSize = 12.fsp
                                    )
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password_hint),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Black03,
                                    fontSize = 18.fsp
                                )
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = White,
                            focusedContainerColor = White,
                            errorContainerColor = White,
                            cursorColor = Black01,
                            errorIndicatorColor = Black01,
                            errorTextColor = Black01,
                            errorSupportingTextColor = Error
                        ),
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(
                            horizontal = 0.dp,
                            vertical = 6.dp
                        ),
                        isError = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isError) {
                    Text(
                        text = stringResource(R.string.password_error),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Error,
                            fontSize = 12.fsp
                        )
                    )
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(56.dp))

                Row(
                    modifier = Modifier.height(52.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Red02,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { hideSheet() }
                    ) {
                        Text(
                            text = stringResource(R.string.exit),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.fsp
                            )
                        )

                            TextButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                enabled = password.length >= 4 && !isLoading,
                                colors = ButtonDefaults.textButtonColors(
                                    disabledContainerColor = Grey02,
                                    disabledContentColor = Black04,
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = White
                                ),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    viewModel.joinGroup(password = password)
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.join),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 16.fsp
                                    )
                                )
                            }
                        }
                    }

                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        enabled = password.length >= 4 && !isLoading,
                        colors = ButtonDefaults.textButtonColors(
                            disabledContainerColor = Grey02,
                            disabledContentColor = Black04,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            viewModel.joinGroup(password = password, alarmType = "VIBRATION")

                if (showDialog) {
                    PrecautionsDialog(
                        buttonType = DialogButtonType.SINGLE,
                        title = "그룹 인원이 모두 찼어요",
                        description = "다른 그룹을 찾아볼까요?",
                        onDismissRequest = { showDialog = false },
                        onConfirm =  { showDialog = false }
                    )
                }

                HomeTabScreen(
                    navigateToDescript = navigateToDescript,
                    onGroupItemClick = { isPublic, groupId ->
                        if (!isPublic) {
                            viewModel.checkJoinedGroup(groupId)
                            viewModel.selectedGroupId.value = groupId
                            return@HomeTabScreen
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.join),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.fsp
                            )
                        )
                    }
                }
            }
        }

        HomeTab(
            navigateToDescript = navigateToDescript,
            navigateToMyAlarm = navigateToMyAlarm,
            onGroupItemClick = { isPublic, groupId ->
                if (!isPublic) {
                    viewModel.checkJoinedGroup(groupId)
                    viewModel.selectedGroupId.value = groupId
                    return@HomeTab
                }
                navigateToGroupDetails(groupId)
            }
        )
    }
}