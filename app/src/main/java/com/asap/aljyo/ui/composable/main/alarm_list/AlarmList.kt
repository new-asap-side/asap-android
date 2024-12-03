package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.Alarm

@Composable
internal fun AlarmList(
    modifier: Modifier,
    alarmList: List<Alarm>,
    navigateToHome: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        alarmList.forEach { alarm ->
            item {
                AlarmCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    alarm = alarm
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
                        fontSize = 16.sp,
                    )
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmList(
            modifier = Modifier.fillMaxSize(),
            alarmList = listOf(
                Alarm(),
                Alarm(),
                Alarm(),
            ),
            navigateToHome = {}
        )
    }
}