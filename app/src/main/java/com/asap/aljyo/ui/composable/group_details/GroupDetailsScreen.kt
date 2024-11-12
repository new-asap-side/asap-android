@file:OptIn(ExperimentalMaterial3Api::class)

package com.asap.aljyo.ui.composable.group_details

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.UserGroupType

@Composable
fun GroupDetailsScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    SideEffect {
        val activity = (context as ComponentActivity)
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb(),
            )
        )
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

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
                            onClick = {

                            }
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
                            onClick = { navController.popBackStack() }
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
            bottomBar = {
                GroupBottomNavBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White)
                        .padding(20.dp),
                    userGroupType = UserGroupType.Leader
                )
            }
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