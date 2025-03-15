package com.asap.aljyo.ui.composable.main.home.latest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.core.components.viewmodel.main.LatestViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.ErrorBox
import com.asap.aljyo.ui.composable.common.sheet.FilterSheet
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.composable.main.home.GroupItemShimmer

@Composable
fun LatestScreen(
    onGroupItemClick: (Boolean, Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val latestViewModel: LatestViewModel = hiltViewModel()
    val scrollInfo = viewModel.scrollPositionMap[HomeViewModel.LATEST_TAB_SCROLL_KEY] ?: Pair(0, 0)
    val scrollState = rememberLazyGridState(
        initialFirstVisibleItemIndex = scrollInfo.first,
        initialFirstVisibleItemScrollOffset = scrollInfo.second
    )

    val latestGroupState by latestViewModel.latestState.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

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

    if (latestGroupState is UiState.Error) {
        ErrorBox(modifier = Modifier.fillMaxSize()) {
            viewModel.fetchHomeData()
        }
        return
    }

    if (showFilterSheet) {
        FilterSheet(
            modifier = Modifier,
            onDismissRequest = { showFilterSheet = false },
            viewModel = latestViewModel
        )
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.wrapContentWidth().clickable { showFilterSheet = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "filter icon",
                    tint = Color.Unspecified
                )

                Text(
                    text = stringResource(R.string.filter),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp,
                        color = Color(0xFF111111)
                    )
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
                UiState.Loading -> {
                    repeat(6) {
                        item {
                            GroupItemShimmer(modifier = Modifier)
                        }
                    }
                }

                is UiState.Success -> {
                    val latestGroup = (latestGroupState as UiState.Success).data
                    latestGroup.forEach { group ->
                        item {
                            GroupItem(
                                modifier = Modifier.clickable {
                                    onGroupItemClick(group.isPublic, group.groupId)
                                },
                                alarmGroup = group,
                            )
                        }
                    }

                    if (latestGroup.size % 2 == 1) {
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
        
        Spacer(modifier = Modifier.height(50.dp))
    }

}