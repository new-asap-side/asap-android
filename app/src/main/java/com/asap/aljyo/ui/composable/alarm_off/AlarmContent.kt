package com.asap.aljyo.ui.composable.alarm_off

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel

sealed class AlarmContent(
    open val id: Int,
    open val title: String,
    val description: String,
) {
    protected val modifier: Modifier = Modifier.fillMaxSize()

    @Composable
    abstract fun Content(viewModel: AlarmOffViewModel)

}


data class Card(override val id: Int, override val title: String) :
    AlarmContent(id, title, "빠르게 카드를 모두 터치하여\n알람 해제!") {
    @Composable
    override fun Content(viewModel: AlarmOffViewModel) {
        Box(modifier = modifier)
    }

}
