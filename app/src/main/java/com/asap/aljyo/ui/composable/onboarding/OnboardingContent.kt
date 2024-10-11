package com.asap.aljyo.ui.composable.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Red01

@Composable
fun OnboardingMain() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painterResource(R.drawable.onboarding_main_title),
            contentDescription = "onboarding main title"
        )
        Image(
            painterResource(R.drawable.onboarding_main_image),
            contentDescription = "onboarding main title",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(
                width = 320.dp,
                height = 257.dp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingMainPreview() {
    OnboardingMain()
}

@Composable
fun OnboardingTitleText(title: String, subTitle: String) {
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

@Preview(showBackground = true)
@Composable
fun OnboardingTitleTextPreview() {
    AljyoTheme {
        OnboardingTitleText("Title", "SubTitle")
    }
}

@Composable
fun OnboardingHardObjective(imageHeight: Dp) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        OnboardingTitleText(
            title = stringResource(R.string.hard_objective_title),
            subTitle = stringResource(R.string.hard_objective_sub_title)
        )
        Image(
            painterResource(R.drawable.onboarding_hard_objective),
            contentDescription = "onboarding hard object image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(imageHeight)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingHardObjectivePreview() {
    AljyoTheme {
        OnboardingHardObjective(414.dp)
    }
}

@Composable
fun OnboardingReleaseAlarm() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OnboardingTitleText(
            title = stringResource(R.string.funny),
            subTitle = stringResource(R.string.release_alarm_with_contents)
        )
        Image(
            painterResource(R.drawable.onboarding_release_alarm),
            contentDescription = "onboarding release alarm image",
            modifier = Modifier.padding(start = 20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingReleaseAlarmPreview() {
    AljyoTheme {
        OnboardingReleaseAlarm()
    }
}

@Composable
fun OnboardingGoodToday() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OnboardingTitleText(
            title = stringResource(R.string.with_aljyo),
            subTitle = stringResource(R.string.good_today)
        )
        Image(
            painterResource(R.drawable.onboarding_good_today),
            contentDescription = "good_today_image"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingGoodTodayPreview() {
    AljyoTheme {
        OnboardingGoodToday()
    }
}