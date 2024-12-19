package com.asap.aljyo.ui.composable.release_alarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme

sealed interface CardState {
    data object Normal : CardState
    data object Fail : CardState
    data object Success : CardState
}

@Composable
internal fun CardItem(
    modifier: Modifier = Modifier,
    resourceId: Int,
    onSelect: () -> Boolean,
) {
    val cardState = remember {
        mutableStateOf<CardState>(CardState.Normal)
    }

    key(cardState.value) {
        when (cardState.value) {
            CardState.Fail ->
                Image(
                    modifier = modifier,
                    painter = painterResource(R.drawable.ic_fail_card),
                    contentDescription = "Normal card",
                )

            CardState.Normal ->
                Image(
                    modifier = modifier.clickable {
                        val result = onSelect()
                        cardState.value = if (result) {
                            CardState.Success
                        } else {
                            CardState.Fail
                        }
                    },
                    painter = painterResource(R.drawable.ic_card),
                    contentDescription = "Normal card",
                )

            CardState.Success ->
                Box(
                    modifier = modifier.paint(
                        painter = painterResource(R.drawable.ic_empty_card),
                        contentScale = ContentScale.FillHeight,
                    ),
                ) {
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center),
                        painter = painterResource(resourceId),
                        contentDescription = "Normal card",
                    )
                }
        }
    }

}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        CardItem(
            modifier = Modifier.size(90.dp, 130.dp),
            resourceId = R.drawable.img_illust_water_drop
        ) {
            false
        }
    }
}