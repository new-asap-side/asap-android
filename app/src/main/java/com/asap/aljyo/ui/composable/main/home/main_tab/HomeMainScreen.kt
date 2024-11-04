package com.asap.aljyo.ui.composable.main.home.main_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.White

@Composable
fun HomeMainScreen() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Grey01)
        )
        ResultCardWrapper(
            modifier = Modifier
                .background(color = White)
                .padding(vertical = 32.dp, horizontal = 20.dp)
                .fillMaxWidth()
        )
        TodayPopularGroup(
            modifier = Modifier.background(color = White)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Banner(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
    }
}