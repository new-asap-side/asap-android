package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01

@Composable
fun GroupMembers(
    modifier: Modifier = Modifier,
    count: Int
) {
    Column(modifier = modifier) {
        Text(
            text = "${stringResource(R.string.member)} $count",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 16.sp,
                color = Black01
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        repeat(count) {
            GroupMemberItem(
                modifier = Modifier.fillMaxWidth(),
                thumbnail = "",
                nickname = if (it == 0) "leader" else "member",
                isLeader = it == 0
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMembersPreview() {
    AljyoTheme {
        GroupMembers(
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 20.dp,
            ), count = 3
        )
    }
}

@Composable
fun GroupMemberItem(
    modifier: Modifier = Modifier,
    thumbnail: String,
    nickname: String,
    isLeader: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = thumbnail,
            contentDescription = "Group member thumbnail",
            error = painterResource(R.drawable.ic_my_page)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = nickname,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 15.sp,
                color = Black01
            )
        )
        if (isLeader) {
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                painter = painterResource(R.drawable.ic_leader),
                contentDescription = "Group leader icon",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMemberItemPreview() {
    AljyoTheme {
        GroupMemberItem(
            modifier = Modifier.fillMaxWidth(),
            thumbnail = "",
            nickname = "닉네임",
            isLeader = true
        )
    }
}