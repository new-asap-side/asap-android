package com.asap.aljyo.core.components.viewmodel

import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.alarm.AlarmOffUseCase
import com.asap.utility.calculator.ArithmeticOperation
import com.asap.utility.calculator.randomOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
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

    // 계산기 문제 정보
    private val _operation = MutableStateFlow(randomOperation())
    val operation get() = _operation.asStateFlow()

    // 답안지 리스트 정보
    private val _choiceState = MutableStateFlow(ChoiceState(index = -1))
    val choiceState get() = _choiceState.asStateFlow()

    // 사용자 답안지 선택 이벤트 처리
    fun emit(groupId: Int, index: Int, value: Int) {
        viewModelScope.launch {
            // 답안지 선택 비활성화
            _choiceState.emit(_choiceState.value.disable(index))
            _operation.value.isAnswer(value).let { answer ->
                delay(if (answer) 1000 else 2000)
                if (answer) {
                    solveCount++

                    if (solveCount == ArithmeticOperation.GAME_COUNT) {
                        alarmOff(groupId)
                        return@launch
                    }
                }
                _choiceState.emit(_choiceState.value.enable())

                if (answer) {
                    // animation duration
                    delay(200)
                    // 새로운 문제 생성
                    _operation.emit(randomOperation())
                }
            }
        }
    }
}