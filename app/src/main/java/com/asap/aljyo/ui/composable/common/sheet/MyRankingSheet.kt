package com.asap.aljyo.ui.composable.common.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.MyRankingViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.Black01

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRankingSheet(modifier: Modifier) {
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
            LazyColumn {

            }
        }
    }
}