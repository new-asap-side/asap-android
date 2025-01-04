package com.asap.aljyo.ui.composable.main.my_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey02

@Composable
fun MyPageProfile(
    modifier: Modifier = Modifier,
    nickname: String,
    navigateToSetting: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = null,
                    error = painterResource(R.drawable.ic_my_page),
                    contentDescription = "My page profile image",
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = nickname,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Black01,
                    fontSize = 16.fsp
                )
            )
        }

        TextButton(
            colors = ButtonDefaults.textButtonColors(
                containerColor = Grey02,
                contentColor = Black02
            ),
            onClick = {
                navigateToSetting()
            }
        ) {
            Text(
                text = stringResource(R.string.edit_my_info),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.fsp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        MyPageProfile(
            modifier = Modifier.fillMaxWidth(),
            nickname = "알죠"
        )
    }
}