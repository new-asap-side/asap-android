package com.asap.aljyo.core.components

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.asap.aljyo.core.navigation.AppNavHost
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.aljyo.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleAlarmIntent()
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb(),
            )
        )
        setContent {
            AppNavHost()
        }
    }

    private fun handleAlarmIntent() {
        val uri = intent.data.toString()
        Log.d(TAG, "received uri: $uri")

        val isAlarmUri = uri.startsWith("aljyo://${ScreenRoute.ReleaseAlarm.route}")
        if (!isAlarmUri) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)

            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}