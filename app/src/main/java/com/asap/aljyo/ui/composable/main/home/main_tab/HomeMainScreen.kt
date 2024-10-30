package com.asap.aljyo.ui.composable.main.home.main_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.Grey01

@Composable
fun HomeMainScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ResultCardWrapper(
            modifier = Modifier
                .background(color = Grey01)
                .padding(vertical = 32.dp, horizontal = 20.dp)
                .fillMaxWidth()
        )
    }
}