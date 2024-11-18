package com.asap.aljyo.ui.composable.onboarding

import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun SocialLogin(
    modifier: Modifier = Modifier,
    onLoading: () -> Unit,
    onLoginSuccess: (OAuthToken) -> Unit,
    onError: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KakaoLoginButton(
            onLoading = onLoading,
            onLoginSuccess = onLoginSuccess,
            onError = onError,
        )
    }
}

@Composable
fun KakaoLoginButton(
    onLoading: () -> Unit,
    onLoginSuccess: (OAuthToken) -> Unit,
    onError: () -> Unit = {},
) {
    val context = LocalContext.current

    TextButton(
        onClick = {
            kakaoLogin(
                context,
                onLoading = onLoading,
                onLoginSuccess = onLoginSuccess,
                onError = onError
            )
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

private fun kakaoLogin(
    context: Context,
    onLoading: () -> Unit,
    onLoginSuccess: (OAuthToken) -> Unit,
    onError: () -> Unit = {},
) {
    onLoading()
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, e ->
        if (e != null) {
            onError()
        }

        if (token != null) {
            onLoginSuccess(token)
        }
    }

    val available = UserApiClient.instance.isKakaoTalkLoginAvailable(context)
    if (available) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                onLoginSuccess(token)
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
        KakaoLoginButton(
            onLoading = {},
            onLoginSuccess = {},
        )
    }
}