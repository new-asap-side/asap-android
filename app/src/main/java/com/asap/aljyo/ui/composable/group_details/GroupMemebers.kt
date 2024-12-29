package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_details.GroupDetailsViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01

@Composable
fun GroupMembers(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailsViewModel,
) {
    val groupDetailsState by viewModel.groupDetails.collectAsState()

    when (groupDetailsState) {
        is UiState.Error -> Unit

        UiState.Loading -> GroupMembersShimer(modifier = modifier)

        is UiState.Success -> {
            val groupDetails = (groupDetailsState as UiState.Success).data

            val count = groupDetails?.currentPerson ?: 1
            Column(modifier = modifier) {
                Text(
                    text = "${stringResource(R.string.member)} $count",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 16.fsp,
                        color = Black01
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                groupDetails?.users?.forEach { participant ->
                    GroupMemberItem(
                        modifier = Modifier.fillMaxWidth(),
                        thumbnail = participant.user.profileImageUrl,
                        nickname = participant.user.nickName,
                        isLeader = participant.isGroupMaster
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

        }
    }
}

@Composable
private fun GroupMembersShimer(modifier: Modifier) {
    val shape = RoundedCornerShape(4.dp)
    Column(modifier = modifier) {
        ShimmerBox(
            modifier = Modifier
                .size(41.dp, 16.dp)
                .clip(shape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(10.dp))

            ShimmerBox(
                modifier = Modifier
                    .size(26.dp, 15.dp)
                    .clip(shape)
            )

            Spacer(modifier = Modifier.width(2.dp))

            ShimmerBox(
                modifier = Modifier
                    .size(16.dp)
                    .clip(shape)
            )

        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(10.dp))

            ShimmerBox(
                modifier = Modifier
                    .size(26.dp, 15.dp)
                    .clip(shape)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
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
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            model = thumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = "Group member thumbnail",
            error = painterResource(R.drawable.ic_my_page)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = nickname,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 15.fsp,
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
private fun GroupMemberItemPreview() {
    AljyoTheme {
        GroupMemberItem(
            modifier = Modifier.fillMaxWidth(),
            thumbnail = "",
            nickname = "닉네임",
            isLeader = true
        )
    }
}