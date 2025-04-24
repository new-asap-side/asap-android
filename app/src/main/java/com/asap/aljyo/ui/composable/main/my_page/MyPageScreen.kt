package com.asap.aljyo.ui.composable.main.my_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.core.components.main.MyPageViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.Banner
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White

@Composable
internal fun MyPageScreen(
    navigateToDescript: () -> Unit,
    navigateToPreferences: () -> Unit,
    navigateToOnboarding: () -> Unit,
    navigateToProfileSetting: (String?, String?, Int?) -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToCustomizeProfile: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.fetchScreen()
    }

    val myPageState by viewModel.state.collectAsStateWithLifecycle()

    when (myPageState) {
        is UiState.Loading -> {}
        is UiState.Error -> {}
        is UiState.Success -> {
            val state = (myPageState as UiState.Success).data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAF8F8))
            ) {
                Spacer(modifier = Modifier.height(42.dp))

                MyPageProfile(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    nickname = state.nickName,
                    profileImage = state.profileImage,
                    profileItem = state.profileItem,
                    navigateToSetting = { nickname, profileImage, profileItem ->
                        navigateToProfileSetting(nickname, profileImage, profileItem)
                        viewModel.fetchScreenFlag()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .clickable {
                            navigateToCustomizeProfile()
                            viewModel.fetchScreenFlag()
                        }
                        .height(52.dp)
                        .padding(horizontal = 20.dp)
                        .background(color = White, shape = RoundedCornerShape(14.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 30.dp),
                            text = "내 프로필 꾸미기",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.fsp,
                                color = Black02
                            )
                        )

                        if (state.profileItemNotification != 0) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .align(Alignment.Top)
                                    .background(color = Red01, shape = CircleShape)
                                    .padding(start = 4.5.dp, end = 4.5.dp, top = 1.5.dp, bottom = 2.5.dp),
                                text = "${state.profileItemNotification}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 10.fsp,
                                    color = White
                                )
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            modifier = Modifier.padding(end = 24.dp),
                            contentDescription = "ArrowRight Icon"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(modifier = Modifier.weight(1f)) {
                    MyPageMenu(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(White)
                            .padding(vertical = 44.dp),
                        navigateToOnboarding = navigateToOnboarding,
                        navigateToPreferences = navigateToPreferences
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(
                                horizontal = 20.dp,
                                vertical = 32.dp
                            )
                    ) {
                        Banner(
                            modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                            navigateToDescript = navigateToDescript
                        )

                        Text(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable(
                                    onClick = navigateToPrivacyPolicy
                                ),
                            text = "개인정보 처리방침",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.fsp,
                                color = Black02
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        MyPageScreen(
            navigateToDescript = {},
            navigateToOnboarding = {},
            navigateToPreferences = {},
            navigateToProfileSetting = { _, _ ,_-> },
            navigateToPrivacyPolicy = {},
            navigateToCustomizeProfile = {}
        )
    }
}