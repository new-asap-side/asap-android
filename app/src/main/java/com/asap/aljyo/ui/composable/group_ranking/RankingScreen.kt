package com.asap.aljyo.ui.composable.group_ranking

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_ranking.GroupRankingViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.di.ViewModelFactoryProvider
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.ErrorBox
import com.asap.aljyo.ui.composable.common.dialog.LoadingDialog
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RankingScreen(
    onBackPressed: () -> Unit,
    groupId: Int
) {
    val context = LocalContext.current

    SideEffect {
        val activity = (context as ComponentActivity)
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb()
            ),
        )
    }

    AljyoTheme {
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        val coroutineScope = rememberCoroutineScope()

        val hideSheet = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 24.dp
                ),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                arrangement = Arrangement.spacedBy(16.dp),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.select_time),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 18.fsp,
                                color = Black01
                            )
                        )
                        IconButton(
                            onClick = { hideSheet() }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "close"
                            )
                        }
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.rank_score_descript),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.fsp,
                        color = Black02
                    )
                )
                Text(
                    text = stringResource(R.string.no_points_score),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.fsp,
                        color = Black02
                    )
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10),
                    onClick = { hideSheet() }
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.fsp,
                            color = White
                        )
                    )
                }
            }
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(vertical = 10.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = White
                    ),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.group_ranking),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.fsp,
                                color = Black01
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackPressed
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                tint = Black01,
                                contentDescription = "Top app bar navigation icon"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { showBottomSheet = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_question_mark),
                                tint = Color.Unspecified,
                                contentDescription = "Question mark icon"
                            )
                        }

                    }
                )
            },
        ) { paddingValues ->
            val factory = EntryPointAccessors.fromActivity(
                context as Activity,
                ViewModelFactoryProvider::class.java
            ).groupRankingViewModelFactory()

            val viewModel: GroupRankingViewModel = viewModel(
                factory = GroupRankingViewModel.provideGroupRankingViewModelFactory(
                    factory = factory,
                    groupId = groupId
                )
            )
            val state by viewModel.state.collectAsState()

            when (state) {
                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        ErrorBox(
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            viewModel.fetchGroupRanking()
                        }
                    }
                }

                UiState.Loading -> Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    LoadingDialog(
                        modifier = Modifier.align(Alignment.Center)
                    ) { }
                }

                is UiState.Success -> {
                    val mIndex = viewModel.mIndex ?: -1

                    Column(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        RankingArea(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 60.dp
                                ),
                            rankings = viewModel.getRankList(),
                            mIndex = mIndex
                        )
                        UnRankingArea(
                            modifier = Modifier
                                .fillMaxWidth()
                                .dropShadow(
                                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                                    offsetY = (-1).dp
                                )
                                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                                .background(White)
                                .padding(28.dp)
                                .weight(1f),
                            unRakings = viewModel.getUnRankList(),
                        )
                    }
                }
            }
        }
    }
}
