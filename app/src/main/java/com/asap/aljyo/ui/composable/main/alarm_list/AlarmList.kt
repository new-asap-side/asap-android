package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.AlarmListViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.AlarmSummary

@Composable
internal fun AlarmList(
    modifier: Modifier,
    alarmList: List<AlarmSummary>,
    navigateToHome: () -> Unit,
    navigateToGroupDetails: (Int) -> Unit,
    onCheckChanged: (Boolean, AlarmSummary, () -> Unit) -> Unit,
    viewModel: AlarmListViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AlarmTimer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 32.dp)
            )
        }

        alarmList.forEach { alarm ->
            item {
                AlarmCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            navigateToGroupDetails(alarm.groupId)
                        },
                    alarm = alarm,
                    onCheckChanged = onCheckChanged,
                    isDeactivated = viewModel.isDeactivatedAlarm(alarm.groupId)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(22.dp))
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Black01,
                    contentColor = White,
                ),
                onClick = { navigateToHome() }
            ) {
                Text(
                    text = stringResource(R.string.find_more_group),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.fsp,
                    )
                )
            }
            Spacer(modifier = Modifier.height(62.dp))
        }
    }
}