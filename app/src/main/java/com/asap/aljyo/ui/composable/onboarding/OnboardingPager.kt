package com.asap.aljyo.ui.composable.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.composable.common.pager.PagerIndicator
import com.asap.aljyo.ui.theme.Black01

@Composable
fun OnboardingPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
        ) { page ->
            when (page) {
                0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 138.dp)
                    ) {
                        OnboardingMain()
                    }
                }

                else -> {
                    Text(
                        text = "$page / ${pagerState.pageCount}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Black01)
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 180.dp),
        ) {
            repeat(pagerState.pageCount) { currentPage ->
                val isSelected = pagerState.currentPage == currentPage
                PagerIndicator(isSelected = isSelected)
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun OnboardingPagerPreview() {
    OnboardingPager(Modifier.fillMaxSize())
}