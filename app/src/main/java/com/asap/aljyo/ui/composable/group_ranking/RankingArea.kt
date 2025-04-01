package com.asap.aljyo.ui.composable.group_ranking

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.ProfileBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.entity.remote.GroupRanking

@Composable
internal fun RankingArea(
    modifier: Modifier = Modifier,
    rankings: List<GroupRanking>,
    mIndex: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RankingProfile(
            painter = painterResource(R.drawable.ic_silver_crown),
            size = 86.dp,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.fsp,
                color = Black01,
            ),
            isShowMeBadge = mIndex == 1,
            ranking = rankings.getOrNull(1)
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                text = "${rankings.getOrNull(1)?.rankScore ?: 0}점",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.fsp,
                    color = Black03
                )
            )
        }
        
        RankingProfile(
            painter = painterResource(R.drawable.ic_gold_crown),
            size = 117.dp,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 16.fsp,
                color = Black01,
            ),
            isShowMeBadge = mIndex == 0,
            ranking = rankings.getOrNull(0),
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                text = "${DecimalFormat("#,###").format(rankings.getOrNull(0)?.rankScore ?: 0)}점",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 14.fsp,
                    color = White
                )
            )
        }

        RankingProfile(
            painter = painterResource(R.drawable.ic_bronze_crown),
            size = 86.dp,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.fsp,
                color = Black01,
            ),
            isShowMeBadge = mIndex == 2,
            ranking = rankings.getOrNull(2)
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                text = "${rankings.getOrNull(2)?.rankScore ?: 0}점",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.fsp,
                    color = Black03
                )
            )
        }
    }
}

@Composable
private fun RankingProfile(
    modifier: Modifier = Modifier,
    painter: Painter,
    size: Dp,
    style: TextStyle,
    isShowMeBadge: Boolean = false,
    ranking: GroupRanking?,
    score: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            ProfileBox(
                modifier = Modifier.size(size),
                profileImagePadding = 12.dp,
                profileImage = ranking?.thumbnail ?: "",
                profileItem = PictureUtil.getProfileItemByName(ranking?.profileItem),
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-10).dp),
                painter = painter,
                tint = Color.Unspecified,
                contentDescription = "Ranking profile crown"
            )
            if (isShowMeBadge) {
                MeBadge(
                    modifier = Modifier
                        .padding(bottom = 13.dp)
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = ranking?.nickName ?: "-",
            style = style
        )
        Spacer(modifier = Modifier.height(4.dp))
        score()
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFEEF3)
@Composable
private fun Preview() {
    AljyoTheme {
        RankingArea(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 30.dp
                ),
            rankings = listOf(
                GroupRanking(
                    nickName = "NICKNAME",
                    thumbnail = "",
                    rankScore = 300,
                    rankNumber = 1,
                    createdAt = "21:30:01",
                    profileItem = null
                ),
                GroupRanking(
                    nickName = "NICKNAME",
                    thumbnail = "",
                    rankScore = 200,
                    rankNumber = 2,
                    createdAt = "21:30:01",
                    profileItem = null
                ),
                GroupRanking(
                    nickName = "NICKNAME",
                    thumbnail = "",
                    rankScore = 100,
                    rankNumber = 3,
                    createdAt = "21:30:01",
                    profileItem = null
                )
            ),
            mIndex = 0
        )
    }
}