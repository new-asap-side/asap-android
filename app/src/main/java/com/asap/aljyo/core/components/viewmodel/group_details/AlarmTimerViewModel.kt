package com.asap.aljyo.core.components.viewmodel.group_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.utility.datetime.DateTimeCalculator
import com.asap.utility.datetime.Seconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmTimerViewModel @Inject constructor(
    @Seconds private val calculator: DateTimeCalculator
): ViewModel() {
    private val _remainTime = MutableStateFlow("")
    val remainTime get() = _remainTime.asStateFlow()

    fun start(days: String, time: String) {
        viewModelScope.launch {
            while(true) {
                _remainTime.emit(calculator.calculate(days, time).await())
                delay(1000L)
            }
        }
    }
}