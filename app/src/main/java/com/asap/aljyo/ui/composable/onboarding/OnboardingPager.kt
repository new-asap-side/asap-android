package com.asap.aljyo.ui.composable.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.pager.GoodDay
import com.asap.aljyo.ui.composable.common.pager.HardObject
import com.asap.aljyo.ui.composable.common.pager.PagerIndicator
import com.asap.aljyo.ui.composable.common.pager.ReleaseAlarm
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
fun OnboardingPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 4 })

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(state = pagerState) { page ->
            val pageModifier =  Modifier.size(480.dp)
            when (page) {
                0 -> {
                    Box(modifier = pageModifier) {
                        Image(
                            modifier = Modifier.align(Alignment.Center),
                            painter = painterResource(R.drawable.img_onboarding_main),
                            contentDescription = "onboarding main image"
                        )
                    }
                }
                1 -> HardObject(modifier = pageModifier)
                2 -> ReleaseAlarm(modifier = pageModifier)
                3 -> GoodDay(modifier = pageModifier)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
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
    AljyoTheme {
        OnboardingPager(Modifier.fillMaxSize())
    }
}