package com.asap.aljyo.ui.composable.main.my_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.MyPageViewModel
import com.asap.aljyo.ui.composable.common.MenuItem
import com.asap.aljyo.ui.composable.common.dialog.PrecautionsDialog
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.ui.theme.Yellow

@Composable
internal fun MyPageMenu(
    modifier: Modifier = Modifier,
    navigateToOnboarding: () -> Unit,
    navigateToPreferences: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var showLogoutDialog by remember { mutableStateOf(false) }

        if (showLogoutDialog) {
            PrecautionsDialog(
                title = stringResource(R.string.ask_log_out),
                description = stringResource(R.string.no_more_alarm),
                onDismissRequest = { showLogoutDialog = false },
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.deleteLocalUserInfo().invokeOnCompletion {
                        navigateToOnboarding()
                    }
                }
            )
        }

        MenuItem(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.preferences),
            onClick = { navigateToPreferences() }
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "preview",
                tint = Yellow
            )
        }

        MenuItem(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.log_out),
            onClick = { showLogoutDialog = true }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_log_out),
                contentDescription = "preview",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        MyPageMenu(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(White)
                .padding(
                    vertical = 44.dp
                ),
            navigateToOnboarding = {},
            navigateToPreferences = {}
        )
    }
}