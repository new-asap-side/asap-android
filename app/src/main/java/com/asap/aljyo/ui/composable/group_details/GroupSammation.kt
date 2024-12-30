package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_details.GroupDetailsViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.custom.OverlappingRow
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black00
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.White

@Composable
fun GroupSummation(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailsViewModel
) {
    val groupDetailsState by viewModel.groupDetails.collectAsState()

    when (groupDetailsState) {
        is UiState.Error -> Unit

        UiState.Loading -> GroupSummationShimmer(modifier = modifier)

        is UiState.Success -> {
            val groupDetails = (groupDetailsState as UiState.Success).data

            Column(modifier = modifier) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(270.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = groupDetails?.groupThumbnailImageUrl,
                        contentDescription = "Group thumbnail",
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ic_my_page)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    0.0f to Black00.copy(alpha = 0.6f),
                                    0.22f to Black00.copy(alpha = 0.4f),
                                    0.39f to Black00.copy(alpha = 0.0f),
                                ),
                            )
                    )
                }
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
                    val leader = viewModel.findLeader(groupDetails)

                    GroupLeader(
                        leaderNickname = leader?.user?.nickName ?: "",
                        leaderThumbnail = leader?.user?.profileImageUrl ?: ""
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Grey01,
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = groupDetails?.title ?: "",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 22.fsp,
                            color = Black01,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    GroupAlarmDates(
                        dates = viewModel.parseAlarmDays(groupDetails),
                        timeStamp = groupDetails?.alarmTime ?: ""
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = groupDetails?.description ?: "",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 15.fsp,
                            color = Color(0xFF666666),
                        ),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    GroupPersonnel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFFFD9E7),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(color = MaterialTheme.colorScheme.secondary)
                            .padding(
                                vertical = 13.dp,
                                horizontal = 14.dp
                            ),
                        personnel = groupDetails?.currentPerson ?: 0,
                        participantsProfiles = groupDetails?.users?.map {
                            it.user.profileImageUrl
                        } ?: listOf()
                    )
                }
            }

        }
    }
}

@Composable
private fun GroupSummationShimmer(modifier: Modifier) {
    Column(modifier = modifier) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
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
            val roundedCornerShape4Dp = RoundedCornerShape(4.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(6.dp))

                ShimmerBox(
                    modifier = Modifier
                        .size(24.dp, 14.dp)
                        .clip(roundedCornerShape4Dp)
                )

                Spacer(modifier = Modifier.width(2.dp))

                ShimmerBox(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(roundedCornerShape4Dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp)
                    .clip(roundedCornerShape4Dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                ShimmerBox(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(roundedCornerShape4Dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                ShimmerBox(
                    modifier = Modifier
                        .size(48.dp, 16.dp)
                        .clip(roundedCornerShape4Dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                ShimmerBox(
                    modifier = Modifier.size(1.5.dp, 16.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                ShimmerBox(
                    modifier = Modifier
                        .size(41.dp, 16.dp)
                        .clip(roundedCornerShape4Dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )

                Row(
                    modifier = Modifier
                        .padding(13.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        ShimmerBox(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(roundedCornerShape4Dp),
                            baseColor = Color(0xFFDBDAE2)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        ShimmerBox(
                            modifier = Modifier
                                .size(120.dp, 20.dp)
                                .clip(roundedCornerShape4Dp),
                            baseColor = Color(0xFFDBDAE2)
                        )
                    }

                    ShimmerBox(
                        modifier = Modifier
                            .size(72.dp, 24.dp)
                            .clip(roundedCornerShape4Dp),
                        baseColor = Color(0xFFDBDAE2)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GroupSummationShimmer_Preview() {
    AljyoTheme {
        GroupSummationShimmer(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun GroupLeader(
    modifier: Modifier = Modifier,
    leaderThumbnail: String,
    leaderNickname: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape),
            model = leaderThumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = "Group leader profile",
            error = painterResource(R.drawable.ic_my_page),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = leaderNickname,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.fsp,
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
fun GroupLeader_Preview() {
    AljyoTheme {
        GroupLeader(
            leaderThumbnail = "",
            leaderNickname = "닉네임"
        )
    }
}

@Composable
private fun GroupAlarmDates(
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
                fontSize = 16.fsp,
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
                fontSize = 16.fsp,
                color = Black01
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupAlarmDates_Preview() {
    AljyoTheme {
        GroupAlarmDates(dates = "월 화 수", timeStamp = "21:00")
    }
}

@Composable
internal fun GroupPersonnel(
    modifier: Modifier = Modifier,
    personnel: Int = 0,
    participantsProfiles: List<String>
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
                    fontSize = 14.fsp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        OverlappingRow {
            participantsProfiles.forEach { profile ->
                AsyncImage(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    model = profile,
                    contentScale = ContentScale.Crop,
                    contentDescription = "participant's profile icon"
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun GroupPersonnel_Preview() {
    AljyoTheme {
        GroupPersonnel(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFFFD9E7),
                    shape = RoundedCornerShape(8f)
                )
                .clip(RoundedCornerShape(8f))
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(
                    vertical = 13.dp,
                    horizontal = 14.dp
                ),
            participantsProfiles = listOf()
        )
    }
}