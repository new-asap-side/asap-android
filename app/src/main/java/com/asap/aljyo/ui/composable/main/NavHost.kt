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
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home.route
    ) {
        composable(route = MainRoute.Home.route) {
            HomeScreen()
        }

        composable(route = MainRoute.AlarmList.route) {
            AlarmListScreen()
        }

        composable(route = MainRoute.MyPage.route) {
            MyPageScreen()
        }
    }
}