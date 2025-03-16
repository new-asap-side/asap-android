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

data class ChoiceState(
    val enable: Boolean = true,
    val index: Int,
) {
    fun enable() = this.copy(enable = true, index = -1)

    fun disable(index: Int) = this.copy(enable = false, index = index)
}

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    alarmOffUseCase: AlarmOffUseCase
) : AlarmOffViewModel(alarmOffUseCase) {
    private var solveCount = 0

    private val _operation = MutableStateFlow(randomOperation())
    val operation get() = _operation.asStateFlow()

    private val _choiceState = MutableStateFlow(ChoiceState(index = -1))
    val choiceState get() = _choiceState.asStateFlow()

    // 사용자 선택 이벤트 처리
    fun emit(groupId: Int, index: Int, value: Int) {
        viewModelScope.launch {
            _choiceState.emit(_choiceState.value.disable(index))
            val answer = _operation.value.isAnswer(value)
            delay(
                if (answer) {
                    solveCount++
                    1000
                } else {
                    2000
                }
            )

            if (solveCount == ArithmeticOperation.GAME_COUNT) {
                alarmOff(groupId)
                return@launch
            }
            _choiceState.emit(_choiceState.value.enable())
            _operation.emit(randomOperation())
        }
    }
}