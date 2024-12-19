package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.HomeViewModel
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.overscroll.CustomOverscroll
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.composable.main.home.GroupItemShimmer
import com.asap.aljyo.ui.theme.AljyoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayPopularGroup(
    modifier: Modifier = Modifier,
    tabChange: (Int) -> Unit,
    navigate: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val popularListState = rememberLazyListState()
    val overscrollEffect = remember(coroutineScope) {
        CustomOverscroll.HorizontalClamping(scope = coroutineScope)
    }

    val popularGroupsState by viewModel.popularGroupState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SeeMoreTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.today_popular_group_title)
        ) {
            tabChange(1)
        }

        when (popularGroupsState) {
            is UiState.Error -> Unit
            UiState.Loading -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) {
                        item {
                            GroupItemShimmer(modifier = Modifier.width(148.dp))
                        }
                    }
                }
            }

            is UiState.Success -> {
                val popularGroups = (popularGroupsState as UiState.Success).data ?: emptyList()

                LazyRow(
                    state = popularListState,
                    userScrollEnabled = false,
                    modifier = Modifier
                        .overscroll(overscrollEffect = overscrollEffect)
                        .scrollable(
                            orientation = Orientation.Horizontal,
                            reverseDirection = true,
                            state = popularListState,
                            overscrollEffect = overscrollEffect
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    popularGroups.forEach { alarmGroup ->
                        item {
                            GroupItem(
                                modifier = Modifier
                                    .width(148.dp)
                                    .clickable { navigate(alarmGroup.groupId) },
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
            navigate = {}
        )
    }
}
