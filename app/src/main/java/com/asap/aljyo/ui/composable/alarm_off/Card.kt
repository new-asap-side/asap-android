package com.asap.aljyo.ui.composable.alarm_off

import android.os.VibrationEffect
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.components.service.AlarmService
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.core.manager.VibratorManager
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.theme.White

data class Card(
    override val id: Int, override val title: String
) : AlarmContent(id, title, "빠르게 카드를 모두 터치하여\n알람 해제!"),
    AlarmOffViewModelProvider<AlarmOffViewModel> by GeneralProvider() {
    private val cardCount = 5
    private val cardModifier = Modifier.size(90.dp, 130.dp)

    @Composable
    override fun Effect(navigateToResult: () -> Unit) {
        val viewModel = provide()
        val context = LocalContext.current
        val requestState by viewModel.state.collectAsState()

        LaunchedEffect(requestState) {
            when (requestState) {
                RequestState.Initial, RequestState.Loading -> Unit

                is RequestState.Success -> {
                    val result = (requestState as RequestState.Success).data
                    if (result) {
                        // 알람 서비스 종료
                        AlarmService.stopAlarmService(context)
                        navigateToResult()
                    }
                }

                is RequestState.Error -> {
                    Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    @Composable
    override fun Title(title: String) {
        val viewModel = provide()
        val time = viewModel.currentTime

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = time,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 86.fsp,
                color = White
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(White.copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "clock",
                tint = White
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.fsp,
                    color = White
                )
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 24.fsp,
                color = White
            ),
            textAlign = TextAlign.Center
        )

    }

    @Composable
    override fun Content() {
        var selectedCardCount by remember { mutableIntStateOf(0) }
        val viewModel = provide()
        val onClick: () -> Unit = { selectedCardCount++ }

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
