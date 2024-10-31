package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey01

private val dummyDates = listOf("월", "화", "수", "목", "금", "토", "일")
private const val everyDay = 7

@Composable
fun GroupItem(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        GroupThumbnail()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "그룹 타이틀그룹 타이틀그룹 타이틀그룹 타이틀그룹 타이틀",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Black01
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val dates = if (dummyDates.size == everyDay)
                stringResource(R.string.everyday)
            else dummyDates.joinToString(
                separator = " "
            )
            GreyBackgroundText(dates)
            GreyBackgroundText("21:00")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupItemPreview() {
    AljyoTheme {
        GroupItem(modifier = Modifier.width(148.dp))
    }
}

@Composable
fun GroupThumbnail() {
    Box(
        modifier = Modifier.size(148.dp)
    ) {
        AsyncImage(
            model = "",
            modifier = Modifier.size(148.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Group thumbnail",
            error = painterResource(R.drawable.ic_my_page)
        )
        Row(
            modifier = Modifier
                .offset(x = 8.dp, y = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0x99222222))
                .padding(4.dp, 2.dp, 6.dp, 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val publicGroup = false
            val painter = if (publicGroup) painterResource(R.drawable.ic_hello)
            else painterResource(R.drawable.ic_lock)

            val text = if (publicGroup) stringResource(R.string.public_group)
            else stringResource(R.string.private_group)

            Icon(
                modifier = Modifier.size(20.dp),
                painter = painter,
                contentDescription = "Public group icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    color = Grey01
                )
            )
        }
    }
}

@Composable
fun GreyBackgroundText(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Grey01)
            .padding(
                horizontal = 6.dp,
                vertical = 2.dp
            )
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                color = Black02
            )
        )
    }
}