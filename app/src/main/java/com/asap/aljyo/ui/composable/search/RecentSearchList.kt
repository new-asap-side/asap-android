package com.asap.aljyo.ui.composable.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.SearchViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState

@Composable
fun RecentSearchList(modifier: Modifier) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchedListState by viewModel.searchedList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSearchedList()
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.recent_search),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.fsp,
                    color = Color(0xFF111111),
                )
            )

            Text(
                text = stringResource(R.string.delete_all),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.fsp,
                    color = Color(0xFF111111),
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            when (searchedListState) {
                UiState.Loading, is UiState.Error-> Unit
                is UiState.Success -> {
                    (searchedListState as UiState.Success).data.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.query,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 15.fsp,
                                    color = Color(0xFF666666),
                                )
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.date,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontSize = 15.fsp,
                                        color = Color(0xFF666666),
                                    )
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { viewModel.delete(item.query) }
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "close",
                                        tint = Color(0xFF666666)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}