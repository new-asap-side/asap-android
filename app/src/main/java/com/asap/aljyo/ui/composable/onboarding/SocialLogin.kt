package com.asap.aljyo.ui.composable.onboarding

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.components.main.MainActivity
import com.asap.aljyo.components.onboarding.OnboardingViewModel
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.composable.common.dialog.LoadingDialog
import com.asap.aljyo.ui.theme.AljyoTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun SocialLogin(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KakaoLoginButton()
    }
}

@Composable
fun KakaoLoginButton(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    if (state == RequestState.Loading) {
        LoadingDialog { }
    }
    val context = LocalContext.current
    if (state is RequestState.Success) {
        Intent(context, MainActivity::class.java).also {
            context.startActivity(it)
        }
    }

    TextButton(
        onClick = {
            kakaoLogin(context, viewModel)
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFEE400),
            contentColor = Color(0xFF371D1E)
        ),
        modifier = Modifier.height(52.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_kakao),
            contentDescription = "kakao icon"
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .offset((-10).dp),
            text = stringResource(R.string.start_with_kakao),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

private fun kakaoLogin(context: Context, viewModel: OnboardingViewModel) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, e ->
        if (e != null) {
            viewModel.kakaoLoginFailed()
        }

        if (token != null) {
            viewModel.kakaoLoginSuccess(token = token)
        }
    }
    val available = UserApiClient.instance.isKakaoTalkLoginAvailable(context)
    if (available) {
        viewModel.kakaoLoginLoading()
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                viewModel.kakaoLoginSuccess(token = token)
            }
        }
        return
    }
    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
}

@Preview(showBackground = true, widthDp = 300, heightDp = 50)
@Composable
fun KakaoLoginButtonPreview() {
    AljyoTheme {
        KakaoLoginButton()
    }
}