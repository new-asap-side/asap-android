package com.asap.aljyo.ui.composable.alarm_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import kotlin.random.Random

sealed class AlarmIllustration(
    val titleId: Int,
    val descriptionId: Int,
    val illustration: Int
) {
    data object WaterDrop : AlarmIllustration(
        titleId = R.string.title_water_drop,
        descriptionId = R.string.description_water_drop,
        illustration = R.drawable.img_result_illust_water_drop
    )

    data object Flower : AlarmIllustration(
        titleId = R.string.title_flower,
        descriptionId = R.string.description_flower,
        illustration = R.drawable.img_result_illust_flower
    )

    data object Rainbow : AlarmIllustration(
        titleId = R.string.title_rainbow,
        descriptionId = R.string.description_rainbow,
        illustration = R.drawable.img_result_illust_rainbow
    )

    data object Cloud : AlarmIllustration(
        titleId = R.string.title_cloud,
        descriptionId = R.string.description_cloud,
        illustration = R.drawable.img_result_illust_cloud
    )
}

private val fortunes = listOf(
    AlarmIllustration.WaterDrop,
    AlarmIllustration.Flower,
    AlarmIllustration.Rainbow,
    AlarmIllustration.Cloud,
)

@Composable
internal fun FortuneCard(
    modifier: Modifier = Modifier,
    index: Int
) {
    val fortune by remember { mutableStateOf(fortunes[index]) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_light),
            contentDescription = "Light"
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .paint(
                    painter = painterResource(R.drawable.img_alarm_result_bg),
                    contentScale = ContentScale.FillWidth
                )
                .aspectRatio(280f / 321f, false)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(fortune.titleId),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        color = Black01
                    )
                )

                Text(
                    text = stringResource(fortune.descriptionId),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        color = Black02
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(fortune.illustration),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Release alarm result illustration"
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        val index = Random.run { nextInt(fortunes.size) }
        FortuneCard(
            modifier = Modifier.fillMaxWidth(),
            index = index
        )
    }
}