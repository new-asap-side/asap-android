package com.asap.aljyo.ui.composable.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.composable.common.pager.PagerIndicator
import com.asap.aljyo.ui.theme.Black01

@Composable
fun OnboardingPager(modifier: Modifier = Modifier) {
    // TODO 온보딩 서버 데이터 ?
    val pagerState = rememberPagerState(pageCount = { 4 })
    Column {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.align(alignment = Alignment.CenterHorizontally)
        ) { page ->
            Text(
                text = "$page / ${pagerState.pageCount}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Black01)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { currentPage ->
                val isSelected = pagerState.currentPage == currentPage
                PagerIndicator(isSelected = isSelected)
            }
        }
    }
}