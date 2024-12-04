package com.asap.aljyo.components.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.components.usersetting.UserSettingScreen
import com.asap.aljyo.ui.composable.group_details.GroupDetailsScreen
import com.asap.aljyo.ui.composable.main.MainScreen
import com.asap.aljyo.ui.composable.main.alarm_list.AlarmListScreen
import com.asap.aljyo.ui.composable.main.home.HomeScreen
import com.asap.aljyo.ui.composable.main.my_page.MyPageScreen
import com.asap.aljyo.ui.composable.onboarding.OnboardingScreen


private const val TAG = "ApplicationNavHost"

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Onboarding.route,
    ) {
        composable(route = ScreenRoute.Onboarding.route) {
            OnboardingScreen(
                onLoginComplete = {
                    Log.d(TAG, "navigate to main")
                    navController.navigate(ScreenRoute.Main.route) {
                        popUpTo(route = ScreenRoute.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = ScreenRoute.Main.route) {
            MainScreen(
                screenNavController = navController
            )
        }

        composable(route = ScreenRoute.GroupDetails.route) {
            GroupDetailsScreen(
                navController = navController
            )
        }

        composable(route = ScreenRoute.UserSetting.route) {
            UserSettingScreen(
                navigateToMain = {
                    navController.navigate(ScreenRoute.Main.route) {
                        popUpTo(0) {inclusive = true}
                    }
                },
                onBackClick = {}
            )
        }
    }
}

// main screen
@Composable
fun MainNavHost(
    screenNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreenRoute.Home.route
    ) {
        val navigateToGroupDetails = {
            screenNavController.navigate(ScreenRoute.GroupDetails.route)
        }
        composable(route = MainScreenRoute.Home.route) {
            HomeScreen(
                onGroupItemClick = navigateToGroupDetails
            )
        }

        composable(route = MainScreenRoute.AlarmList.route) {
            AlarmListScreen()
        }

        composable(route = MainScreenRoute.MyPage.route) {
            MyPageScreen()
        }
    }
}