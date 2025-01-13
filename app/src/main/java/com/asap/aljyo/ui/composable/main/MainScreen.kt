package com.asap.aljyo.ui.composable.main

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.core.navigation.MainNavHost
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White

@Composable
internal fun MainScreen(
    screenNavController: NavHostController
) {
    val context = LocalContext.current
    SideEffect {
        val activity = (context as ComponentActivity)
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb(),
            )
        )
    }

    AljyoTheme {
        val navController = rememberNavController()
        Scaffold(
            containerColor = White,
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .height(66.dp),
                    navController = navController
                )
            },
        ) { padding ->
            Box(
                Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = 52.dp
                )
            ) {
                MainNavHost(
                    screenNavController = screenNavController,
                    navController = navController,
                )
            }
        }
    }
}