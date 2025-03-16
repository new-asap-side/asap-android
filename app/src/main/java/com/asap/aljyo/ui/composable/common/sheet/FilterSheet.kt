package com.asap.aljyo.ui.composable.common.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.main.Filter
import com.asap.aljyo.core.components.viewmodel.main.FilterViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    viewModel: FilterViewModel,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val hideSheet = {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onDismissRequest()
            }
        }
    }

    BottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.filter),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 18.fsp,
                        color = Black01
                    )
                )

                Icon(
                    Icons.Default.Close,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { hideSheet() },
                    contentDescription = "close"
                )
            }
        },
        content = {
            FilterList(
                modifier = Modifier,
                viewModel = viewModel
            )
        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
private fun FilterList(
    modifier: Modifier,
    viewModel: FilterViewModel
) {
    val filterState = viewModel.filterState
    val selectedStyle = MaterialTheme.typography.headlineMedium.copy(
        fontSize = 16.fsp,
        color = Black01
    )

    val unSelectedStyle = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 16.fsp,
        color = Black02
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.filter(Filter.Total) }
                .padding(vertical = 10.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "전체",
                style = if (filterState.value == Filter.Total) {
                    selectedStyle
                } else {
                    unSelectedStyle
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.filter(Filter.Public) }
                .padding(vertical = 10.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(R.string.public_group),
                style = if (filterState.value == Filter.Public) {
                    selectedStyle
                } else {
                    unSelectedStyle
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.filter(Filter.Private) }
                .padding(vertical = 10.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(R.string.private_group),
                style = if (filterState.value == Filter.Private) {
                    selectedStyle
                } else {
                    unSelectedStyle
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}