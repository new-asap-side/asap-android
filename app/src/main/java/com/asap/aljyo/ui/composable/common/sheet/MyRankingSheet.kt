package com.asap.aljyo.ui.composable.common.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.MyRankingViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.theme.Black01

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRankingSheet(
    modifier: Modifier,
    navigateToRanking: (Int) -> Unit
) {
    val myRankingViewModel: MyRankingViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState()
    val state by myRankingViewModel.sheetState.collectAsStateWithLifecycle()

    if (state.show) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(Unit) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myRankingViewModel.fetchMyRanking()
            }
        }

        BottomSheet(
            sheetState = sheetState,
            modifier = modifier,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.my_ranking),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 18.fsp,
                            color = Black01
                        )
                    )

                    Icon(
                        Icons.Default.Close,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { myRankingViewModel.hideSheet() },
                        contentDescription = "close"
                    )
                }
            },
            onDismissRequest = { myRankingViewModel.hideSheet() },
        ) {
            when (state.myRanks) {
                is UiState.Error -> {
                    myRankingViewModel.hideSheet()
                }

                UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    }
                }

                is UiState.Success -> {
                    (state.myRanks as UiState.Success).data.run {
                        if (isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.no_exist_my_alarm),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontSize = 15.fsp,
                                        color = Black01
                                    )
                                )
                            }

                            return@run
                        }
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .layout { measurable, constraint ->
                                    measurable
                                        .measure(
                                            Constraints(
                                                maxWidth = constraint.maxWidth,
                                                minHeight = 400,
                                                maxHeight = 1600
                                            )
                                        )
                                        .run { layout(width, height) { place(0, 0) } }
                                }
                        ) {
                            forEach { (groupId, title, rankNumber) ->
                                item {
                                    Row(
                                        modifier = Modifier
                                            .clickable { navigateToRanking(groupId) }
                                            .fillMaxWidth()
                                            .padding(vertical = 14.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = title,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontSize = 15.fsp,
                                                color = Black01
                                            )
                                        )

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${rankNumber}등",
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontSize = 15.fsp,
                                                    color = Black01
                                                ),
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            Icon(
                                                painter = painterResource(R.drawable.ic_arrow_right),
                                                contentDescription = "right arrow"
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
    }
}