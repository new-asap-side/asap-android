package com.asap.aljyo.ui.composable.group_ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.GroupRanking

@Composable
internal fun UnRankingArea(
    modifier: Modifier = Modifier,
    unRakings: List<GroupRanking>,
    mIndex: Int,
) {
    val scrollState = rememberScrollState(initial = 0)
    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        if (unRakings.isEmpty()) {
            repeat(5) { count ->
                EmptyProfile(count + 4)
            }
        } else {
            unRakings.forEachIndexed { index, rank ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = (index + 4).toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.fsp,
                            color = Black03
                        )
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.size(36.dp)) {
                            AsyncImage(
                                modifier = Modifier.clip(CircleShape),
                                model = rank.thumbnail,
                                contentDescription = "Group particular thumbnail",
                                error = painterResource(R.drawable.ic_empty_profile)
                            )
                            if (mIndex - 3 == index) {
                                MeBadge(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
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
                            }
                        }
                        Text(
                            text = rank.nickName,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.fsp,
                                color = Black01
                            )
                        )
                    }
                    Text(
                        text = "${rank.rankScore}점",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.fsp,
                            color = Black03
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyProfile(order: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = order.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.fsp,
                color = Black03
            )
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(R.drawable.ic_empty_profile),
                contentDescription = "Empty profile icon",
                tint = Color.Unspecified
            )
            Text(
                text = "-",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.fsp,
                    color = Black01
                )
            )
        }
        Text(
            text = "0점",
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.fsp,
                color = Black03
            ),
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        UnRankingArea(
            modifier = Modifier.fillMaxWidth(),
            unRakings = listOf(
                GroupRanking(
                    nickName = "NICKNAME",
                    thumbnail = "",
                    rankScore = 100,
                    rankNumber = 4,
                ),
                GroupRanking(
                    nickName = "NICKNAME",
                    thumbnail = "",
                    rankScore = 50,
                    rankNumber = 5,
                ),
                GroupRanking(
                    nickName = "NICKNAME",
                    thumbnail = "",
                    rankScore = 10,
                    rankNumber = 6,
                )
            ),
            mIndex = 3
        )
    }
}