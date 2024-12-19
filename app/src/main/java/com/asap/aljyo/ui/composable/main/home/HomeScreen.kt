package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.main.home.main.NewGroupButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.Alarm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToReleaseAlarm: (Alarm) -> Unit,
    onCreateButtonClick: () -> Unit,
    onGroupItemClick: (Int) -> Unit,
) {
    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                TopAppBar(
                    windowInsets = WindowInsets(0.dp),
                    modifier = Modifier
                        .height(44.dp)
                        .background(White)
                        .padding(top = 8.dp, bottom = 8.dp, start = 20.dp),
                    title = {
                        Image(
                            painter = painterResource(R.drawable.ic_aljo),
                            contentDescription = "Title bar icon",
                            contentScale = ContentScale.FillHeight
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
                )
            },
            floatingActionButton = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    FloatingActionButton(
                        contentColor = White,
                        containerColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 5.dp
                        ),
                        onClick = {
                            navigateToReleaseAlarm(Alarm(alarmUnlockContents = "card"))
                        }
                    ) {
                        Text("Card")
                    }

                    FloatingActionButton(
                        contentColor = White,
                        containerColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 5.dp
                        ),
                        onClick = {
                            navigateToReleaseAlarm(Alarm(alarmUnlockContents = "drag"))
                        }
                    ) {
                        Text("Drag")
                    }

                    NewGroupButton(onClick = onCreateButtonClick)
                }
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                color = White
            ) {
                HomeTabScreen(
                    onGroupItemClick = onGroupItemClick
                )
            }
        }
    }
}