package com.asap.aljyo.ui.composable.alarm_off

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.core.components.viewmodel.AlarmOffViewModel
import com.asap.aljyo.core.components.viewmodel.CalculatorViewModel

interface AlarmOffViewModelProvider <out VM: AlarmOffViewModel> {
    @Composable
    fun provide(): VM
}

internal class GeneralProvider : AlarmOffViewModelProvider<AlarmOffViewModel> {
    @Composable
    override fun provide(): AlarmOffViewModel = hiltViewModel()
}

internal class CalculatorViewModelProvider : AlarmOffViewModelProvider<CalculatorViewModel> {
    @Composable
    override fun provide(): CalculatorViewModel = hiltViewModel()

}

sealed class AlarmContent(
    open val id: Int,
    open val title: String,
    val description: String,
) {
    protected val modifier: Modifier = Modifier.fillMaxSize()

    @Composable
    abstract fun Effect(navigateToResult: () -> Unit)

    @Composable
    abstract fun Title(title: String)

    @Composable
    abstract fun Content()
}
