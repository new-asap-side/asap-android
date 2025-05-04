package com.asap.aljyo.ui.composable.group_ranking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
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
    Box(
        modifier = Modifier.fillMaxSize()
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
//                .dropShadow(
//                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
//                    offsetY = (-1).dp
//                )
//                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
//                .background(White)
                    .padding(horizontal = 28.dp)
                    .weight(1f),
                unRankings = unranks,
                mIndex = mIndex
            )
        }
        val myRankInfo = (ranks + unranks).getOrNull(mIndex)

        if (myRankInfo != null) {
            MyRankingBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                rankInfo = myRankInfo
            )
        }
    }
}