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
import androidx.compose.foundation.shape.CircleShape
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
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Grey01
import com.asap.data.utility.DateTimeManager.sortByDay
import com.asap.domain.entity.remote.AlarmGroup

private const val everyDay = 7

@Composable
internal fun GroupItem(
    modifier: Modifier = Modifier,
    alarmGroup: AlarmGroup = AlarmGroup.dummy()
) {
    Column(modifier = modifier) {
        GroupThumbnail(
            modifier = Modifier.fillMaxWidth(),
            aspectRatio = 155f / 124f,
            thumbnailUrl = alarmGroup.thumbnailUrl,
            isPublic = alarmGroup.isPublic
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val dates = if (alarmGroup.alarmDays.size == everyDay)
                stringResource(R.string.everyday)
            else {
                val alarmDays = alarmGroup.alarmDays.sortByDay()
                alarmDays.joinToString(separator = " ")
            }
            GreyBackgroundText(dates)
            GreyBackgroundText(alarmGroup.alarmTime)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            alarmGroup.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.fsp,
                color = Color(0xFF111111)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        GroupCounting(
            currentCount = alarmGroup.currentPerson,
            totalCount = alarmGroup.maxPersion
        )
    }
}

@Composable
internal fun LinearGroupItem(
    modifier: Modifier = Modifier,
    alarmGroup: AlarmGroup = AlarmGroup.dummy()
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GroupThumbnail(
            modifier = Modifier.size(100.dp),
            aspectRatio = 1f,
            isPublic = alarmGroup.isPublic,
            thumbnailUrl = alarmGroup.thumbnailUrl
        )

        Column(
            modifier = Modifier.weight(1f).height(100.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                val dates = if (alarmGroup.alarmDays.size == everyDay)
                    stringResource(R.string.everyday)
                else {
                    val alarmDays = alarmGroup.alarmDays.sortByDay()
                    alarmDays.joinToString(separator = " ")
                }
                GreyBackgroundText(dates)
                GreyBackgroundText(alarmGroup.alarmTime)
            }

            Text(
                alarmGroup.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 15.fsp,
                    color = Color(0xFF111111)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            GroupCounting(
                currentCount = alarmGroup.currentPerson,
                totalCount = alarmGroup.maxPersion
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupItemPreview() {
    AljyoTheme {
        GroupItem(modifier = Modifier.width(148.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LinearGroupItemPreview() {
    AljyoTheme {
        LinearGroupItem(modifier = Modifier.width(360.dp))
    }
}

@Composable
private fun GroupThumbnail(
    modifier: Modifier,
    aspectRatio: Float,
    thumbnailUrl: String,
    isPublic: Boolean
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = thumbnailUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = "Group thumbnail",
            placeholder = painterResource(R.drawable.img_loading),
            error = painterResource(R.drawable.img_loading)
        )

        if (!isPublic) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 8.dp, y = 8.dp)
                    .background(
                        shape = CircleShape,
                        color = Color(0xFF000000).copy(alpha = 0.52f)
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp)
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = "lock icon",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
private fun GreyBackgroundText(text: String) {
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
                fontSize = 12.fsp,
                color = Black02
            )
        )
    }
}

@Composable
private fun GroupCounting(currentCount: Int, totalCount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
                        fontSize = 12.fsp
                    )
                ) {
                    append(currentCount.toString())
                }
                append("/${stringResource(R.string.counting_people, totalCount)}")
            },
            style = MaterialTheme.typography.labelMedium.copy(
                color = Black03,
                fontSize = 12.fsp
            )
        )
    }
}