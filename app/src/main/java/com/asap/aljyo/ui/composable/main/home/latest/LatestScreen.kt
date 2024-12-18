package com.asap.aljyo.ui.composable.main.home.latest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.ErrorBox
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.composable.main.home.GroupItemShimmer

@Composable
fun LatestScreen(
    navigateToGroupDetails: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollInfo = viewModel.scrollPositionMap[HomeViewModel.LATEST_TAB_SCROLL_KEY] ?: Pair(0, 0)
    val scrollState = rememberLazyGridState(
        initialFirstVisibleItemIndex = scrollInfo.first,
        initialFirstVisibleItemScrollOffset = scrollInfo.second
    )

    val latestGroupState by viewModel.latestGroupState.collectAsState()

    DisposableEffect(scrollState) {
        onDispose {
            val index = scrollState.firstVisibleItemIndex
            val offset = scrollState.firstVisibleItemScrollOffset
            viewModel.saveScrollPosition(
                HomeViewModel.LATEST_TAB_SCROLL_KEY,
                index, offset
            )
        }
    }

    LazyVerticalGrid(
        state = scrollState,
        modifier = Modifier.padding(horizontal = 20.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (latestGroupState) {
            is UiState.Error -> {
                item {
                    ErrorBox(modifier = Modifier.fillMaxSize()) {
                        viewModel.fetchHomeData()
                    }
                }
            }

            UiState.Loading -> {
                item(6) {
                    GroupItemShimmer(modifier = Modifier)
                }
            }

            is UiState.Success -> {
                val latestGroup = (latestGroupState as UiState.Success).data ?: emptyList()
                latestGroup.forEach { group ->
                    item {
                        GroupItem(
                            modifier = Modifier.clickable {
                                navigateToGroupDetails(group.groupId)
                            },
                            alarmGroup = group,
                        )
                    }
                }
            }
        }
    }
}