package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

sealed class MainRoute(val route: String) {
    data object Home : MainRoute(route = "HOME")
    data object AlarmList : MainRoute(route = "ALARM_LIST")
    data object MyPage : MainRoute(route = "MY_PAGE")
}

private val bottomNavItems = listOf(
    BottomNavItem.AlarmList,
    BottomNavItem.Home,
    BottomNavItem.MyPage,
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val selectedItem by remember { mutableIntStateOf(1) }
    val currentRoute by remember { mutableStateOf(BottomNavItem.Home.route) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        bottomNavItems.forEach { item ->
            if (item.route == MainRoute.Home.route) {
                Box(
                    modifier = Modifier.offset(y = (-6).dp)
                ) {
                    BottomNavItemMain(navController = navController)
                }
            } else {
                BottomNavItemSub(
                    icon = item.icon,
                    label = item.label,
                    navController = navController
                )
            }
        }
    }
}