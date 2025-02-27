package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.core.navigation.MainNavHost
import com.asap.aljyo.core.navigation.MainScreenRoute
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.aljyo.ui.composable.main.home.main.CreateGroupButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@Composable
internal fun MainScreen(
    screenNavController: NavHostController
) {
    AljyoTheme {
        val mainNavController = rememberNavController()
        var homeScreen by remember { mutableStateOf(true) }

        Scaffold(
            containerColor = White,
            topBar = {
                if (homeScreen) {
                    AljyoTopAppBar()
                }
            },
            floatingActionButton = {
                if (homeScreen) {
                    CreateGroupButton(
                        modifier = Modifier,
                        onClick = {
                            screenNavController.navigate(ScreenRoute.GroupType.route)
                        }
                    )
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(64.dp),
                    onRouteChanged = { route ->
                        homeScreen = route == MainScreenRoute.Home.route
                    },
                    navController = mainNavController
                )
            }
        ) { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                color = White
            ) {
                MainNavHost(
                    screenNavController = screenNavController,
                    navController = mainNavController,
                )
            }
        }
    }
}