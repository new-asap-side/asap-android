package com.asap.aljyo.core.components.viewmodel

import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.alarm.AlarmOffUseCase
import com.asap.utility.calculator.ArithmeticOperation
import com.asap.utility.calculator.randomOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    alarmOffUseCase: AlarmOffUseCase
) : AlarmOffViewModel(alarmOffUseCase) {
    private var solveCount = 0

    private val _operation = MutableStateFlow(randomOperation())
    val operation get() = _operation.asStateFlow()

    // 선택지 enable state
    private val _enable = MutableStateFlow(true)
    val enable get() = _enable.asStateFlow()

    // 선택한 오답 index state
    private val _selectedIndex = MutableStateFlow(-1)
    val selectedIndex get() = _selectedIndex.asStateFlow()

    fun emit(groupId: Int, index: Int, value: Int) {
        if(_operation.value.isAnswer(value)) {
            handleAnswer(groupId)
            return
        }
        handleWrongAnswer(index)
    }

    private fun handleAnswer(groupId: Int) {
        solveCount ++

        if (solveCount == ArithmeticOperation.GAME_COUNT) {
            alarmOff(groupId)
            return
        }
        viewModelScope.launch {
            _operation.emit(randomOperation())
        }
    }

    private fun handleWrongAnswer(index: Int) = viewModelScope.launch {
        _enable.emit(false)
        _selectedIndex.emit(index)

        delay(1000)
        _enable.emit(true)
        _selectedIndex.emit(-1)
    }
}