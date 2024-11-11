package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.White

@Composable
fun GroupDetailsScreen() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            GroupSummation(modifier = Modifier.fillMaxWidth())
        }
        item {
            AlarmDetails(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
            )
        }
    }
}