package com.asap.aljyo.ui.composable.main.home.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White

@Composable
fun ItemTitle(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 18.fsp
            )
        )

        Surface(
            modifier = Modifier.fillMaxHeight(),
            onClick = onClick,
            color = White,
            contentColor = Black03
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.see_more),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp,
                    )
                )

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "see more"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeeMoreTitlePreview() {
    AljyoTheme {
        ItemTitle(
            modifier = Modifier
            .width(200.dp)
            .height(50.dp),
            title = "타이틀"
        ) { }
    }
}