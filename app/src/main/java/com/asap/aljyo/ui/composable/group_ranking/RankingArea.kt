package com.asap.aljyo.ui.composable.group_ranking

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.GroupRanking

@Composable
internal fun RankingArea(
    modifier: Modifier = Modifier,
    rankings: List<GroupRanking>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RankingProfile(
            painter = painterResource(R.drawable.ic_silver_crown),
            size = 68.dp,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Black01,
            ),
            ranking = rankings[1]
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                text = "${rankings[1].score}점",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    color = Black03
                )
            )
        }
        RankingProfile(
            painter = painterResource(R.drawable.ic_gold_crown),
            size = 92.dp,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 16.sp,
                color = Black01,
            ),
            isShowMeBadge = true,
            ranking = rankings[0]
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                text = "${rankings[0].score}점",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 14.sp,
                    color = White
                )
            )
        }
        RankingProfile(
            painter = painterResource(R.drawable.ic_bronze_crown),
            size = 68.dp,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Black01,
            ),
            ranking = rankings[2]
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 2.dp
                    ),
                text = "${rankings[2].score}점",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
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
    ranking: GroupRanking,
    score: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ranking.thumbnail,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                error = painterResource(R.drawable.ic_my_page),
                contentDescription = "Ranking profile thumbnail"
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-29).dp),
                painter = painter,
                tint = Color.Unspecified,
                contentDescription = "Ranking profile crown"
            )
            if (isShowMeBadge) {
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
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = ranking.nickname,
            style = style
        )
        Spacer(modifier = Modifier.height(4.dp))
        score()
    }

}

@Composable
private fun MeBadge(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "ME!",
        style = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.primary
        )
    )
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
            rankings = (0..3).map { GroupRanking.dummy()}
        )
    }
}