package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.composable.main.home.GroupItemShimmer

@Composable
fun NewGroupList(
    modifier: Modifier = Modifier,
    tabChange: (Int) -> Unit,
    onGroupItemClick: (Boolean, Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SeeMoreTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.new_group_title)
        ) {
            tabChange(2)
        }

        val latestGroupState by viewModel.latestGroupState.collectAsState()
        val gridState = rememberLazyGridState()

        LazyVerticalGrid(
            state = gridState,
            modifier = Modifier.heightIn(max = 2200.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            userScrollEnabled = false
        ) {
            when (latestGroupState) {
                is UiState.Error -> Unit
                UiState.Loading -> {
                    item(6) { GroupItemShimmer() }
                }

                is UiState.Success -> {
                    val groups = (latestGroupState as UiState.Success).data ?: emptyList()

                    groups.forEachIndexed { index, group ->
                        if (index > 7) {
                            return@forEachIndexed
                        }
                        item {
                            GroupItem(
                                modifier = Modifier.clickable {
                                    onGroupItemClick(group.isPublic, group.groupId)
                                },
                                alarmGroup = group
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

}