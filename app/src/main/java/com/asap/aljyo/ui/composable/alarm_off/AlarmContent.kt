package com.asap.aljyo.ui.composable.alarm_off

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.Black00
import com.asap.domain.entity.remote.alarm.AlarmUnlockContent

sealed class AlarmContent(
    open val title: String,
    val description: String,
) {
    @Composable
    abstract fun Content(modifier: Modifier)
}

data class Slide(override val title: String) : AlarmContent(title, "옆으로 밀어서 알람 해제!") {
    @Composable
    override fun Content(modifier: Modifier) {
        Box(modifier = modifier.background(Black00)) {

        }
    }

}

data class Card(override val title: String) : AlarmContent(title, "빠르게 카드를 모두 터치하여\n알람 해제!") {
    @Composable
    override fun Content(modifier: Modifier) {

    }

}
