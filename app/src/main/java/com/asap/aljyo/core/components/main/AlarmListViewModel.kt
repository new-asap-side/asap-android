package com.asap.aljyo.core.components.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.data.utility.DateTimeManager
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.usecase.group.FetchAlarmListUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
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

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val fetchAlarmListUseCase: FetchAlarmListUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private var active = true

    private val _nickname = mutableStateOf("")
    val nickname get() = _nickname.value

    private val _alarmList = MutableStateFlow<UiState<List<AlarmSummary>?>>(UiState.Loading)
    val alarmList get() = _alarmList.asStateFlow()

    private val fastestAlarmQueue: Queue<String> = LinkedList()
    private val _fastestAlarmTimeFlow = flow {
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

        val userInfo = getUserInfoUseCase()
        _nickname.value = userInfo?.nickname ?: ""

        fetchAlarmListUseCase(userInfo?.userId?.toInt() ?: -1).catch { e ->
            val errorCode = when (e) {
                is HttpException -> e.code()
                else -> -1
            }
            _alarmList.value = UiState.Error(errorCode = errorCode)
        }.collect { result ->
            sortedByFastest(result ?: emptyList())
            observeFastestAlarmTime()

            _alarmList.value = UiState.Success(result)
        }
    }

    private fun sortedByFastest(alarms: List<AlarmSummary>) {
        mutableListOf<String>().apply {
            alarms.forEach { alarm ->
                addAll(alarm.group.alarmDays.map { alarm.group.parse(it) })
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
        if (!active) {
            active = true
            observeFastestAlarmTime()
        }
    }

    fun pause() {
        active = false
    }

    fun dispose() {
        active = false
        fastestAlarmQueue.clear()
    }

}