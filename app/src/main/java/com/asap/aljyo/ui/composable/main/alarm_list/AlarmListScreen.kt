package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.AlarmListViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.AlarmSummary

@Composable
internal fun AlarmListScreen(
    navigateToHome: () -> Unit = {},
    navigateToGroupDetails: (Int) -> Unit,
    viewModel: AlarmListViewModel = hiltViewModel()
) {
    val onCheckChanged: (Boolean, AlarmSummary, () -> Unit) -> Unit =
        { check, alarmSummary, update ->
            viewModel.onCheckChanged(check, alarmSummary).invokeOnCompletion { result ->
                if (result == null) {
                    update()
                }
            }
        }

    val lifecycleOwner = LocalLifecycleOwner.current
    val observer = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> viewModel.resume()
            Lifecycle.Event.ON_STOP -> viewModel.pause()
            else -> {}
        }
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(modifier = Modifier) {
        Surface(modifier = Modifier.weight(1f)) {
            val uiState by viewModel.alarmList.collectAsState()
            when (uiState) {
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.load_fail),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 20.fsp,
                                    color = Black01,
                                    textAlign = TextAlign.Center
                                )
                            )

                            TextButton(
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = Black01,
                                    contentColor = White,
                                ),
                                onClick = { viewModel.fetchAlarmList() }
                            ) {
                                Text(
                                    text = stringResource(R.string.retry),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 16.fsp,
                                    )
                                )
                            }
                        }
                    }
                }

                UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp))
                    }
                }

                is UiState.Success -> {
                    val alarmList = (uiState as UiState.Success).data

                    if (alarmList?.isEmpty() == true) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            EmptyAlarmList(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                navigateToHome = navigateToHome
                            )
                        }
                    } else {
                        AlarmList(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            alarmList = alarmList ?: emptyList(),
                            navigateToHome = navigateToHome,
                            navigateToGroupDetails = navigateToGroupDetails,
                            onCheckChanged = onCheckChanged
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmListScreen(
            navigateToHome = {},
            navigateToGroupDetails = {}
        )
    }
}
