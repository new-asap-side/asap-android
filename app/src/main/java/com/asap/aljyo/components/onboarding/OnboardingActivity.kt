package com.asap.aljyo.components.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.asap.aljyo.components.main.MainActivity
import com.asap.aljyo.ui.composable.onboarding.OnboardingPager
import com.asap.aljyo.ui.composable.onboarding.SocialLogin
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb()
            )
        )
        setContent {
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
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            delay(1000)
            Intent(this@OnboardingActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}