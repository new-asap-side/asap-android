package asap.aljyo.presentation.ui.common.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import asap.aljyo.presentation.R
import asap.aljyo.presentation.theme.AljyoColor
import asap.aljyo.presentation.theme.AljyoTheme

val introductionContents: List<@Composable () -> Unit> = listOf(
    {
        PagerContent(
            adjective = stringResource(R.string.adjective_god_life),
            title = stringResource(R.string.title_god_life),
            painter = painterResource(R.drawable.img_group_details_preview)
        )
    },
    {
        PagerContent(
            adjective = stringResource(R.string.adjective_alarm_contents),
            title = stringResource(R.string.title_alarm_contents),
            painter = painterResource(R.drawable.img_alarm_contents_preview)
        )
    },
    {
        PagerContent(
            adjective = stringResource(R.string.adjective_aljyo),
            title = stringResource(R.string.title_aljyo),
            painter = painterResource(R.drawable.img_home_preview)
        )
    },
)

@Composable
private fun PagerTitle(
    modifier: Modifier = Modifier,
    adjective: String,
    title: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = adjective,
            textAlign = TextAlign.Center,
            style = AljyoTheme.typograpy.b6.copy(
                color = AljyoColor.Red01
            )
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = AljyoTheme.typograpy.h2.copy(
                color = AljyoColor.Black00
            )
        )
    }
}

@Composable
fun PagerContent(
    modifier: Modifier = Modifier,
    adjective: String,
    title: String,
    painter: Painter
) {
    Column(modifier = modifier) {
        PagerTitle(
            modifier = Modifier.fillMaxWidth(),
            adjective = adjective,
            title = title
        )

        Image(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .aspectRatio(320f / 380f)
                .fillMaxSize(),
            painter = painter,
            contentScale = ContentScale.Fit,
            contentDescription = "Pager content image"
        )
    }
}

@Stable
@Composable
fun IntroductionPager(
    modifier: Modifier = Modifier,
    contents: List<@Composable () -> Unit> = introductionContents,
) {
    val pagerState = rememberPagerState(pageCount = { contents.size })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { page ->
            contents[page]()
        }

        PagerIndicator(
            modifier = Modifier.fillMaxWidth(),
            pagerCount = pagerState.pageCount,
            currentPage = pagerState.currentPage
        )
    }
}


// Preview
@Preview(showBackground = true)
@Composable
private fun IntroductionPagerPreview() {
    AljyoTheme {
        IntroductionPager()
    }
}

@Preview(showBackground = true)
@Composable
private fun PagerTitlePreview() {
    AljyoTheme {
        PagerTitle(title = "형용사", adjective = "Title")
    }
}