package com.asap.aljyo.ui.composable.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asap.aljyo.ui.composable.main.alarm_list.AlarmListScreen
import com.asap.aljyo.ui.composable.main.home.HomeScreen
import com.asap.aljyo.ui.composable.main.my_page.MyPageScreen

@Composable
fun MainNavHost(
    appNavController: NavHostController,
    mainNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeScreen(appNavController = appNavController)
        }

        composable(route = BottomNavItem.AlarmList.route) {
            AlarmListScreen()
        }

        composable(route = BottomNavItem.MyPage.route) {
            MyPageScreen()
        }
    }
}