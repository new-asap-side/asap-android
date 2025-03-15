package com.asap.aljyo.ui.composable.search

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.core.components.viewmodel.SearchViewModel
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit,
) {
    var focused by remember { mutableStateOf(true) }
    val viewmodel: SearchViewModel = hiltViewModel()
    val query by viewmodel.query.collectAsState()
    val searchState by viewmodel.searchState.collectAsState()

    AljyoTheme {
        Scaffold(
            topBar = {
                SearchTopBar(
                    modifier = Modifier,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    onFocusChanged = { focused = it },
                    onBackClick = onBackClick
                )
            },
            containerColor = White
        ) { paddingValues ->
            if (query.isEmpty() or query.isBlank()) {
                RecentSearchList(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
                return@Scaffold
            }

            when (searchState) {
                RequestState.Initial -> Unit

                RequestState.Loading -> {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(50.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                is RequestState.Success -> {
                    val result = (searchState as RequestState.Success).data

                    SearchResults(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        groups = result
                    )
                }

                is RequestState.Error -> Unit
            }
        }
    }
}