package com.asap.aljyo.ui.composable.aljyo_descript

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.pager.AljyoPager
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AljyoDescriptScreen(
    onBackPress: () -> Unit,
) {
    AljyoTheme {
        Scaffold(
            containerColor = White,
            contentWindowInsets = WindowInsets(10.dp),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = White
                    ),
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { onBackPress() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_top_back),
                                modifier = Modifier.size(20.dp),
                                tint = Black01,
                                contentDescription = "Top app bar navigation icon"
                            )
                        }
                    }
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding() - 56.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .fillMaxSize()
            ) {
                AljyoPager(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}