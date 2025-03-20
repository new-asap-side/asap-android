package com.asap.aljyo.core.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.ui.composable.main.alarm_list.AlarmListScreen
import com.asap.aljyo.ui.composable.main.home.HomeScreen
import com.asap.aljyo.ui.composable.main.my_page.MyPageScreen

@Composable
fun MainNavHost(
    screenNavController: NavHostController,
    navController: NavHostController,
    mainViewModel: MainViewModel
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
                navigateToMyAlarm = {
                    mainViewModel.select(1)
                    navController.navigate(MainScreenRoute.AlarmList.route) {
                        popUpTo(MainScreenRoute.Home.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToGroupDetails = navigateToGroupDetails,
                navigateToPersonalSetting = { groupId ->
                    screenNavController.navigate(route = "${ScreenRoute.PersonalEdit.route}/$groupId")
                },
                navigateToCustomizeProfile = { screenNavController.navigate(route = ScreenRoute.CustomizeProfile.route) }
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

        composable(route = MainScreenRoute.MyPage.route) {
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
                navigateToProfileSetting = { nickName, profileImage, profileItem ->
                    screenNavController.navigate("${ScreenRoute.UserSetting.route}/$nickName/${Uri.encode(profileImage)}/$profileItem")
                },
                navigateToPrivacyPolicy = {
                    screenNavController.navigate(ScreenRoute.PrivacyPolicy.route)
                },
                navigateToCustomizeProfile = {
                    screenNavController.navigate(ScreenRoute.CustomizeProfile.route)
                }
            )
        }
    }
}
