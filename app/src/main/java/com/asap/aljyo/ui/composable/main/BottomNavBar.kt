package com.asap.aljyo.ui.composable.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.navigation.MainScreenRoute
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.theme.White

private val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.MyAlarm,
    BottomNavItem.MyPage,
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        mainViewModel.getProfileItemNotification()
    }

    Row(
        modifier = modifier
            .dropShadow(shape = RectangleShape, offsetY = 5.dp, blur = 14.dp)
            .background(color = White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        val selectedIndex by mainViewModel.selectedIndex.collectAsState()
        val isNew by mainViewModel.isNew.collectAsState()

        bottomNavItems.forEachIndexed { index, item ->
            val isNewNotification = if (item == BottomNavItem.MyPage) isNew else false

            item.Item(selected = index == selectedIndex, isNewNotification = isNewNotification) {
                mainViewModel.select(index)
                if (currentRoute != item.route) {
                    navController.navigate(item.route) {
                        val previousRoute = currentRoute ?: MainScreenRoute.Home.route
                        popUpTo(previousRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}
