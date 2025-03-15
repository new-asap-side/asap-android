package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.main.PopularViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.composable.main.home.GroupItemShimmer
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
fun TodayPopularGroup(
    modifier: Modifier = Modifier,
    tabChange: (Int) -> Unit,
    onGroupItemClick: (Boolean, Int) -> Unit,
) {
    val viewmodel: PopularViewModel = hiltViewModel()
    val popularListState = rememberLazyGridState()
    val popularGroupsState by viewmodel.popularGroupState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ItemTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.today_popular_group_title)
        ) {
            tabChange(1)
        }

        when (popularGroupsState) {
            is UiState.Error -> Unit
            UiState.Loading -> {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(2) {
                        GroupItemShimmer(modifier = Modifier.width(148.dp))
                    }
                }
            }

            is UiState.Success -> {
                val popularGroups = (popularGroupsState as UiState.Success).data

                LazyVerticalGrid(
                    state = popularListState,
                    modifier = Modifier.heightIn(max = 2200.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    userScrollEnabled = false
                ) {
                    for ((index, alarmGroup) in popularGroups.withIndex()) {
                        if (index > 3) {
                            break
                        }

                        item {
                            GroupItem(
                                modifier = Modifier
                                    .width(148.dp)
                                    .clickable {
                                        onGroupItemClick(
                                            alarmGroup.isPublic,
                                            alarmGroup.groupId
                                        )
                                    },
                                alarmGroup = alarmGroup
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodayPopularGroupPreview() {
    AljyoTheme {
        TodayPopularGroup(
            tabChange = {},
            onGroupItemClick = { _, _ -> }
        )
    }
}
