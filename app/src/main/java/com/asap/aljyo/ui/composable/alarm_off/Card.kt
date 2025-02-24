package com.asap.aljyo.ui.composable.alarm_off

import android.os.VibrationEffect
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel
import com.asap.aljyo.core.manager.VibratorManager

data class Card(override val id: Int, override val title: String) :
    AlarmContent(id, title, "빠르게 카드를 모두 터치하여\n알람 해제!") {
    private val cardCount = 5
    private val cardModifier = Modifier.size(
        90.dp, 130.dp
    )

    @Composable
    override fun Content(viewModel: AlarmOffViewModel) {
        var selectedCardCount by remember { mutableIntStateOf(0) }

        val onClick: () -> Unit = { selectedCardCount ++ }
        LaunchedEffect(selectedCardCount) {
            if (selectedCardCount == cardCount) {
                viewModel.alarmOff(id)
            }
        }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                repeat(2) {
                    CardItem(
                        modifier = cardModifier,
                        onClick = onClick
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(15.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                repeat(3) {
                    CardItem(
                        modifier = cardModifier,
                        onClick = onClick
                    )
                }
            }
        }

    }
}

sealed interface CardState {
    data object Front : CardState
    data object Back : CardState
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val cardState = remember {
        mutableStateOf<CardState>(CardState.Back)
    }

    val context = LocalContext.current
    val vibratorManager = VibratorManager(context)

    key(cardState.value) {
        when (cardState.value) {
            CardState.Back -> Image(
                modifier = modifier.clickable {
                    onClick()

                    cardState.value = CardState.Front
                    vibratorManager.vibrate(
                        VibrationEffect.createOneShot(
                            100L, VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                },
                painter = painterResource(R.drawable.img_card_back),
                contentDescription = "card back"
            )

            CardState.Front -> Image(
                modifier = modifier,
                painter = painterResource(R.drawable.img_card_front),
                contentDescription = "card front"
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    CardItem(modifier = Modifier.size(90.dp, 130.dp), onClick = {})
}
