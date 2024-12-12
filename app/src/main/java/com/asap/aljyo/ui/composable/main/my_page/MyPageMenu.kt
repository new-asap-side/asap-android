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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.ui.theme.Yellow

@Composable
internal fun MyPageMenu(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MyPageMenuItem(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.preferences)
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "preview",
                tint = Yellow
            )
        }

        MyPageMenuItem(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.log_out)
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
                )
        )
    }
}