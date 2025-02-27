package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.core.components.viewmodel.main.MainViewModel
import com.asap.aljyo.core.navigation.MainScreenRoute
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.theme.White

private val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.MyAlarm,
    BottomNavItem.MyPage,
)

@Preview
@Composable
private fun BottomNavigationBackgroundPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(64.dp),
        onRouteChanged = {},
        navController = navController
    )
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onRouteChanged: (String) -> Unit,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .dropShadow(shape = RectangleShape, offsetY = 5.dp, blur = 14.dp)
            .background(color = White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        val viewModel: MainViewModel = hiltViewModel()
        val selectedIndex by viewModel.selectedIndex.collectAsState()

        bottomNavItems.forEachIndexed { index, item ->
            item.Item(selected = index == selectedIndex) {
                viewModel.select(index)
                if (currentRoute != item.route) {
                    onRouteChanged(item.route)

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
