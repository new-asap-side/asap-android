package com.asap.aljyo.ui.composable.group_ranking

import android.graphics.Paint.Align
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.ProfileBox
import com.asap.aljyo.ui.composable.common.extension.dropShadow
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Blue
import com.asap.aljyo.ui.theme.Red01
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
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RankingProfile(
            modifier = Modifier.padding(top = 20.dp),
            painter = painterResource(R.drawable.ic_silver_crown),
            isShowMeBadge = mIndex == 1,
            ranking = rankings.getOrNull(1)
        ) {
            Text(
                text = "${DecimalFormat("#,###").format(rankings.getOrNull(0)?.rankScore ?: 0)}점",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.fsp,
                    color = Black03,
                )
            )
        }

        RankingProfile(
            modifier = Modifier.dropShadow(
                shape = RoundedCornerShape(12.dp),
                blur = 12.dp,
                color = Color(0xFFFFECF8),
            ),
            painter = painterResource(R.drawable.ic_gold_crown),
            isShowMeBadge = mIndex == 0,
            ranking = rankings.getOrNull(0),
        ) {
            Text(
                text = "${DecimalFormat("#,###").format(rankings.getOrNull(0)?.rankScore ?: 0)}점",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.fsp,
                    color = Red01,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        RankingProfile(
            modifier = Modifier.padding(top = 20.dp),
            painter = painterResource(R.drawable.ic_bronze_crown),
            isShowMeBadge = mIndex == 2,
            ranking = rankings.getOrNull(2)
        ) {
            Text(
                text = "${DecimalFormat("#,###").format(rankings.getOrNull(0)?.rankScore ?: 0)}점",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.fsp,
                    color = Black03,
                )
            )
        }
    }
}

@Composable
private fun RankingProfile(
    modifier: Modifier = Modifier,
    painter: Painter,
    isShowMeBadge: Boolean = false,
    ranking: GroupRanking?,
    score: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .width(102.dp)
            .background(color = White, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Icon(
            modifier = Modifier
                .offset(y = (-30).dp),
            painter = painter,
            tint = Color.Unspecified,
            contentDescription = "Ranking profile crown"
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileBox(
                modifier = Modifier
                    .size(71.dp),
                profileImagePadding = 7.dp,
                profileImage = ranking?.thumbnail ?: "",
                profileItem = PictureUtil.getProfileItemByName(ranking?.profileItem),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ranking?.nickName ?: "-",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.fsp,
                    color = Black01,
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            score()
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
//            if (isShowMeBadge) {
//                MeBadge(
//                    modifier = Modifier
//                        .padding(bottom = 13.dp)
//                        .align(Alignment.BottomCenter)
//                        .clip(RoundedCornerShape(100))
//                        .border(
//                            width = 1.dp,
//                            color = MaterialTheme.colorScheme.primary,
//                            shape = RoundedCornerShape(100)
//                        )
//                        .background(White)
//                        .padding(
//                            horizontal = 6.5.dp,
//                            vertical = 1.5.dp
//                        ),
//                    style = MaterialTheme.typography.labelMedium.copy(
//                        color = MaterialTheme.colorScheme.primary,
//                        fontSize = 11.fsp
//                    )
//                )
//            }
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