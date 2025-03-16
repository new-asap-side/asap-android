package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.asap.domain.entity.remote.GroupRanking

@Composable
fun RankingPager(
    modifier: Modifier,
    state: PagerState,
    mIndex: Int,
    ranks: List<GroupRanking>,
    unranks: List<GroupRanking>,
) {
    HorizontalPager(
        modifier = modifier,
        state = state,
    ) { index ->
        when (index) {
            0 -> TotalRankingPage(
                modifier = modifier,
                mIndex = mIndex,
                ranks = ranks,
                unranks = unranks,
            )

            1 -> {
                TodayRankingPage(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFFAF8F8)),
                    ranks = ranks
                )
            }
        }
    }
}