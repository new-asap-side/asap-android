package com.asap.aljyo.components.group_details

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.asap.aljyo.components.ExtrasKey
import com.asap.aljyo.ui.composable.group_details.GroupDetailsScreen
import com.asap.aljyo.ui.composable.group_details.NextAlarmTimer
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.AlarmGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailsActivity : ComponentActivity() {
    private val alarmGroupItem: AlarmGroup? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(ExtrasKey.ALARM_GROUP_KEY, AlarmGroup::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(ExtrasKey.ALARM_GROUP_KEY)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
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
                                    onClick = { finish() }
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
                        GroupDetailsScreen()
                    }
                }
            }
        }
    }
}