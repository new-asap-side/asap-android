package com.asap.aljyo.core.navigation

import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.asap.aljyo.core.components.edit.PersonalEditViewModel
import com.asap.aljyo.core.components.group_form.GroupFormViewModel
import com.asap.aljyo.core.components.usersetting.UserSettingScreen
import com.asap.aljyo.core.navigation.navtype.AlarmNavType
import com.asap.aljyo.core.navigation.navtype.CustomNavType
import com.asap.aljyo.ui.composable.alarm_result.AlarmResultScreen
import com.asap.aljyo.ui.composable.aljyo_descript.AljyoDescriptScreen
import com.asap.aljyo.ui.composable.group_details.GroupDetailsScreen
import com.asap.aljyo.ui.composable.group_edit.GroupEditScreen
import com.asap.aljyo.ui.composable.group_edit.PersonalEditScreen
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
import com.asap.aljyo.ui.composable.main.my_page.PrivacyPolicyScreen
import com.asap.aljyo.ui.composable.onboarding.OnboardingScreen
import com.asap.aljyo.ui.composable.preferences.PreferencesScreen
import com.asap.aljyo.ui.composable.release_alarm.ReleaseAlarmScreen
import com.asap.aljyo.ui.composable.report.ReportScreen
import com.asap.aljyo.ui.composable.withdrawal.WithdrawalScreen
import com.asap.aljyo.ui.composable.withdrawal_complete.WithdrawalCompleteScreen
import com.asap.domain.entity.remote.alarm.AlarmPayload


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
            route = "${ScreenRoute.GroupDetails.route}/{groupId}?isNew={isNew}",
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("isNew") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            enterTransition = { defaultEnterTransition() },
            exitTransition = { defaultExitTransition() },
            popEnterTransition = null,
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            val isNew = backStackEntry.arguments?.getBoolean("isNew") ?: false
            GroupDetailsScreen(
                navController = navController,
                isNew = isNew,
                groupId = groupId
            )
        }

        composable(
            route = "${ScreenRoute.Ranking.route}/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.IntType }),
            enterTransition = { defaultEnterTransition() },
            exitTransition = { defaultExitTransition() },
            popEnterTransition = null,
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
                arguments?.getParcelable(AlarmNavType.name, AlarmPayload::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable(AlarmNavType.name)
            } ?: throw IllegalArgumentException("Argument is null!")


            ReleaseAlarmScreen(
                alarm = alarm,
                navigateToResult = { index ->
                    val query = "/groupId=${alarm.groupId}?title=${alarm.groupTitle}/$index"
                    val route = "${ScreenRoute.AlarmResult.route}$query"
                    navController.navigate(route) {
                        popUpTo("${ScreenRoute.ReleaseAlarm.route}/{${AlarmNavType.name}}") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${ScreenRoute.AlarmResult.route}/groupId={groupId}?title={title}/{index}",
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType },
                // 알람 해제 완료 일러스트 인덱스
                navArgument("index") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Unknown"
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            AlarmResultScreen(
                groupId = groupId,
                title = title,
                index = index,
                navigateToHome = {
                    navController.navigate(ScreenRoute.Main.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                navigateToRanking = {
                    navController.navigate("${ScreenRoute.Ranking.route}/$groupId")
                }
            )
        }

        composable(route = ScreenRoute.UserSetting.route) {
            UserSettingScreen(
                isEditMode = false,
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
                        popUpTo(0) { inclusive = true }
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
                    navController.navigate(ScreenRoute.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = ScreenRoute.AljyoDescript.route,
            enterTransition = { defaultEnterTransition() },
            exitTransition = { defaultExitTransition() },
            popEnterTransition = null,
        ) {
            AljyoDescriptScreen(onBackPress = { navController.popBackStack() })
        }

        groupCreateNavGraph(navController)

        editNavGraph(navController)

        // 개인 프로필 수정 경로
        composable(
            route = "${ScreenRoute.UserSetting.route}/{nickName}/{profileImage}",
            arguments = listOf(
                navArgument("nickName") { type = NavType.StringType },
                navArgument("profileImage") { type = NavType.StringType }
            )
        ) {
            UserSettingScreen(
                isEditMode = true,
                onBackClick = { navController.popBackStack() },
                navigateToMain = { navController.popBackStack() }
            )
        }

        composable(
            route = "${ScreenRoute.Report.route}/{groupId}",
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            ReportScreen(
                onBackClick = { navController.popBackStack() },
                navigateToComplete = { navController.popBackStack() }
            )
        }

        composable(route = ScreenRoute.PrivacyPolicy.route){
            PrivacyPolicyScreen(
                onCloseClick = { navController.popBackStack() }
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
                navigateToDescript = {
                    screenNavController.navigate(ScreenRoute.AljyoDescript.route)
                },
                navigateToGroupDetails = navigateToGroupDetails,
                onCreateButtonClick = { screenNavController.navigate(ScreenRoute.GroupType.route) },
                navigateToPersonalSetting = { groupId ->
                    screenNavController.navigate(route = "${ScreenRoute.PersonalEdit.route}/$groupId")
                }
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
                },
                navigateToGroupDetails = navigateToGroupDetails
            )
        }

        composable(
            route = MainScreenRoute.MyPage.route,
        ) {
            MyPageScreen(
                navigateToDescript = {
                    screenNavController.navigate(ScreenRoute.AljyoDescript.route)
                },
                navigateToPreferences = {
                    screenNavController.navigate(ScreenRoute.Preferences.route)
                },
                navigateToOnboarding = {
                    screenNavController.navigate(ScreenRoute.Onboarding.route) {
                        popUpTo(0)
                    }
                },
                navigateToProfileSetting = { nickName, profileImage ->
                    screenNavController.navigate("${ScreenRoute.UserSetting.route}/$nickName/${Uri.encode(profileImage)}")
                },
                navigateToPrivacyPolicy = {
                    screenNavController.navigate(ScreenRoute.PrivacyPolicy.route)
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

    composable(route = ScreenRoute.GroupCreate.route) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ScreenRoute.GroupType.route)
        }

        CreateGroupScreen(
            viewModel = hiltViewModel(parentEntry),
            onBackClick = { navController.popBackStack() },
            onNextClick = { navController.navigate(ScreenRoute.AlarmType.route) }
        )
    }

    composable(route = ScreenRoute.AlarmType.route) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ScreenRoute.GroupType.route)
        }

        AlarmTypeScreen(
            viewModel = hiltViewModel(parentEntry),
            onBackClick = { navController.popBackStack() },
            navigateToAlarmSetting = {
                navController.navigate(ScreenRoute.AlarmSetting.route)
            }
        )
    }

    composable(route = ScreenRoute.AlarmSetting.route) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ScreenRoute.GroupType.route)
        }

        AlarmSettingScreen(
            viewModel = hiltViewModel(parentEntry),
            onBackClick = { navController.popBackStack() },
            navigateToAlarmMusicScreen = { navController.navigate("${ScreenRoute.AlarmMusic.route}/create?musicTitle=$it") },
            onCompleteClick = { groupId->
                navController.navigate("${ScreenRoute.GroupDetails.route}/$groupId?isNew=true") {
                    popUpTo(ScreenRoute.GroupType.route) { inclusive = true }
                }
            }
        )
    }

    composable(
        route = "${ScreenRoute.AlarmMusic.route}/create?musicTitle={musicTitle}",
        arguments = listOf(
            navArgument("musicTitle") {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ScreenRoute.GroupType.route)
        }
        val musicTitle = backStackEntry.arguments?.getString("musicTitle")
        val previousViewModel = hiltViewModel<GroupFormViewModel>(parentEntry)

        AlarmMusicScreen(
            musicTitle = musicTitle,
            onBackClick = { navController.popBackStack() },
            onCompleteClick = {
                previousViewModel.saveStateHandle["selectedMusic"] = it
                navController.popBackStack()
            }
        )
    }
}

