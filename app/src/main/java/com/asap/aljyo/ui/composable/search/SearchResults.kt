package com.asap.aljyo.ui.composable.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.composable.main.home.GroupItem
import com.asap.domain.entity.remote.AlarmGroup

@Stable
@Composable
fun SearchResults(
    modifier: Modifier,
    groups: List<AlarmGroup>
) {
    val gridState = rememberLazyGridState()

    Column(modifier = modifier) {
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 20.dp),
            state = gridState,
            columns = GridCells.Fixed(2)
        ) {
            items(groups.size) {
                GroupItem(alarmGroup = groups[it])
            }
        }
    }
}