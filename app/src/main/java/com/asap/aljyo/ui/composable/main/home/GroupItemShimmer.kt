package com.asap.aljyo.ui.composable.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.ui.composable.common.loading.ShimmerBox
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.ShimmerBaseDark

@Composable
internal fun GroupItemShimmer(modifier: Modifier = Modifier) {
    val shape6dp = RoundedCornerShape(6.dp)
    val shape4dp = RoundedCornerShape(4.dp)
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape6dp)
            )

            ShimmerBox(
                modifier = Modifier
                    .offset(8.dp, 8.dp)
                    .size(53.dp, 24.dp)
                    .clip(shape4dp),
                baseColor = ShimmerBaseDark
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(shape4dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        ShimmerBox(
            modifier = Modifier
                .size(69.dp, 14.dp)
                .clip(shape4dp)
        )

        Spacer(modifier = Modifier.height(13.dp))

        Row {
            ShimmerBox(
                modifier = Modifier
                    .size(49.dp, 22.dp)
                    .clip(shape4dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            ShimmerBox(
                modifier = Modifier
                    .size(42.dp, 22.dp)
                    .clip(shape4dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ShimmerBox(
                modifier = Modifier
                    .size(14.dp, 14.dp)
                    .clip(shape4dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            ShimmerBox(
                modifier = Modifier
                    .size(28.dp, 12.dp)
                    .clip(shape4dp)
            )
        }
    }

}

@Preview
@Composable
private fun Preview_GroupItemShimmer() {
    AljyoTheme {
        GroupItemShimmer(modifier = Modifier.width(148.dp))
    }
}