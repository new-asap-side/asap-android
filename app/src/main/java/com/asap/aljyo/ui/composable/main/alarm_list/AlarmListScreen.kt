package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.components.main.AlarmListViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

@Composable
internal fun AlarmListScreen(
    navigateToHome: () -> Unit = {},
    viewModel: AlarmListViewModel = hiltViewModel()
) {
    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(White)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.alarm_list),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    color = Black01
                )
            )
        }

        Surface(
            modifier = Modifier.weight(1f)
        ) {
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
                                    fontSize = 20.sp,
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
                                        fontSize = 16.sp,
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

                    if (alarmList.isEmpty()) {
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
                            alarmList = alarmList,
                            navigateToHome = navigateToHome
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
        AlarmListScreen()
    }
}
