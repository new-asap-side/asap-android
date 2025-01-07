package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.AlarmListViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01

@Composable
internal fun AlarmTimer(
    modifier: Modifier = Modifier,
    viewModel: AlarmListViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val fastestAlarmTimeState by viewModel.fastestAlarmTimeState.collectAsStateWithLifecycle()
        val nickname by remember { mutableStateOf(viewModel.nickname) }
        Text(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            text = buildAnnotatedString {
                append(text = "${stringResource(R.string.sir, nickname)},\n")
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    )
                ) {
                    append(
                        text = "$fastestAlarmTimeState 뒤\n"
                    )
                }
                append(text = stringResource(R.string.the_alarm_goes_off))
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 20.fsp,
                color = Black01,
                lineHeight = 28.fsp,
            )
        )
        Image(
            painter = painterResource(R.drawable.img_alarm_list_illust),
            contentDescription = "Alarm list illust"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmTimer(
            modifier = Modifier.fillMaxWidth()
        )
    }
}