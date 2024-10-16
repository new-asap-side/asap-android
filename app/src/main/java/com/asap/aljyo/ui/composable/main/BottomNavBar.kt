package com.asap.aljyo.ui.composable.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.asap.aljyo.ui.theme.White

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

private val margin = 6.dp
private val radius = 18.dp
private val centerBezierGap = 22.dp

@Composable
fun BottomNavigationBarBackground(
    modifier: Modifier = Modifier,
    color: Color = White
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val screenWidthPx = with(density) { config.screenWidthDp.dp.toPx() }

    val path = Path()
    var canvasHeight by remember { mutableFloatStateOf(0f) }

    Canvas(
        modifier = modifier
            .onGloballyPositioned { coordinate ->
                canvasHeight = coordinate.size.height.toFloat()
            }
    ) {
        val marginPx = margin.toPx()
        val radiusPx = radius.toPx()
        val gap = centerBezierGap.toPx()

        path.apply {
            moveTo(0f, marginPx + radiusPx)
            arcTo(
                rect = Rect(
                    left = 0f,
                    top = marginPx,
                    right = radiusPx,
                    bottom = marginPx + radiusPx
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false,
            )
            lineTo((screenWidthPx / 2) - gap, marginPx)
            quadraticTo(
                x1 = (screenWidthPx / 2),
                y1 = -marginPx,
                x2 = (screenWidthPx / 2) + gap,
                y2 = marginPx
            )
            lineTo(screenWidthPx - radiusPx, marginPx)
            arcTo(
                rect = Rect(
                    left = screenWidthPx - radiusPx,
                    top = marginPx,
                    right = screenWidthPx,
                    bottom = marginPx + radiusPx
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(screenWidthPx, canvasHeight)
            lineTo(0f, canvasHeight)
            lineTo(0f, marginPx + radiusPx)
        }
        drawPath(path, color)
    }
}

@Preview
@Composable
fun BottomNavigationBackgroundPreview() {
    BottomNavigationBarBackground(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    )
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController
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

    BottomNavigationBarBackground(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = BottomBarShape(
                    marginPx = marginPx,
                    radiusPx = radiusPx,
                    gap = centerBezierGapPx,
                )
            ),
    )
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        bottomNavItems.forEach { item ->
            if (item.route == MainRoute.Home.route) {
                BottomNavItemMain(

                )
            } else {
                BottomNavItemSub(
                    icon = item.icon,
                    label = item.label,
                    isSelected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}
