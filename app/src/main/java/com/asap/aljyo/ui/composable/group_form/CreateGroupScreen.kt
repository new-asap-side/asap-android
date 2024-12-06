package com.asap.aljyo.ui.composable.group_form

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    onBackClick: () -> Unit
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->

        }
    )

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
        GroupProgressbar(
            modifier = Modifier.padding(innerPadding),
            startProgress = 0.3f,
            endProgress = 0.6f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
        ) {
            Text(
                text = "대표 이미지",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black02,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            GroupImagePicker(
                onImagePickerClick = {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
    }
}

@Composable
fun GroupImagePicker(
    onImagePickerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 2f)
            .padding(top = 8.dp)
    ) {
        AsyncImage(
            model = R.drawable.group_default_img,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        )
        Box(
            modifier = Modifier
                .padding(bottom = 14.dp, end = 14.dp)
                .size(40.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(color = White)
                .padding(12.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable { onImagePickerClick() },
                painter = painterResource(R.drawable.ic_select_photo),
                contentDescription = "Group Image Picker Icon",
                tint = Color.Unspecified,
            )
        }
    }
    Text(
        text = "사진을 등록하지 않는 경우 랜덤 이미지로 보여집니다",
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Black03,
            fontSize = 12.sp
        ),
        modifier = Modifier
            .padding(top = 8.dp)
    )
}

@Composable
@Preview
fun PreviewCreateGroupScreen() {
    AljyoTheme {
        CreateGroupScreen(
            onBackClick = {}
        )
    }
}