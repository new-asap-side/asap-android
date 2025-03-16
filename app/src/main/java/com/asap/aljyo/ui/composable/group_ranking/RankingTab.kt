package com.asap.aljyo.ui.composable.group_ranking

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White


sealed class RankingTabItem(@StringRes val title: Int) {
    data object TotalRankingItem : RankingTabItem(title = R.string.total_ranking)
    data object TodayRankingItem : RankingTabItem(title = R.string.today_ranking)
}

private val rankingTabs = listOf(
    RankingTabItem.TotalRankingItem,
    RankingTabItem.TodayRankingItem
)

@Composable
fun RankingTab(
    modifier: Modifier,
    tabIndex: Int
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex,
        containerColor = White,
        contentColor = Black01,
        indicator = { positions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(positions[tabIndex])
                    .padding(horizontal = 20.dp)
                    .height(2.dp)
                    .background(Black01)
            )
        },
        divider = {}
    ) {
        rankingTabs.forEach { item ->

        }
    }

}

@Preview
@Composable
private fun RankingTabPreview() {
    AljyoTheme {
        RankingTab(
            modifier = Modifier
                .width(360.dp)
                .height(48.dp),
            tabIndex = 0
        )
    }
}