package com.asap.aljyo.ui.composable.onboarding

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.components.onboarding.OnboardingViewModel
import com.asap.aljyo.ui.theme.AljyoTheme

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
    TextButton(
        onClick = {
            viewModel.requestKakaoLogin()
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

@Preview(showBackground = true, widthDp = 300, heightDp = 50)
@Composable
fun KakaoLoginButtonPreview() {
    AljyoTheme {
        KakaoLoginButton()
    }
}