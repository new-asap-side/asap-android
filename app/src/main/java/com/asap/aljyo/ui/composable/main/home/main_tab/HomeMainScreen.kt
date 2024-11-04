package com.asap.aljyo.ui.composable.main.home.main_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.White

@Composable
fun HomeMainScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        ResultCardWrapper(
            modifier = Modifier
                .background(color = White)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )
        TodayPopularGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        )
        Banner(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
        NewGroupList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}