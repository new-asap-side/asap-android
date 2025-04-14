package asap.aljyo.presentation.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import asap.aljyo.presentation.R
import asap.aljyo.presentation.theme.AljyoColor
import asap.aljyo.presentation.theme.AljyoTheme

@Composable
fun LoginButton(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KakaoLoginButton(
            modifier = Modifier.aspectRatio(320f / 52f),
            enabled = true
        ) { }
    }
}

@Composable
private fun KakaoLoginButton(
    modifier: Modifier,
    enabled: Boolean,
    onLogin: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = {
            if (enabled) {
                onLogin()
            }
        },
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            contentColor = AljyoColor.Grey04,
            containerColor = Color(0xFFFEE400)
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.CenterStart),
                painter = painterResource(R.drawable.ic_kakao),
                tint = Color.Unspecified,
                contentDescription = "kakao icon"
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.start_with_kakao),
                style = AljyoTheme.typograpy.b3.copy(
                    color = AljyoColor.Black00,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

// preview
@Preview(showBackground = true)
@Composable
private fun KakaoLoginButtonPreview() {
    AljyoTheme {
        KakaoLoginButton(
            modifier = Modifier.aspectRatio(320f / 52f),
            enabled = true
        ) { }
    }
}