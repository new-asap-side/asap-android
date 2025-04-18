package com.asap.aljyo.ui.composable.common

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.onboarding.OnboardingPager
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.White

@Composable
fun ProfileImagePicker(
    modifier: Modifier = Modifier,
    profileImage: Uri?,
    profileItem: Int? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember{ MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
//        AsyncImage(
//            model = profileImage,
//            contentDescription = "Profile Image",
//            contentScale = if (profileImage == null) ContentScale.Fit else ContentScale.Crop,
//            error = painterResource(R.drawable.ic_empty_profile),
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//                .align(Alignment.Center)
//        )
        ProfileBox(
            modifier = Modifier.fillMaxSize(),
            profileImagePadding = 12.dp,
            profileImage = profileImage.toString(),
            profileItem = profileItem
        )
        Box(
            modifier = Modifier
                .padding(bottom = 12.dp, end = 12.dp)
                .size(28.dp)
                .background(White, shape = CircleShape)
                .border(1.dp, Grey02, shape = CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_fill_camera),
                contentDescription = "Camera Icon",
                modifier = Modifier
                    .align(Alignment.Center),

                )
        }
    }
}

@Preview
@Composable
fun ProfileImagePickerPreview() {
    AljyoTheme {
        ProfileImagePicker(
            modifier = Modifier.size(80.dp),
            profileImage = null,
            onClick = {}
        )
    }
}