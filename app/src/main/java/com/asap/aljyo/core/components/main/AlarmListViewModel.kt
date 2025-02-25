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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.PriorityQueue
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

    private val _nickname = mutableStateOf("-")
    val nickname get() = _nickname.value

    private val _alarmList = MutableStateFlow<UiState<List<AlarmSummary>?>>(UiState.Loading)
    val alarmList get() = _alarmList.asStateFlow()

    private val _deactivatedAlarms = mutableSetOf<Int>()

    private val alarmQueue: Queue<String> = PriorityQueue { alarm1, alarm2 ->
        val diff1 = DateTimeManager.diffFromNow(alarm1)
        val diff2 = DateTimeManager.diffFromNow(alarm2)
        return@PriorityQueue (diff1 - diff2).toInt()
    }

    private val _fastestAlarmTimeFlow = flow {
        val fastest = alarmQueue.peek()
        if (fastest != null) {
            emit(fastest)
            return@flow
        }
        _fastestAlarmTimeState.value = NO_ACTIVATED_ALARM
    }

    private val _fastestAlarmTimeState = MutableStateFlow(LOADING)
    val fastestAlarmTimeState get() = _fastestAlarmTimeState.asStateFlow()

    init {
        viewModelScope.launch {
            _deactivatedAlarms.addAll(getDeactivatedAlarmListUseCase().map { it.groupId })
            Log.d("VM", "Deactivated alarm group-id: $_deactivatedAlarms")
        }

        fetchAlarmList()
    }

    fun fetchAlarmList() = viewModelScope.launch {
        val userInfo = getUserInfoUseCase()
        _nickname.value = userInfo?.nickname ?: ""

        fetchAlarmListUseCase(userInfo?.userId?.toInt() ?: -1).catch { e ->
            Log.e(TAG, "$e")
            val errorCode = when (e) {
                is HttpException -> e.code()
                else -> -1
            }
            _alarmList.value = UiState.Error(errorCode = errorCode)
        }.collect { result ->
            initAlarmQueue(result ?: emptyList())
            observeNextAlarmTime()

            _alarmList.value = UiState.Success(result)
        }
    }

    fun onCheckChanged(check: Boolean, alarmSummary: AlarmSummary) = viewModelScope.launch {
        if (check) deactivate(alarmSummary) else activate(alarmSummary)
    }

    private suspend fun activate(alarm: AlarmSummary) {
        val isSuccess = activateAlarmUseCase(alarm)
        if (isSuccess) {
            _deactivatedAlarms.remove(alarm.groupId)
            alarmQueue.addAll(
                alarm.group.alarmDays.map { alarm.group.parse(it) }
            )
        }
    }

    private suspend fun deactivate(alarm: AlarmSummary) {
        val isSuccess = deactivateAlarmUseCase(alarm)
        if (isSuccess) {
            _deactivatedAlarms.add(alarm.groupId)
            alarm.group.alarmDays.map { alarm.group.parse(it) }.forEach {
                alarmQueue.remove(it)
            }
        }
    }

    fun isDeactivatedAlarm(groupId: Int): Boolean = _deactivatedAlarms.contains(groupId)

    private fun initAlarmQueue(alarms: List<AlarmSummary>) {
        mutableListOf<String>().apply {
            alarms.forEach { alarm ->
                if (!isDeactivatedAlarm(alarm.groupId)) {
                    addAll(alarm.group.alarmDays.map { alarm.group.parse(it) })
                }
            }
        }.also {
            alarmQueue.addAll(it)
            Log.d(TAG, "$alarmQueue")
        }
    }

    private fun observeNextAlarmTime() = viewModelScope.launch {
        while (active) {
            delay(1000L)
            _fastestAlarmTimeFlow.collect {
                val duration = DateTimeManager.diffFromNow(it)
                _fastestAlarmTimeState.value = DateTimeManager.parseToDay(duration)
                if (duration == 0L) {
                    val lastest = alarmQueue.poll()
                    alarmQueue.add(lastest)
                }
            }
        }
    }

    fun resume() {
        if (!active) {
            active = true
            observeNextAlarmTime()
        }

        fetchAlarmList()
    }

    fun pause() {
        active = false
    }

    override fun onCleared() {
        active = false
        alarmQueue.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "AlarmListViewModel"

        const val LOADING = "-"
        const val NO_ACTIVATED_ALARM = "_"
    }
}