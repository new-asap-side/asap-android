package com.asap.aljyo.ui.composable.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.White

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    navigateToDescript: () -> Unit = {},
) {
    val pagerState = rememberPagerState(pageCount = { 1 })
    val bannerColor = when (pagerState.currentPage) {
        0 -> Color(0xFFFFF2D8)
        else -> White
    }

    Box(
        modifier = modifier.background(color = bannerColor),
        contentAlignment = Alignment.BottomEnd
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> DefaultBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateToDescript() }
                        .padding(
                            start = 20.dp,
                            end = 16.dp,
                            top = 20.dp,
                            bottom = 20.dp
                        )
                        .background(Color(0xFFFFF2D8))
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    end = 10.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(100f))
                .background(color = Black01.copy(alpha = 0.5f))
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp
                ),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                            color = White
                        )
                    ) {
                        append("${pagerState.currentPage + 1}")
                    }
                    append(" / ${pagerState.pageCount}")
                },
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 12.fsp,
                    color = Grey03
                )
            )
        }
    }
}

@Composable
fun DefaultBanner(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.default_banner_sub_title),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Black01,
                    fontSize = 12.fsp
                )
            )
            Text(
                text = stringResource(R.string.default_banner_title),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Black01,
                    fontSize = 15.fsp
                )
            )
        }
        Image(
            painter = painterResource(R.drawable.img_default_banner),
            contentDescription = "Default banner image "
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultBannerPreview() {
    AljyoTheme {
        DefaultBanner(
            modifier = Modifier.fillMaxWidth()
        )
    }
}