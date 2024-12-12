package com.asap.aljyo.core.components.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.data.utility.DateTimeManager
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.usecase.user.FetchAlarmListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

private val tempAlarms = listOf(
    Alarm(
        alarmDay = "목 금",
        alarmTime = "17:33"
    ),
    Alarm(
        alarmDay = "목 금",
        alarmTime = "13:30"
    ),
    Alarm(
        alarmDay = "월 화 수 목 금",
        alarmTime = "14:30"
    ),
    Alarm(
        alarmDay = "화 토",
        alarmTime = "15:30"
    ),
    Alarm(alarmTime = "12:30")
)

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val fetchAlarmListUseCase: FetchAlarmListUseCase
) : ViewModel() {
    private var active = true

    private val _alarmList = MutableStateFlow<UiState<List<Alarm>>>(UiState.Loading)
    val alarmList get() = _alarmList.asStateFlow()

    private val fastestAlarmQueue: Queue<String> = LinkedList()
    private val _fastestAlarmTimeFlow = flow {
        Log.d("VM", "emit alarm time !")
        val fastest = fastestAlarmQueue.peek()
        if (fastest != null) {
            emit(fastest)
        }
    }

    private val _fastestAlarmTimeState = MutableStateFlow("")
    val fastestAlarmTimeState get() = _fastestAlarmTimeState.asStateFlow()

    init {
        fetchAlarmList()
    }

    fun fetchAlarmList() = viewModelScope.launch {
        _alarmList.value = UiState.Loading
        fetchAlarmListUseCase.invoke().catch { e ->
            if (e is HttpException) {
                _alarmList.value = UiState.Error(errorCode = e.code())
                return@catch
            }

            _alarmList.value = UiState.Error()
        }.collect { result ->
            if (result == null) {
                // _alarmList.value = UiState.Error()
                _alarmList.value =
                    UiState.Success(listOf(Alarm(), Alarm(), Alarm(), Alarm(), Alarm(), Alarm()))
                return@collect
            }
            _alarmList.value = UiState.Success(tempAlarms)
            // sortedByFastest(result)
            sortedByFastest(tempAlarms)
            observeFastestAlarmTime()
        }
    }

    // TODO 실제 데이터 반영
    private fun sortedByFastest(alarms: List<Alarm>) {
        mutableListOf<String>().apply {
            alarms.forEach { alarm ->
                addAll(alarm.alarmDay.split(" ").map { alarm.parse(it) })
            }
        }.sortedBy {
            DateTimeManager.diffFromNow(it)
        }.also {
            fastestAlarmQueue.addAll(it)
        }
    }

    private fun observeFastestAlarmTime() = viewModelScope.launch {
        while (active) {
            delay(1000L)
            _fastestAlarmTimeFlow.collect {
                val duration = DateTimeManager.diffFromNow(it)
                _fastestAlarmTimeState.value = DateTimeManager.parseToDay(duration)
                if (duration == 0L) {
                    val lastest = fastestAlarmQueue.poll()
                    fastestAlarmQueue.add(lastest)
                }
            }
        }
    }

    fun resume() {
        active = true
        observeFastestAlarmTime()
    }

    fun pause() {
        active = false
    }

    fun dispose() {
        active = false
        fastestAlarmQueue.clear()
    }

}