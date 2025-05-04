package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import asap.aljyo.presentation.theme.AljyoTheme
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.ProfileBox
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.entity.remote.GroupRanking

@Composable
fun MeBadge(
    modifier: Modifier = Modifier,
    style: TextStyle? = null
) {
    Text(
        modifier = modifier,
        text = "ME!",
        style = style ?: MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun MyRankingBar(
    modifier: Modifier = Modifier,
    rankInfo: GroupRanking,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Red02)
            .padding(vertical = 14.dp, horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = rankInfo.rankNumber.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.fsp,
                color = Black03
            )
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.size(46.dp)) {
                ProfileBox(
                    modifier = Modifier.fillMaxSize(),
                    profileItem = PictureUtil.getProfileItemByName(rankInfo.profileItem),
                    profileImage = rankInfo.thumbnail,
                    profileImagePadding = 5.dp,
                )
            }
            MeBadge(
                modifier = Modifier
                    .padding(start = 11.dp, end = 4.dp)
                    .clip(RoundedCornerShape(100))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(100)
                    )
                    .background(White)
                    .padding(
                        horizontal = 6.5.dp,
                        vertical = 1.5.dp
                    ),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 11.fsp
                )
            )
            Text(
                text = rankInfo.nickName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.fsp,
                    color = Black01
                )
            )
        }
        Text(
            text = "${rankInfo.rankScore}점",
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.fsp,
                color = Black03
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewMyRankingBar() {
    AljyoTheme {
        MyRankingBar(
            rankInfo = GroupRanking(
                nickName = "NICKNAME",
                thumbnail = "",
                rankScore = 10,
                rankNumber = 6,
                createdAt = "21:30:01",
                profileItem = "100_000"
            )
        )
    }
}