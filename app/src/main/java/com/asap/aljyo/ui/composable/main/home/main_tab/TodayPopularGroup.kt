package com.asap.aljyo.ui.composable.main.home.main_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.overscroll.CustomOverscroll
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.aljyo.ui.theme.AljyoTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayPopularGroup(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val popularListState = rememberLazyListState()
    val overscrollEffect = remember(coroutineScope) {
        CustomOverscroll.HorizontalClamping(scope = coroutineScope)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SeeMoreTitle(
            title = stringResource(R.string.today_popular_group_title)
        ) { }
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
            items(5) {
                GroupItem(modifier = Modifier.width(148.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayPopularGroupPreview() {
    AljyoTheme {
        TodayPopularGroup()
    }
}