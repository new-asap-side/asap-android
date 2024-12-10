package com.asap.aljyo.core.components.navigation

import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.core.components.usersetting.UserSettingScreen
import androidx.navigation.navArgument
import com.asap.aljyo.core.components.navigation.navtype.AlarmNavType
import com.asap.aljyo.ui.composable.alarm_result.AlarmResultScreen
import com.asap.aljyo.ui.composable.group_details.GroupDetailsScreen
import com.asap.aljyo.ui.composable.group_ranking.RankingScreen
import com.asap.aljyo.ui.composable.main.MainScreen
import com.asap.aljyo.ui.composable.main.alarm_list.AlarmListScreen
import com.asap.aljyo.ui.composable.main.home.HomeScreen
import com.asap.aljyo.ui.composable.main.my_page.MyPageScreen
import com.asap.aljyo.ui.composable.onboarding.OnboardingScreen
import com.asap.aljyo.ui.composable.release_alarm.AlarmContent
import com.asap.aljyo.ui.composable.release_alarm.ReleaseAlarmScreen
import com.asap.domain.entity.remote.Alarm


private const val TAG = "ApplicationNavHost"

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
//        startDestination = ScreenRoute.Onboarding.route,
        startDestination = "${ScreenRoute.ReleaseAlarm.route}/{}",
    ) {
        composable(route = ScreenRoute.Onboarding.route) {
            OnboardingScreen(
                navigateToMain = {
                    navController.navigate(ScreenRoute.Main.route) {
                        popUpTo(route = ScreenRoute.Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToUserSetting = {
                    navController.navigate(ScreenRoute.UserSetting.route)
                }
            )
        }

        composable(route = ScreenRoute.Main.route) {
            MainScreen(
                screenNavController = navController
            )
        }

        composable(
            route = "${ScreenRoute.GroupDetails.route}/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.IntType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            GroupDetailsScreen(
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = "${ScreenRoute.Ranking.route}/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.IntType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            RankingScreen(
                onBackPressed = { navController.popBackStack() },
                groupId = groupId
            )
        }

        composable(
            route = "${ScreenRoute.ReleaseAlarm.route}/{${AlarmNavType.name}}",
            arguments = listOf(
                navArgument(name = AlarmNavType.name) {
                    type = AlarmNavType
                }
            )
        ) { navBackstackEntry ->
            val arguments = navBackstackEntry.arguments
            val alarm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(AlarmNavType.name, Alarm::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable(AlarmNavType.name)
            } ?: throw IllegalArgumentException("Argument is null!")

            ReleaseAlarmScreen(
                alarm = alarm,
                navigateToResult = { index ->
                    navController.navigate("${ScreenRoute.AlarmResult.route}/$index") {
                        popUpTo(route = ScreenRoute.ReleaseAlarm.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${ScreenRoute.AlarmResult.route}/{illustIndex}",
            arguments = listOf(navArgument("illustIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("illustIndex") ?: 0
            AlarmResultScreen(illustIndex = index)
        }

        composable(route = ScreenRoute.UserSetting.route) {
            UserSettingScreen(
                navigateToMain = {
                    navController.navigate(ScreenRoute.Main.route) {
                        popUpTo(0) { inclusive = true }
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
        val navigateToGroupDetails: (Int) -> Unit = { groupId ->
            screenNavController.navigate("${ScreenRoute.GroupDetails.route}/$groupId")
        }
        composable(route = MainScreenRoute.Home.route) {
            HomeScreen(
                onGroupItemClick = navigateToGroupDetails
            )
        }

        composable(route = MainScreenRoute.AlarmList.route) {
            AlarmListScreen(
                navigateToHome = {
                    navController.navigate(MainScreenRoute.Home.route) {
                        popUpTo(MainScreenRoute.AlarmList.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = MainScreenRoute.MyPage.route) {
            MyPageScreen()
        }
    }
}