package com.asap.aljyo.ui.composable.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R

@Composable
fun OnboardingMain() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painterResource(R.drawable.onboarding_main_title),
            contentDescription = "onboarding main title"
        )
        Image(
            painterResource(R.drawable.onboarding_main_image),
            contentDescription = "onboarding main title"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingMainPreview() {
    OnboardingMain()
}