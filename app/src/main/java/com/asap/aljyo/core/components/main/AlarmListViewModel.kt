package com.asap.aljyo.core.components.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.data.utility.DateTimeManager
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.usecase.alarm.ActivateAlarmUseCase
import com.asap.domain.usecase.alarm.DeactivateAlarmUseCase
import com.asap.domain.usecase.alarm.GetDeactivatedAlarmListUseCase
import com.asap.domain.usecase.group.FetchAlarmListUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDeactivatedAlarmListUseCase: GetDeactivatedAlarmListUseCase,
    private val activateAlarmUseCase: ActivateAlarmUseCase,
    private val deactivateAlarmUseCase: DeactivateAlarmUseCase
) : ViewModel() {
    private var active = true

    private val _nickname = mutableStateOf("")
    val nickname get() = _nickname.value

    private val _alarmList = MutableStateFlow<UiState<List<AlarmSummary>?>>(UiState.Loading)
    val alarmList get() = _alarmList.asStateFlow()

    private val _deactivatedAlarms = mutableSetOf<Int>()

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
        viewModelScope.launch {
            _deactivatedAlarms.addAll(getDeactivatedAlarmListUseCase().map { it.groupId })
            Log.d("VM", "deactivated list - $_deactivatedAlarms")
        }

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

    fun onCheckChanged(check: Boolean, alarmSummary: AlarmSummary): Deferred<Boolean> {
        return if (check) deactivate(alarmSummary) else activate(alarmSummary)
    }

    private fun activate(alarmSummary: AlarmSummary) = viewModelScope.async {
        activateAlarmUseCase(alarmSummary)
    }

    private fun deactivate(alarmSummary: AlarmSummary) = viewModelScope.async {
        deactivateAlarmUseCase(alarmSummary)
    }

    fun isDeactivatedAlarm(groupId: Int): Boolean = _deactivatedAlarms.contains(groupId)

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

    // composable lifecycle listener
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