package com.asap.aljyo.ui.composable.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
internal fun OnboardingScreen(
    navController: NavHostController,
) {
    AljyoTheme {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                OnboardingPager(
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    SocialLogin(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 24.dp)
                            .fillMaxWidth(),
                        navController = navController
                    )
                }
            }
        }
    }
}