fun NavGraphBuilder.editNavGraph(
    navController: NavHostController
) {
    composable(
        route = "${ScreenRoute.GroupEdit.route}/{groupDetail}",
        arguments = listOf(
            navArgument("groupDetail") { type = CustomNavType.groupEditType }
        )
    ) {
        GroupEditScreen(
            onBackClick = { navController.popBackStack() }
        )
    }

    composable(
        route = "${ScreenRoute.PersonalEdit.route}/{groupId}?setting={setting}",
        arguments = listOf(
            navArgument("groupId") { type = NavType.IntType },
            navArgument("setting") {
                type = CustomNavType.PersonalEditType
                nullable = true
            }
        )
    ) {
        PersonalEditScreen(
            onBackClick = { navController.popBackStack() },
            navigateToAlarmMusicScreen = { navController.navigate("${ScreenRoute.AlarmMusic.route}?musicTitle=$it") },
            navigateToGroupDetails = {
                navController.navigate("${ScreenRoute.GroupDetails.route}/$it"){
                    launchSingleTop = true
                    popUpTo("${ScreenRoute.PersonalEdit.route}/{groupId}?setting={setting}") { inclusive = true }
                }
            }
        )
    }

    composable(
        route = "${ScreenRoute.AlarmMusic.route}?musicTitle={musicTitle}",
        arguments = listOf(
            navArgument("musicTitle") {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { backStackEntry ->
        val musicTitle = backStackEntry.arguments?.getString("musicTitle")
        val previousViewModel =
            hiltViewModel<PersonalEditViewModel>(navController.previousBackStackEntry!!)

        AlarmMusicScreen(
            musicTitle = musicTitle,
            onBackClick = { navController.popBackStack() },
            onCompleteClick = {
                previousViewModel.saveStateHandle["selectedMusic"] = it
                navController.popBackStack()
            }
        )
    }
}