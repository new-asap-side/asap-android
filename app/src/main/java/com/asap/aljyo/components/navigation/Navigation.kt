package com.asap.aljyo.components.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.components.usersetting.UserSettingScreen
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.asap.aljyo.components.group_form.GroupFormViewModel
import com.asap.aljyo.ui.composable.alarm_result.AlarmResultScreen
import com.asap.aljyo.ui.composable.group_details.GroupDetailsScreen
import com.asap.aljyo.ui.composable.group_form.group_alarm.AlarmMusicScreen
import com.asap.aljyo.ui.composable.group_form.group_alarm.AlarmSettingScreen
import com.asap.aljyo.ui.composable.group_form.group_alarm.AlarmTypeScreen
import com.asap.aljyo.ui.composable.group_form.group_create.CreateGroupScreen
import com.asap.aljyo.ui.composable.group_form.group_type.SelectGroupTypeScreen
import com.asap.aljyo.ui.composable.group_ranking.RankingScreen
import com.asap.aljyo.ui.composable.main.MainScreen
import com.asap.aljyo.ui.composable.main.alarm_list.AlarmListScreen
import com.asap.aljyo.ui.composable.main.home.HomeScreen
import com.asap.aljyo.ui.composable.main.my_page.MyPageScreen
import com.asap.aljyo.ui.composable.onboarding.OnboardingScreen
import com.asap.aljyo.ui.composable.release_alarm.ReleaseAlarmScreen


private const val TAG = "ApplicationNavHost"

@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.GroupType.route,
//        startDestination = ScreenRoute.ReleaseAlarm.route,
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
            route = ScreenRoute.ReleaseAlarm.route,
        ) {
            ReleaseAlarmScreen(
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
            Log.d(TAG, "illust index: $index")
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

        groupCreateNavGraph(navController)
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
            AlarmListScreen()
        }

        composable(route = MainScreenRoute.MyPage.route) {
            MyPageScreen()
        }
    }
}

fun NavGraphBuilder.groupCreateNavGraph(
    navController: NavHostController
) {
    composable(route = ScreenRoute.GroupType.route) {
        SelectGroupTypeScreen(
            viewModel = hiltViewModel(),
            navigateToCreateGroup = {
                navController.navigate(ScreenRoute.GroupCreate.route)
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable(route = ScreenRoute.GroupCreate.route) {
        CreateGroupScreen(
            viewModel = hiltViewModel(navController.getBackStackEntry(ScreenRoute.GroupType.route)),
            onBackClick = { navController.popBackStack() },
            onNextClick = { navController.navigate(ScreenRoute.AlarmType.route) }
        )
    }

    composable(route = ScreenRoute.AlarmType.route) {
        AlarmTypeScreen(
            viewModel = hiltViewModel(navController.getBackStackEntry(ScreenRoute.GroupType.route)),
            onBackClick = { navController.popBackStack() },
            navigateToAlarmSetting = {
                navController.navigate(ScreenRoute.AlarmSetting.route)
            }
        )
    }

    composable(route = ScreenRoute.AlarmSetting.route) {
        AlarmSettingScreen(
            viewModel = hiltViewModel(navController.getBackStackEntry(ScreenRoute.GroupType.route)),
            onBackClick = { navController.popBackStack() },
            navigateToAlarmMusicScreen = { navController.navigate(ScreenRoute.AlarmMusic.route) },
            onCompleteClick = {}
        )
    }

    composable(route = ScreenRoute.AlarmMusic.route) {
        AlarmMusicScreen(
            viewModel = hiltViewModel(navController.getBackStackEntry(ScreenRoute.GroupType.route)),
            onBackClick = { navController.popBackStack() },
            onCompleteClick = { navController.popBackStack() }
        )
    }
}