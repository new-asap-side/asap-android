package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.group_details.GroupDetailsViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.UserGroupType

sealed class AlarmDetailsTabItem(val titleId: Int) {
    data object Details : AlarmDetailsTabItem(titleId = R.string.details)
    data object Settings : AlarmDetailsTabItem(titleId = R.string.private_setting)
}

private val tabItems = listOf(
    AlarmDetailsTabItem.Details,
    AlarmDetailsTabItem.Settings,
)

@Composable
fun AlarmDetails(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailsViewModel
) {
    val groupDetailsState by viewModel.groupDetails.collectAsState()
    val userGroupType = viewModel.userGroupType
    var tabIndex by remember { mutableIntStateOf(0) }

    when (groupDetailsState) {
        is UiState.Error -> Unit

        UiState.Loading -> AlarmDetailsShimmer(
            modifier = modifier.padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
        )

        is UiState.Success -> {
            val groupDetails = (groupDetailsState as UiState.Success).data

            val parsedAlarmEndDate = viewModel.parseISOFormat(
                groupDetails?.alarmEndDate ?: ""
            )
            val parsedAlarmTime = viewModel.parseToAmPm(
                groupDetails?.alarmTime ?: ""
            )
            val parsedAlarmDays = viewModel.parseAlarmDays(groupDetails)

            val privateSetting by viewModel.privateSettingState.collectAsState()

            when (userGroupType) {
                UserGroupType.Leader,
                UserGroupType.Participant -> {
                    Column(modifier = modifier) {
                        TabRow(
                            selectedTabIndex = tabIndex,
                            containerColor = White,
                            contentColor = Black01,
                            indicator = { positions ->
                                Box(
                                    modifier = Modifier
                                        .tabIndicatorOffset(positions[tabIndex])
                                        .padding(horizontal = 20.dp)
                                        .height(2.dp)
                                        .background(Black01)
                                )
                            },
                            divider = {}
                        ) {
                            tabItems.forEachIndexed { index, item ->
                                val selected = tabIndex == index
                                Tab(
                                    modifier = Modifier
                                        .height(48.dp)
                                        .padding(horizontal = 20.dp),
                                    selected = selected,
                                    selectedContentColor = Black01,
                                    unselectedContentColor = Black03,
                                    onClick = { tabIndex = index }
                                ) {
                                    Text(
                                        text = stringResource(item.titleId),
                                        textAlign = TextAlign.Center,
                                        style = if (selected) {
                                            MaterialTheme.typography.headlineMedium.copy(
                                                fontSize = 15.fsp
                                            )
                                        } else {
                                            MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = 15.fsp,
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        when (tabIndex) {
                            0 -> AlarmDetailsContent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 24.dp
                                    ),
                                groupDetails = groupDetails,
                                parsedAlarmTime = parsedAlarmTime,
                                parsedAlarmDays = parsedAlarmDays,
                                parsedAlarmEndDate = parsedAlarmEndDate
                            )

                            1 -> PrivateSetting(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 24.dp
                                    ),
                                alarmType = privateSetting?.alarmType ?: "",
                                musicTitle = privateSetting?.musicTitle ?: "",
                                volume = privateSetting?.volume ?: 0
                            )
                        }

                    }
                }

                UserGroupType.NonParticipant -> {
                    AlarmDetailsContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = White)
                            .padding(
                                horizontal = 20.dp,
                                vertical = 24.dp
                            ),
                        groupDetails = groupDetails,
                        parsedAlarmTime = parsedAlarmTime,
                        parsedAlarmDays = parsedAlarmDays,
                        parsedAlarmEndDate = parsedAlarmEndDate
                    )
                }

                null -> Unit
            }

        }
    }
}

@Composable
private fun AlarmDetailsShimmer(modifier: Modifier) {
    val shape = RoundedCornerShape(4.dp)
    Column(modifier = modifier) {
        ShimmerBox(
            modifier = Modifier
                .size(58.dp, 16.dp)
                .clip(shape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(58.dp, 16.dp)
                    .clip(shape)
            )

            Column {
                ShimmerBox(
                    modifier = Modifier
                        .size(58.dp, 16.dp)
                        .clip(shape)
                )

                Spacer(modifier = Modifier.height(10.dp))

                ShimmerBox(
                    modifier = Modifier
                        .size(110.dp, 16.dp)
                        .clip(shape)
                )

            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(58.dp, 16.dp)
                    .clip(shape)
            )

            ShimmerBox(
                modifier = Modifier
                    .size(175.dp, 16.dp)
                    .clip(shape)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(58.dp, 16.dp)
                    .clip(shape)
            )

            ShimmerBox(
                modifier = Modifier
                    .size(113.dp, 16.dp)
                    .clip(shape)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(58.dp, 16.dp)
                    .clip(shape)
            )

            ShimmerBox(
                modifier = Modifier
                    .size(26.dp, 16.dp)
                    .clip(shape)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun AlarmDetailsShimmer_Preview() {
    AljyoTheme {
        AlarmDetailsShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .background(White),
        )
    }
}

@Composable
private fun AlarmDetailsContent(
    modifier: Modifier = Modifier,
    groupDetails: GroupDetails?,
    parsedAlarmTime: String,
    parsedAlarmDays: String,
    parsedAlarmEndDate: String
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.alarm_information),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 16.fsp,
                color = Black01
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            RowText(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.alarm_time),
                content = "$parsedAlarmTime\n$parsedAlarmDays"
            )

            RowText(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.alarm_end_date),
                content = parsedAlarmEndDate
            )

            RowText(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.alarm_group_participants),
                content = stringResource(
                    R.string.alarm_participation_status,
                    groupDetails?.currentPerson ?: 0,
                    groupDetails?.maxPerson ?: 0
                )
            )

            RowText(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.alarm_pulbic_or_private),
                content = if (groupDetails?.isPublic != false)
                    stringResource(R.string.public_group)
                else
                    stringResource(R.string.private_group)
            )

            RowText(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.alarm_release_content),
                content = when (groupDetails?.alarmUnlockContents ?: "") {
                    "CARD" -> stringResource(R.string.alarm_unlock_contents_card)
                    "SLIDE" -> stringResource(R.string.alarm_unlock_contents_slide)
                    else -> ""
                }
            )
        }
    }
}

@Composable
fun PrivateSetting(
    modifier: Modifier = Modifier,
    alarmType: String,
    musicTitle: String,
    volume: Int
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.alarm_information),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 16.fsp,
                color = Black01
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            RowText(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.alarm_method),
                content = when (alarmType) {
                    "SOUND" -> "소리"
                    "ALL" -> "소리, 진동"
                    "VIBRATION" -> "진동"
                    else -> ""
                }
            )
            if (alarmType != "VIBRATION") {
                RowText(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.alarm_music),
                    content = musicTitle
                )
                RowText(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.alarm_sound),
                    content = "$volume%"
                )
            }
        }
    }
}

@Composable
private fun RowText(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.fsp,
                color = Black03
            )
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.fsp,
                lineHeight = 24.fsp,
                color = Black02
            ),
            textAlign = TextAlign.End
        )
    }
}