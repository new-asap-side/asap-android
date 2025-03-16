@file:OptIn(ExperimentalMaterial3Api::class)

package com.asap.aljyo.ui.composable.main

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.SharedElement
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AljyoTopAppBar(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navigateToSearch: () -> Unit,
    mainViewModel: MainViewModel
) {
    val selectedIndex by mainViewModel.selectedIndex.collectAsState()
    when (selectedIndex) {
        0 -> {
            HomeTopAppBar(
                modifier,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                navigateToSearch = navigateToSearch
            )
        }

        1 -> MyAlarmTopAppBar(modifier)
        2 -> MyPageTopAppBar(modifier)
        else -> Unit
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navigateToSearch: () -> Unit,
) {
    with (sharedTransitionScope) {
        TopAppBar(
            modifier = modifier,
            title = {
                Image(
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(SharedElement.SEARCH_TITLE),
                        animatedVisibilityScope = animatedContentScope
                    ),
                    painter = painterResource(R.drawable.ic_aljo),
                    contentDescription = "top bar icon",
                    contentScale = ContentScale.FillHeight
                )
            },
            actions = {
                Box(
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(SharedElement.SEARCH_BAR),
                        animatedVisibilityScope = animatedContentScope
                    )
                ) {
                    IconButton(
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(SharedElement.SEARCH_ICON),
                            animatedVisibilityScope = animatedContentScope
                        ),
                        onClick = {
                            Log.d("TopAppBar", "search icon click !")
                            navigateToSearch()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "top bar action[search]"
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
        )
    }
}

@Composable
private fun MyAlarmTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.alarm_list),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Black01,
                    fontSize = 16.fsp
                ),
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
    )
}

@Composable
private fun MyPageTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.my_page),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Black01,
                    fontSize = 16.fsp
                ),
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
    )
}