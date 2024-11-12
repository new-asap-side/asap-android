@file:OptIn(ExperimentalMaterial3Api::class)

package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@Composable
fun GroupDetailsScreen() {
    AljyoTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            alpha = 0f
                        ),
                    ),
                    title = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            NextAlarmTimer(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100))
                                    .background(color = White.copy(alpha = 0.88f))
                                    .padding(
                                        vertical = 6.dp,
                                        horizontal = 14.dp
                                    )
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                tint = White,
                                contentDescription = "Top app bar first action"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                tint = White,
                                contentDescription = "Top app bar navigation icon"
                            )
                        }
                    }
                )
            },
        ) { padding ->
            Surface(
                modifier = Modifier
                    .padding(bottom = padding.calculateBottomPadding())
                    .fillMaxSize(),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        GroupSummation(modifier = Modifier.fillMaxWidth())
                    }
                    item {
                        AlarmDetails(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                        )
                    }
                    item {
                        GroupMembers(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                                .padding(
                                    vertical = 24.dp,
                                    horizontal = 20.dp
                                ),
                            count = 3
                        )
                    }
                }
            }
        }
    }

}