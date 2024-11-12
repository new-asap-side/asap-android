package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.main.home.GroupItem

@Composable
fun NewGroupList(
    modifier: Modifier = Modifier,
    tabChange: (Int) -> Unit,
    navigate: () -> Unit,
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.heightIn(max = 1000.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            userScrollEnabled = false
        ) {
            items(6) {
                GroupItem(
                    modifier = Modifier.clickable {
                        navigate()
                    }
                )
            }
        }
    }

}