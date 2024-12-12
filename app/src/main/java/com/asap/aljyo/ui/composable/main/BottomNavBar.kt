package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.core.navigation.MainScreenRoute
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.theme.White

private val bottomNavItems = listOf(
    BottomNavItem.AlarmList,
    BottomNavItem.Home,
    BottomNavItem.MyPage,
)

private val margin = 6.dp
private val radius = 36.dp
private val centerBezierGap = 22.dp

@Preview
@Composable
private fun BottomNavigationBackgroundPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(66.dp),
        navController = navController
    )
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val density = LocalDensity.current
    val marginPx = density.run {
        margin.toPx()
    }
    val radiusPx = density.run {
        radius.toPx()
    }
    val centerBezierGapPx = density.run {
        centerBezierGap.toPx()
    }

    val shape = BottomBarShape(
        marginPx = marginPx,
        radiusPx = radiusPx,
        gap = centerBezierGapPx,
    )
    Box(
        modifier = modifier
            .dropShadow(shape = shape, offsetY = (-1).dp)
            .clip(shape = shape)
            .background(color = White)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            bottomNavItems.forEach { item ->
                val onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }

                if (item.route == MainScreenRoute.Home.route) {
                    BottomNavItemMain(onClick = onClick)
                } else {
                    BottomNavItemSub(
                        icon = item.icon,
                        label = item.label,
                        isSelected = currentRoute == item.route,
                        onClick = onClick
                    )
                }
            }
        }
    }
}
