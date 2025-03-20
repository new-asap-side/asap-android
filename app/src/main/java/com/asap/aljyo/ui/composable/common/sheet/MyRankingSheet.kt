package com.asap.aljyo.ui.composable.common.sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRankingSheet(modifier: Modifier) {
    val sheetState = rememberModalBottomSheetState()

    BottomSheet(
        sheetState = sheetState,
        modifier = modifier,
        title = {},
        onDismissRequest = {},
    ) { }
}