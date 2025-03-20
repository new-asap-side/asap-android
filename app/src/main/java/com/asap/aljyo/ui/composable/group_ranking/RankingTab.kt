package com.asap.aljyo.ui.composable.group_ranking

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.GroupRankingViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
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
    selectedIndex: Int,
    onTabSelect: (Int) -> Unit
) {
    val selectedStyle = MaterialTheme.typography.headlineMedium.copy(
        fontSize = 15.fsp
    )

    val unselectedStyle = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 15.fsp,
    )

    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedIndex,
        containerColor = White,
        contentColor = Black01,
        indicator = { positions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(positions[selectedIndex])
                    .padding(horizontal = 20.dp)
                    .height(2.dp)
                    .background(Black01)
            )
        },
        divider = {}
    ) {
        rankingTabs.forEachIndexed { index, item ->
            val selected = selectedIndex == index
            Tab(
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 20.dp),
                selected = selected,
                selectedContentColor = Black01,
                unselectedContentColor = Black03,
                onClick = {
                    onTabSelect(index)
                }
            ) {
                Text(
                    text = stringResource(item.title),
                    textAlign = TextAlign.Center,
                    style = if (selected) {
                        selectedStyle
                    } else {
                        unselectedStyle
                    }
                )
            }
        }
    }
}