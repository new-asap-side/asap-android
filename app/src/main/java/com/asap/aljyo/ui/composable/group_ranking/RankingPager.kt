package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.asap.domain.entity.remote.GroupRanking

@Composable
fun RankingPager(
    modifier: Modifier,
    mIndex: Int,
    ranks: List<GroupRanking>,
    unranks: List<GroupRanking>,
) {
    val pagerState = rememberPagerState { 2 }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
    ) { index ->
        when(index) {
            0 -> TotalRankingPage(
                modifier = modifier,
                mIndex = mIndex,
                ranks = ranks,
                unranks = unranks,
            )
            1 -> Unit
        }
    }
}