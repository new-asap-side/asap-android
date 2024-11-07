package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.White

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
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
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