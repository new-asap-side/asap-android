package com.asap.aljyo.components.usersetting

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.composable.common.NicknameTextField
import com.asap.aljyo.ui.composable.common.ProfileImagePicker
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingScreen(
    userSettingViewModel: UserSettingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            userSettingViewModel.setProfileImage(uri)
        }
    )
    val userSettingState by userSettingViewModel.userSettingState.collectAsStateWithLifecycle()
    val buttonState = userSettingState.run {
            msg == UserSettingMsgType.Success && selectedProfileImage != null
        }

    Scaffold(
        containerColor = White,
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_top_back),
                            contentDescription = "BACK"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "어떤 프로필로 시작할까요?",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            ProfileImagePicker(
                profileImage = userSettingState.selectedProfileImage,
                onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            NicknameTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                nickname = userSettingState.nickname,
                onNicknameChange = { userSettingViewModel.updateNickname(it) },
                onFocusChange = { userSettingViewModel.updateNickname(userSettingState.nickname) },
                onNicknameCheck = { userSettingViewModel.checkNickname(it) },
                msg = userSettingState.msg
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                modifier = Modifier
                    .imePadding(),
                text = "확인",
                textColor = Color.White,
                backgroundColor = Red01,
                onClick = {userSettingViewModel.saveUserProfile()},
                enable = buttonState
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AljyoTheme {
        UserSettingScreen(
            onBackClick = {}
        )
    }
}

