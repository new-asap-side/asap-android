package com.asap.aljyo.ui.composable.main.home.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.components.main.HomeViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.White

@Composable
fun MainTabScreen(
    tabChange: (Int) -> Unit,
    navigate: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollInfo = viewModel.scrollPositionMap[HomeViewModel.MAIN_TAB_SCROLL_KEY] ?: Pair(0, 0)
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollInfo.first,
        initialFirstVisibleItemScrollOffset = scrollInfo.second
    )

    DisposableEffect(scrollState) {
        onDispose {
            val index = scrollState.firstVisibleItemIndex
            val offset = scrollState.firstVisibleItemScrollOffset
            viewModel.saveScrollPosition(
                HomeViewModel.MAIN_TAB_SCROLL_KEY,
                index, offset
            )
        }
    }

    Log.d("MainTabScreen", "composition call !")

    val uiState by viewModel.cardState.collectAsState()

    when (uiState) {
        is UiState.Error -> TODO()
        UiState.Loading -> Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }

        is UiState.Success ->
            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                item {
                    val state = uiState as UiState.Success
                    ResultCard(
                        modifier = Modifier
                            .background(color = White)
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        successRate = state.data?.successRate,
                        participatingGroup = state.data?.participatingGroup
                    )
                }
                item {
                    TodayPopularGroup(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                        tabChange = tabChange,
                        navigate = navigate
                    )
                }
                item {
                    Banner(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                    )
                }
                item {
                    NewGroupList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        tabChange = tabChange,
                        navigate = navigate
                    )
                }
            }

    }
}