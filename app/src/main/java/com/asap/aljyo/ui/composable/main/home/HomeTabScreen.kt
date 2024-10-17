package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White

sealed class TabItem(val titleId: Int) {
    data object MainTabItem : TabItem(titleId = R.string.main)
    data object SortedByPopularity : TabItem(titleId = R.string.sorted_by_populrity)
    data object SortedByLatest : TabItem(titleId = R.string.sorted_by_latest)
}

private val tabItems = listOf(
    TabItem.MainTabItem,
    TabItem.SortedByPopularity,
    TabItem.SortedByLatest,
)

@Composable
fun HomeTabScreen(modifier: Modifier = Modifier) {
    var tabIndex by remember { mutableIntStateOf(0) }
    Column {
        ScrollableTabRow(
            modifier = modifier,
            selectedTabIndex = tabIndex,
            containerColor = White,
            edgePadding = 12.dp,
            contentColor = White,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Black01
                )
            },
            divider = {}
        ) {
            tabItems.forEachIndexed { index, item ->
                val selected = index == tabIndex
                val badge = if (index == 2) " NEW!" else ""

                val annotatedString = buildAnnotatedString {
                    append(text = stringResource(item.titleId))
                    withStyle(
                        style = SpanStyle(
                            color = Red01,
                            fontSize = 11.sp,
                            baselineShift = BaselineShift(0.3f)
                        ),
                    ) {
                        append(text = badge)
                    }
                }
                Tab(
                    modifier = Modifier.height(48.dp),
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
                                fontSize = 15.sp
                            )
                        } else {
                            MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp,
                            )
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTabScreenPreview() {
    AljyoTheme {
        HomeTabScreen()
    }
}