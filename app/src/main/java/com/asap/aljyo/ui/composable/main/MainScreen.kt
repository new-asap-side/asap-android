package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.navigation.MainNavHost
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.aljyo.ui.composable.main.home.main.CreateGroupButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@Composable
internal fun MainScreen(
    screenNavController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    AljyoTheme {
        val mainNavController = rememberNavController()
        val selectedIndex by mainViewModel.selectedIndex.collectAsState()

        Scaffold(
            containerColor = White,
            topBar = {
                if (selectedIndex == 0) {
                    AljyoTopAppBar()
                }
            },
            floatingActionButton = {
                if (selectedIndex == 0) {
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
                    navController = mainNavController,
                    mainViewModel = mainViewModel
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
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}