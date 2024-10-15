package com.asap.aljyo.ui.composable.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

sealed class MainRoute(val route: String) {
    data object Home : MainRoute(route = "HOME")
    data object AlarmList : MainRoute(route = "ALARM_LIST")
    data object MyPage : MainRoute(route = "MY_PAGE")
}

@Composable
fun BottomNavigationBar() {

}