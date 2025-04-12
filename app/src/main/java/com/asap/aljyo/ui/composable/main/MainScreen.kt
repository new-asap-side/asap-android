package com.asap.aljyo.ui.composable.main

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import asap.aljyo.core.event.Patch
import asap.aljyo.core.event.shortToast
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.navigation.MainNavHost
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.aljyo.ui.composable.main.home.main.CreateGroupButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.ExpiredTokenHandler
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainScreen(
    screenNavController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    SideEffect {
        systemUiController.setStatusBarColor(Color.White, darkIcons = true)
    }

    LaunchedEffect(Unit) {
        with(mainViewModel) {
            patchToken(scope)
            networkEvent.collect { event ->
                if (event is Patch) {
                    event.process(Unit).also(::println).await().let { success ->
                        if (!success) {
                            context.shortToast("토큰 갱신에 실패했습니다.")
                        }
                    }
                }
            }
        }
    }

    AljyoTheme {
        val mainNavController = rememberNavController()
        val selectedIndex by mainViewModel.selectedIndex.collectAsState()

        LaunchedEffect(Unit) {
            ExpiredTokenHandler.subscribe(
                scope = scope,
                block = {
                    mainViewModel.deleteUserInfo()
                    screenNavController.navigate(ScreenRoute.Onboarding.route) {
                        popUpTo(route = ScreenRoute.Main.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        Scaffold(
            containerColor = White,
            topBar = {
                AljyoTopAppBar(
                    mainViewModel = mainViewModel,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    navigateToSearch = {
                        Log.d("MainScreen", "navigate to search")
                        screenNavController.navigate(ScreenRoute.Search.route)
                    }
                )
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