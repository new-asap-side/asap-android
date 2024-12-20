package com.asap.aljyo.ui.composable.common.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Red01

@Composable
internal fun AljyoPager(
    modifier: Modifier,
    pageCount: Int = 3
) {
    val pagerState = rememberPagerState(pageCount = { pageCount })
    Column(modifier = modifier) {
        HorizontalPager(state = pagerState) { page ->
            val pageModifier =  Modifier.size(480.dp)
            when (page) {
                0 -> HardObject(modifier = pageModifier)
                1 -> ReleaseAlarm(modifier = pageModifier)
                2 -> GoodDay(modifier = pageModifier)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { currentPage ->
                val isSelected = pagerState.currentPage == currentPage
                PagerIndicator(isSelected = isSelected)
            }
        }
    }
}

@Composable
private fun PagerHeader(
    title: String,
    subTitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Red01,
                fontSize = 14.sp
            )
        )
        Text(
            text = subTitle,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
internal fun HardObject(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PagerHeader(
            title = stringResource(R.string.hard_objective_title),
            subTitle = stringResource(R.string.hard_objective_sub_title)
        )

        Image(
            modifier = Modifier.size(320.dp, 380.dp),
            painter = painterResource(R.drawable.img_hard_objective),
            contentDescription = "Hard object image"
        )
    }
}

@Composable
internal fun ReleaseAlarm(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PagerHeader(
            title = stringResource(R.string.funny),
            subTitle = stringResource(R.string.release_alarm_with_contents)
        )

        Image(
            modifier = Modifier.size(320.dp, 380.dp),
            painter = painterResource(R.drawable.img_release_alarm),
            contentDescription = "Hard object image"
        )
    }
}

@Composable
internal fun GoodDay(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PagerHeader(
            title = stringResource(R.string.with_aljyo),
            subTitle = stringResource(R.string.good_today)
        )

        Image(
            modifier = Modifier.size(320.dp, 380.dp),
            painter = painterResource(R.drawable.img_good_day),
            contentDescription = "Hard object image"
        )
    }
}

/**
 *  Preview
 */
@Preview
@Composable
private fun PagerHeader_Preview() {
    AljyoTheme {
        PagerHeader(
            title = "Title",
            subTitle = "Sub title"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IntroPager_Preview() {
    AljyoTheme {
        AljyoPager(modifier = Modifier, pageCount = 3)
    }
}