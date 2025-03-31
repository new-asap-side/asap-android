package com.asap.aljyo.ui.composable.main.my_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.asap.aljyo.ui.composable.common.ProfileBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey02

@Composable
fun MyPageProfile(
    modifier: Modifier = Modifier,
    profileImage: String?,
    profileItem: Int?,
    nickname: String?,
    navigateToSetting: (String?, String?, Int?) -> Unit
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
//            Box(
//                modifier = Modifier
//                    .size(56.dp)
//                    .clip(CircleShape)
//            ) {
//                AsyncImage(
//                    modifier = Modifier.fillMaxSize(),
//                    model = profileImage,
//                    error = painterResource(R.drawable.ic_empty_profile),
//                    contentDescription = "My page profile image",
//                    contentScale = ContentScale.Crop
//                )
//            }
            ProfileBox(
                modifier = Modifier.size(70.dp),
                profileImagePadding = 8.dp,
                profileItemPadding = 4.dp,
                profileItem = profileItem,
                profileImage = profileImage
            )

            Text(
                text = nickname ?: "알 수 없음",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Black01,
                    fontSize = 16.fsp
                )
            )
        }

        Text(
            modifier = Modifier
                .background(Grey02, RoundedCornerShape(100.dp))
                .clickable {
                    navigateToSetting(nickname, profileImage, profileItem)
                }
                .padding(vertical = 4.dp, horizontal = 10.dp),
            text = stringResource(R.string.edit_my_info),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.fsp,
                color = Black02
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        MyPageProfile(
            modifier = Modifier.fillMaxWidth(),
            profileImage = "",
            profileItem = null,
            nickname = "알죠",
            navigateToSetting = {_, _ ,_-> }
        )
    }
}