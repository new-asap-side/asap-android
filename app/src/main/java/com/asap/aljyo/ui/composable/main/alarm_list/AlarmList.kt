package com.asap.aljyo.ui.composable.main.alarm_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
internal fun AlarmList(modifier: Modifier) {
    Surface(
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
            item {
                AlarmCard()
            }
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