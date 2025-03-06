package com.asap.aljyo.ui.composable.search

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.Black00
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SearchTopBar(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit,
) {
    with (sharedTransitionScope) {
        TopAppBar(
            modifier = modifier,
            title = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        modifier = Modifier.sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "search-title"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                        painter = painterResource(R.drawable.ic_top_back),
                        contentDescription = "back button icon"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White,
                titleContentColor = Black00
            )
        )
    }
}