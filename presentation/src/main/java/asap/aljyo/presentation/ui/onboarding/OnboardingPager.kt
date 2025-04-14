package asap.aljyo.presentation.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import asap.aljyo.presentation.R
import asap.aljyo.presentation.theme.AljyoTheme
import asap.aljyo.presentation.ui.common.pager.IntroductionPager
import asap.aljyo.presentation.ui.common.pager.introductionContents

@Stable
@Composable
internal fun OnboardingPager(
    modifier: Modifier
) {
    IntroductionPager(
        modifier = modifier,
        contents = listOf<@Composable () -> Unit> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .layout { measurable, constraint ->
                            measurable.measure(constraint).run {
                                layout(width, height) {
                                    place(0, (height * 0.17f).toInt())
                                }
                            }
                        }
                        .align(Alignment.TopCenter),
                    painter = painterResource(R.drawable.img_aljyo_main),
                    contentDescription = "Aljyo main"
                )
            }
        } + introductionContents
    )
}

@Preview(showBackground = true)
@Composable
private fun OnboardingPagerPreview() {
    AljyoTheme {
        OnboardingPager(modifier = Modifier.size(320.dp, 480.dp))
    }
}