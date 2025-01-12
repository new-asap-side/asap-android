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
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(88.dp)
            .background(Grey02, shape = CircleShape)
            .clickable(
                interactionSource = remember{ MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
            AsyncImage(
                model = profileImage ?: R.drawable.ic_profile_default,
                contentDescription = "Profile Image",
                contentScale = if (profileImage == null) ContentScale.Fit else ContentScale.Crop,
                error = painterResource(R.drawable.ic_empty_profile),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .then(if(profileImage == null) Modifier.offset(y = 12.dp) else Modifier)
            )
        Box(
            modifier = Modifier
                .size(28.dp)
                .shadow(8.dp, shape = CircleShape)
                .background(White, shape = CircleShape)
                .clip(CircleShape)
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
            profileImage = null,
            onClick = {}
        )
    }
}