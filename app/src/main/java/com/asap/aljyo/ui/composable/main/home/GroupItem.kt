package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Grey01

@Composable
fun GroupItem() {
    Column {
        GroupThumbnail()
    }
}

@Preview(showBackground = true)
@Composable
fun GroupItemPreview() {
    AljyoTheme {
        GroupItem()
    }
}

@Composable
fun GroupThumbnail() {
    Box(
        modifier = Modifier.size(148.dp)
    ) {
        AsyncImage(
            model = "",
            contentDescription = "Group thumbnail"
        )
        Row(
            modifier = Modifier
                .offset(x = 8.dp, y = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0x99222222))
                .padding(4.dp, 2.dp, 6.dp, 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_hello),
                contentDescription = "Public group icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                stringResource(R.string.public_group),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    color = Grey01
                )
            )
        }
    }
}