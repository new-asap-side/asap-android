package com.asap.aljyo.ui.composable.release_alarm

import android.os.VibrationEffect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.manager.VibratorManager
import kotlin.random.Random

private const val maximumCardNumber = 5

@Composable
internal fun SelectCardArea(
    modifier: Modifier = Modifier,
    resourceId: Int,
    onComplete: () -> Unit = {},
) {
    val targetIndex by remember {
        val index = Random.run { nextInt(maximumCardNumber) }
        mutableIntStateOf(index)
    }

    val context = LocalContext.current
    val cardModifier = Modifier.size(90.dp, 130.dp)
    val vibratorManager = VibratorManager(context)

    val onSelect: (Int) -> Unit = { index ->
        if (targetIndex == index) {
            // success
            onComplete()
        } else {
            // vibrate
            vibratorManager.vibrate(
                VibrationEffect.createOneShot(
                    100L, VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CardItem(
                modifier = cardModifier,
                resourceId = resourceId
            ) {
                onSelect(0)
                targetIndex == 0
            }
            Spacer(modifier = Modifier.width(15.dp))
            CardItem(
                modifier = cardModifier,
                resourceId = resourceId
            ) {
                onSelect(1)
                targetIndex == 1
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CardItem(
                modifier = cardModifier,
                resourceId = resourceId
            ) {
                onSelect(2)
                targetIndex == 2
            }
            Spacer(modifier = Modifier.width(15.dp))
            CardItem(
                modifier = cardModifier,
                resourceId = resourceId
            ) {
                onSelect(3)
                targetIndex == 3
            }
            Spacer(modifier = Modifier.width(15.dp))
            CardItem(
                modifier = cardModifier,
                resourceId = resourceId
            ) {
                onSelect(4)
                targetIndex == 4
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SelectCardArea(
        modifier = Modifier.wrapContentWidth(),
        resourceId = R.drawable.img_illust_water_drop,
    )
}