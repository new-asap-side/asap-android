package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey01
import com.asap.domain.entity.remote.AlarmGroup

private const val everyDay = 7

@Composable
internal fun GroupItem(
    modifier: Modifier = Modifier,
    alarmGroup: AlarmGroup = AlarmGroup.dummy()
) {
    Column(modifier = modifier) {
        GroupThumbnail(
            thumbnailUrl = alarmGroup.thumbnailUrl,
            isPublic = alarmGroup.isPublic
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            alarmGroup.title,
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
            val dates = if (alarmGroup.alarmDays.size == everyDay)
                stringResource(R.string.everyday)
            else alarmGroup.alarmDays.joinToString(
                separator = " "
            )
            GreyBackgroundText(dates)
            GreyBackgroundText(alarmGroup.alarmTime)
        }
        Spacer(modifier = Modifier.height(4.dp))
        GroupCounting(
            currentCount = alarmGroup.currentPerson,
            totalCount = alarmGroup.maxPersion
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupItemPreview() {
    AljyoTheme {
        GroupItem(modifier = Modifier.width(148.dp))
    }
}

@Composable
fun GroupThumbnail(
    thumbnailUrl: String,
    isPublic: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = thumbnailUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Fit,
            contentDescription = "Group thumbnail",
            placeholder = null,
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
            val painter = if (isPublic) painterResource(R.drawable.ic_hello)
            else painterResource(R.drawable.ic_lock)

            val text = if (isPublic) stringResource(R.string.public_group)
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

@Composable
fun GroupCounting(currentCount: Int, totalCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_people),
            contentDescription = "People"
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Black01,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        fontSize = 12.sp
                    )
                ) {
                    append(currentCount.toString())
                }
                append("/${stringResource(R.string.counting_people, totalCount)}")
            },
            style = MaterialTheme.typography.labelMedium.copy(
                color = Black03,
                fontSize = 12.sp
            )
        )
    }
}