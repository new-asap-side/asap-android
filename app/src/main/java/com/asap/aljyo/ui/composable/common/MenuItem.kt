package com.asap.aljyo.ui.composable.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Yellow

@Composable
internal fun MenuItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit = {},
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(
                vertical = 15.dp,
                horizontal = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Black01,
                    fontSize = 15.sp
                )
            )
        }

        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Menu click icon"
        )
    }

}

@Preview(showBackground = true, widthDp = 200)
@Composable
private fun Preview() {
    AljyoTheme {
        MenuItem(
            modifier = Modifier.fillMaxWidth(),
            title = "환경설정"
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "preview",
                tint = Yellow
            )
        }
    }
}