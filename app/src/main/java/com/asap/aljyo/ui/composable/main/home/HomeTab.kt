package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.main.home.latest.LatestScreen
import com.asap.aljyo.ui.composable.main.home.main.MainTab
import com.asap.aljyo.ui.composable.main.home.popularity.PopularityScreen
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.White

sealed class HomeTabItem(val titleId: Int) {
    data object MainHomeTabItem : HomeTabItem(titleId = R.string.main)
    data object SortedByPopularity : HomeTabItem(titleId = R.string.sorted_by_populrity)
    data object SortedByLatest : HomeTabItem(titleId = R.string.sorted_by_latest)
}

private val homeTabItems = listOf(
    HomeTabItem.MainHomeTabItem,
    HomeTabItem.SortedByPopularity,
    HomeTabItem.SortedByLatest,
)

@Composable
fun HomeTab(
    modifier: Modifier = Modifier,
    navigateToDescript: () -> Unit,
    navigateToMyAlarm: () -> Unit,
    onGroupItemClick: (Boolean, Int) -> Unit,
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier) {
        ScrollableTabRow(
            modifier = modifier,
            selectedTabIndex = tabIndex,
            containerColor = White,
            edgePadding = 0.dp,
            contentColor = White,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Black01
                )
            },
            divider = {}
        ) {
            homeTabItems.forEachIndexed { index, item ->
                val selected = index == tabIndex
                val badge = if (index == 2) " NEW!" else ""

                val annotatedString = buildAnnotatedString {
                    append(text = stringResource(item.titleId))
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.fsp,
                            baselineShift = BaselineShift(0.3f)
                        ),
                    ) {
                        append(text = badge)
                    }
                }
                Tab(
                    modifier = Modifier.height(48.dp).fillMaxWidth(),
                    selected = selected,
                    selectedContentColor = Black01,
                    unselectedContentColor = Black03,
                    onClick = {
                        tabIndex = index
                    },
                ) {
                    Text(
                        text = annotatedString,
                        style = if (selected) {
                            MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 15.fsp
                            )
                        } else {
                            MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.fsp,
                            )
                        }
                    )

                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Grey01)
        )
        when (tabIndex) {
            0 -> MainTab(
                tabChange = { tabIndex = it },
                navigateToDescript = navigateToDescript,
                navigateToMyAlarm = navigateToMyAlarm,
                onGroupItemClick = onGroupItemClick
            )

            1 -> PopularityScreen(onGroupItemClick = onGroupItemClick)
            2 -> LatestScreen(onGroupItemClick = onGroupItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTabScreenPreview() {
    AljyoTheme {
        HomeTab(
            navigateToDescript = {},
            navigateToMyAlarm = {},
            onGroupItemClick = { _, _ -> }
        )
    }
}