package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupDetailsScreen() {
    LazyColumn {
        item {
            GroupSummation(modifier = Modifier.fillMaxWidth())
        }
    }
}