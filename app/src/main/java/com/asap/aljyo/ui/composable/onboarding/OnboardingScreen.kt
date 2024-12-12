package com.asap.aljyo.ui.composable.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.core.components.onboarding.OnboardingViewModel
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.AljyoTheme

enum class SignupState {
    NOT_REGISTERED,
    REGISTERED
}

@Composable
internal fun OnboardingScreen(
    navigateToMain: () -> Unit,
    navigateToUserSetting: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    AljyoTheme {
        Scaffold { innerPadding ->
            val state by viewModel.state.collectAsState()
            val context = LocalContext.current
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {}

            LaunchedEffect(state) {
                if (state is RequestState.Success) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permission = Manifest.permission.POST_NOTIFICATIONS

                        val checkPermission = ContextCompat.checkSelfPermission(context, permission)
                        val granted = checkPermission == PackageManager.PERMISSION_GRANTED

                        if (!granted) {
                            // 알람 권한 요청
                            launcher.launch(permission)
                        }
                    }

                    if ((state as RequestState.Success).data == SignupState.REGISTERED) {
                        navigateToMain()
                    } else {
                        navigateToUserSetting()
                    }
                }
            }

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
                        onLoading = { viewModel.kakaoLoginLoading() },
                        onLoginSuccess = { token ->
                            viewModel.kakaoLoginSuccess(token = token)
                        },
                        onError = { viewModel.kakaoLoginFailed() }
                    )
                }
            }
        }
    }
}