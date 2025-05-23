package com.asap.aljyo.ui.composable.alarm_result

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.components.alarm_result.RankingState
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.ProfileBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.util.PictureUtil

@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    title: String,
    rankState: RankingState,
) {
    Column(
        modifier = modifier
            .aspectRatio(280f / 393f)
            .paint(
                painter = painterResource(R.drawable.img_alarm_result_bg),
                contentScale = ContentScale.FillBounds
            )
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(top = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.alarm_off_result_title, rankState.nickname
                ),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.fsp,
                    color = Black02
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.alarm_rank, rankState.rank),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 32.fsp,
                    color = Black01
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // profile image
//            AsyncImage(
//                model = rankState.profile,
//                contentDescription = "Profile Image",
//                contentScale = ContentScale.Fit,
//                error = painterResource(R.drawable.ic_empty_profile),
//                modifier = Modifier
//                    .size(152.dp)
//                    .clip(CircleShape)
//            )
            ProfileBox(
                modifier = Modifier.size(152.dp),
                profileImage = rankState.profile,
                profileItem = PictureUtil.getProfileItemByName(rankState.profileItem),
                profileImagePadding = 16.dp,
            )

            Icon(
                painter = painterResource(R.drawable.ic_fanfare),
                contentDescription = "ranking fanfare",
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color(0xFFDBBEC9)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.group_name),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 14.fsp,
                        color = Black02
                    )
                )

                Spacer(modifier = Modifier.width(21.dp))

                Text(
                    text = title,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp,
                        color = Black01
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.alarm_point),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 14.fsp,
                        color = Black02
                    )
                )

                Text(
                    text = stringResource(R.string.point, rankState.score),
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp,
                        color = Black01
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun ResultCardPreview() {
    AljyoTheme {
        ResultCard(
            modifier = Modifier.width(280.dp),
            title = "title",
            rankState = RankingState(
                nickname = "알죠",
                profile = "",
                rank = "1",
                score = 2000
            )
        )
    }
}