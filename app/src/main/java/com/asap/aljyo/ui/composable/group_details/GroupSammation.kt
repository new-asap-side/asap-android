package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.asap.aljyo.ui.composable.common.custom.OverlappingRow
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.ui.theme.Yellow

@Composable
fun GroupSummation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp),
            model = "",
            contentDescription = "Alarm group thumbnail",
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_my_page)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp,
                    bottom = 24.dp
                )
        ) {
            GroupLeader()
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Grey01,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "GROUP TITLE GROUP TITLE GROUP TITLE GROUP TITLE GROUP TITLE",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 22.sp,
                    color = Black01,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            GroupAlarmDates(
                dates = "월 화 수",
                timeStamp = "21:00"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Desciption",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 15.sp,
                    color = Color(0xFF666666),
                ),
            )
            Spacer(modifier = Modifier.height(24.dp))
            GroupPersonnel(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFFD9E7)
                    )
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .padding(
                        vertical = 13.dp,
                        horizontal = 14.dp
                    ),
                personnel = 6
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 555)
@Composable
fun GroupSummationPreview() {
    AljyoTheme {
        GroupSummation(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
        )
    }
}

@Composable
fun GroupLeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.clip(CircleShape),
            model = "",
            contentDescription = "Group leader profile",
            error = painterResource(R.drawable.ic_my_page),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "nickname",
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.sp,
                color = Black01
            )
        )
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            painter = painterResource(R.drawable.ic_leader),
            contentDescription = "Group leader icon",
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupLeaderPreview() {
    AljyoTheme {
        GroupLeader()
    }
}

@Composable
fun GroupAlarmDates(
    modifier: Modifier = Modifier,
    dates: String,
    timeStamp: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clock),
            contentDescription = "clock icon"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = dates,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                color = Black01
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        VerticalDivider(
            modifier = Modifier.height(14.dp),
            color = Grey03,
            thickness = 1.5.dp,
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = timeStamp,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                color = Black01
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupAlarmDatesPreview() {
    AljyoTheme {
        GroupAlarmDates(dates = "월 화 수", timeStamp = "21:00")
    }
}

@Composable
fun GroupPersonnel(
    modifier: Modifier = Modifier,
    personnel: Int = 0,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_my_page),
                contentDescription = "Group personnel icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.particularting_personnel, personnel),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        OverlappingRow {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Red01)
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Yellow)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun GroupPersonnelPreview() {
    AljyoTheme {
        GroupPersonnel(
            modifier = Modifier
                .clip(RoundedCornerShape(8))
                .border(
                    width = 1.dp,
                    color = Color(0xFFFFD9E7)
                )
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(
                    vertical = 13.dp,
                    horizontal = 14.dp
                )
        )
    }
}