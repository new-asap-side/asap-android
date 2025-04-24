package asap.aljyo.presentation.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import asap.aljyo.presentation.theme.AljyoColor
import asap.aljyo.presentation.theme.AljyoTheme

@Composable
fun OnboardingScreen() {
    AljyoTheme {
        Scaffold { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                color = AljyoColor.White
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OnboardingPager(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .aspectRatio(360f / 480f),
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 24.dp)
                            .align(Alignment.BottomCenter),
                    ) {
                        LoginButton(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}