package com.asap.aljyo.components.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.asap.aljyo.ui.composable.main.BottomNavigationBar
import com.asap.aljyo.ui.composable.main.MainNavHost
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb()
            ),
        )
        setContent {
            val navController = rememberNavController()
            AljyoTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .fillMaxWidth()
                                .height(66.dp),
                            navController = navController
                        )
                    }
                ) { padding ->
                    Box(Modifier.padding(padding)) {
                        MainNavHost(navController)
                    }
                }
            }
        }
    }
}