package com.asap.aljyo.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asap.aljyo.ui.composable.group_details.GroupDetailsScreen
import com.asap.aljyo.ui.composable.main.MainScreen
import com.asap.aljyo.ui.composable.onboarding.OnboardingScreen

sealed class AppRoute(val route: String) {
    data object Onboarding: AppRoute(route = "ON_BOARDING")
    data object Main: AppRoute(route = "MAIN")
    data object GroupDetails: AppRoute(route = "GROUP_DETAILS")
}

@Composable
internal fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Onboarding.route,
    ) {
        composable(route = AppRoute.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }

        composable(route = AppRoute.Main.route) {
            MainScreen(appNavController = navController)
        }

        composable(route = AppRoute.GroupDetails.route) {
            GroupDetailsScreen(navController = navController)
        }
    }
}