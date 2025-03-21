package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.ProfileBox
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.entity.remote.GroupRanking

@Stable
@Composable
fun TodayRankingPage(
    modifier: Modifier,
    ranks: List<GroupRanking>
) {
    ranks.run {
        if (isEmpty()) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(90.dp, 86.dp),
                    painter = painterResource(R.drawable.ic_empty_alarm),
                    contentDescription = "Empty alarm icon",
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.no_goes_off_yet),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 22.fsp,
                        lineHeight = 30.fsp,
                        color = Black01
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(modifier = modifier) {
                item { Spacer(modifier = Modifier.height(20.dp)) }

                item {
                    forEachIndexed { index, rank ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 20.dp)
                                .height(36.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(R.string.alarm_rank, "${index + 1}"),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.fsp,
                                    color = Black03
                                )
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            ProfileBox(
                                modifier = Modifier.size(46.dp),
                                profileItem = PictureUtil.getProfileItemByName(rank.profileItem),
                                profileImage = rank.thumbnail,
                                profileImagePadding = 5.dp,
                                profileItemPadding = 2.dp,
                            )

                            Spacer(modifier = Modifier.width(11.dp))

                            Text(
                                modifier = Modifier.weight(1f),
                                text = rank.nickName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.fsp,
                                    color = Black01
                                ),
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = rank.createdAt,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 14.fsp,
                                    color = Black03
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}