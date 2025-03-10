package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.main.LatestViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.main.home.LinearGroupItem
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White

@Composable
fun NewGroupList(
    modifier: Modifier = Modifier,
    tabChange: (Int) -> Unit,
    onGroupItemClick: (Boolean, Int) -> Unit,
) {
    val latestViewModel: LatestViewModel = hiltViewModel()
    val latestGroupState by latestViewModel.latestState.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier.heightIn(max = 2000.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false
    ) {
        item {
            ItemTitle (
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.new_group_title)
            ) {
                tabChange(2)
            }
        }

        when (latestGroupState) {
            is UiState.Error, UiState.Loading -> Unit

            is UiState.Success -> {
                val groups = (latestGroupState as UiState.Success).data

                groups.forEachIndexed { index, group ->
                    if (index > 7) {
                        return@forEachIndexed
                    }
                    item {
                        LinearGroupItem(
                            modifier = Modifier.clickable {
                                onGroupItemClick(group.isPublic, group.groupId)
                            },
                            alarmGroup = group
                        )
                    }
                }

                item {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .border(width = 1.dp, color = Black03, shape = RoundedCornerShape(10.dp)),
                        onClick = { tabChange(2) },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = White,
                            contentColor = Black03
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.see_more),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 16.fsp,
                                )
                            )

                            Icon(
                                Icons.AutoMirrored.Default.KeyboardArrowRight,
                                contentDescription = "close"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}