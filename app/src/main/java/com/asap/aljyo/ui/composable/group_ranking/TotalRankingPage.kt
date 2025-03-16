package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.GroupRanking

@Stable
@Composable
fun TotalRankingPage(
    modifier: Modifier,
    mIndex: Int,
    ranks: List<GroupRanking>,
    unranks: List<GroupRanking>
) {
    Column(modifier = modifier) {
        RankingArea(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 60.dp
                ),
            rankings = ranks,
            mIndex = mIndex
        )

        UnRankingArea(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    offsetY = (-1).dp
                )
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(White)
                .padding(28.dp)
                .weight(1f),
            unRakings = unranks,
            mIndex = mIndex
        )
    }
}