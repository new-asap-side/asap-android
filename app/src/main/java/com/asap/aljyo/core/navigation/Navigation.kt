package com.asap.aljyo.core.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.asap.aljyo.core.components.usersetting.UserSettingScreen
import com.asap.aljyo.core.navigation.navtype.AlarmNavType
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
import com.asap.aljyo.ui.composable.preferences.PreferencesScreen
import com.asap.aljyo.ui.composable.release_alarm.ReleaseAlarmScreen
import com.asap.aljyo.ui.composable.withdrawal.WithdrawalScreen
import com.asap.aljyo.ui.composable.withdrawal_complete.WithdrawalCompleteScreen
import com.asap.domain.entity.remote.Alarm
import com.google.gson.Gson


@Composable
internal fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Onboarding.route,
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
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "aljyo://${ScreenRoute.ReleaseAlarm.route}/{${AlarmNavType.name}}"
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
                        popUpTo("${ScreenRoute.ReleaseAlarm.route}/{${AlarmNavType.name}}") {
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

        composable(
            route = ScreenRoute.Preferences.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = { defaultExitTransition() },
            popEnterTransition = null
        ) {
            PreferencesScreen(
                onBackIconPressed = {
                    navController.popBackStack()
                },
                navigateToWithdrawal = {
                    navController.navigate(ScreenRoute.Withdrawal.route)
                }
            )
        }

        composable(
            route = ScreenRoute.Withdrawal.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = { defaultExitTransition() },
            popEnterTransition = null,
        ) {
            WithdrawalScreen(
                onBackIconPressed = { navController.popBackStack() },
                navigateToComplete = {
                    navController.navigate(ScreenRoute.WithdrawalComplete.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(
            route = ScreenRoute.WithdrawalComplete.route,
            enterTransition = { defaultEnterTransition() },
        ) {
            WithdrawalCompleteScreen(
                navigateToOnboarding = {

                }
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
                navigateToReleaseAlarm = { alarm ->
                    val json = Gson().toJson(alarm)
                    screenNavController.navigate("${ScreenRoute.ReleaseAlarm.route}/$json")
                },
                onCreateButtonClick = { screenNavController.navigate(ScreenRoute.GroupType.route) },
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

        composable(
            route = MainScreenRoute.MyPage.route,
        ) {
            MyPageScreen(
                navigateToPreferences = {
                    screenNavController.navigate(ScreenRoute.Preferences.route)
                },
                navigateToOnboarding = {
                    screenNavController.navigate(ScreenRoute.Onboarding.route) {
                        popUpTo(0)
                    }
                }
            )
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