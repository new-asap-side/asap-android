package com.asap.aljyo.ui.composable.main.home.popularity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun PopularityScreen(
    onGroupItemClick: (Boolean, Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollInfo = viewModel.scrollPositionMap[HomeViewModel.POPULAR_TAB_SCROLL_KEY] ?: Pair(0, 0)
    val scrollState = rememberLazyGridState(
        initialFirstVisibleItemIndex = scrollInfo.first,
        initialFirstVisibleItemScrollOffset = scrollInfo.second
    )

    val popularityGroupState by viewModel.popularGroupState.collectAsState()

    DisposableEffect(scrollState) {
        onDispose {
            val index = scrollState.firstVisibleItemIndex
            val offset = scrollState.firstVisibleItemScrollOffset
            viewModel.saveScrollPosition(
                HomeViewModel.POPULAR_TAB_SCROLL_KEY,
                index, offset
            )
        }
    }

    if (popularityGroupState is UiState.Error) {
        ErrorBox(modifier = Modifier.fillMaxSize()) {
            viewModel.fetchHomeData()
        }
        return
    }

    LazyVerticalGrid(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 20.dp,
                end = 20.dp
            ),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (popularityGroupState) {
            UiState.Loading -> {
                repeat(6) {
                    item {
                        GroupItemShimmer(modifier = Modifier)
                    }
                }
            }

            is UiState.Success -> {
                val popularGroup = (popularityGroupState as UiState.Success).data ?: emptyList()
                popularGroup.forEach { group ->
                    item {
                        GroupItem(
                            modifier = Modifier.clickable {
                                onGroupItemClick(group.isPublic, group.groupId)
                            },
                            alarmGroup = group,
                        )
                    }
                }

                if (popularGroup.size % 2 == 1) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            else -> Unit
        }
    }
}