package com.asap.aljyo.ui.composable.common.sheet

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.asap.aljyo.core.components.viewmodel.PasswordViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Error
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordSheet(
    groupId: Int,
    navigateToPersonalSetting: (Int) -> Unit,
    viewModel: PasswordViewModel = hiltViewModel()
) {
    var password by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val hideSheet = {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) { }
        }
    }

    val showSheet by viewModel.showSheet.collectAsState(false)
    val joinState by viewModel.joinState.collectAsState()

    LaunchedEffect(joinState) {
        when (joinState) {
            is RequestState.Error -> Unit
            RequestState.Initial -> Unit
            RequestState.Loading -> Unit
            is RequestState.Success -> {
                navigateToPersonalSetting(groupId)
            }
        }

    }

    if (showSheet) {
        BottomSheet(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
            sheetState = sheetState,
            onDismissRequest = { viewModel.hideSheet() },
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
                        isError = joinState is RequestState.Error,
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

            if (joinState is RequestState.Error) {
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
                }

                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    enabled = password.length >= 4 && joinState !is RequestState.Loading,
                    colors = ButtonDefaults.textButtonColors(
                        disabledContainerColor = Grey02,
                        disabledContentColor = Black04,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        viewModel.join(groupId = groupId, password = password)
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
}