package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.ui.composable.common.Banner
import com.asap.aljyo.ui.theme.White

@Composable
fun MainTab(
    tabChange: (Int) -> Unit,
    navigateToDescript: () -> Unit,
    navigateToMyAlarm: () -> Unit,
    onGroupItemClick: (Boolean, Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollInfo = viewModel.scrollPositionMap[HomeViewModel.MAIN] ?: Pair(0, 0)
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollInfo.first,
        initialFirstVisibleItemScrollOffset = scrollInfo.second
    )

    DisposableEffect(scrollState) {
        onDispose {
            val index = scrollState.firstVisibleItemIndex
            val offset = scrollState.firstVisibleItemScrollOffset
            viewModel.saveScrollPosition(
                HomeViewModel.MAIN,
                index, offset
            )
        }
    }

    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(28.dp))

            SuccessRateCard(
                modifier = Modifier
                    .background(color = White)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                navigateToDescript = navigateToDescript,
                navigateToMyAlarm = navigateToMyAlarm
            )
        }

        item {
            TodayPopularGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                tabChange = tabChange,
                onGroupItemClick = onGroupItemClick
            )
        }

        item {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                navigateToDescript = navigateToDescript
            )
        }

        item {
            NewGroupList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                tabChange = tabChange,
                onGroupItemClick = onGroupItemClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
