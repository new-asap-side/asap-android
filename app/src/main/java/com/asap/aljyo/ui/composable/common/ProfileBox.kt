package com.asap.aljyo.ui.composable.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.domain.entity.local.User

@Composable
fun ProfileBox(
    modifier: Modifier = Modifier,
    profileImagePadding: Dp = 0.dp,
    profileItemPadding: Dp = 0.dp,
    profileImage: String?,
    profileItem: Int? = null,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = modifier
                .padding(profileImagePadding)
                .fillMaxSize()
                .clip(CircleShape),
            model = profileImage,
            error = painterResource(R.drawable.ic_empty_profile),
            contentDescription = "My page profile image",
            contentScale = ContentScale.Crop
        )

        if (profileItem != null) {
            Icon(
                modifier = modifier
                    .padding(profileItemPadding)
                    .fillMaxSize(),
                painter = painterResource(profileItem),
                contentDescription = "SELECTED CUSTOM ITEM",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewProfileBox() {
    AljyoTheme {
//        ProfileBox(
//            modifier = Modifier.size(120.dp),
//            profileImagePadding = 10.dp,
////            profileItemPadding = ,
//            profileImage = R.drawable.group_random_1,
//            profileItem = R.drawable.ic_custom_1
//        )
    }
}
