package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
internal fun AlarmList(modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            AlarmCard()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        AlarmList(
            modifier = Modifier.fillMaxSize()
        )
    }
}