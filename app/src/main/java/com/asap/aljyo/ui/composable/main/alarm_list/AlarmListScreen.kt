package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
internal fun AlarmListScreen(
    navigateToHome: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        EmptyAlarmList(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            navigateToHome = navigateToHome
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmListScreen()
    }
}
