package com.asap.aljyo.components.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.usecase.user.FetchAlarmListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val fetchAlarmListUseCase: FetchAlarmListUseCase
) : ViewModel() {
    private val _alarmList = MutableStateFlow<UiState<List<Alarm>?>>(UiState.Loading)
    val alarmList get() = _alarmList.asStateFlow()

    init { fetchAlarmList() }

    fun fetchAlarmList() = viewModelScope.launch {
        fetchAlarmListUseCase.invoke().catch {
            _alarmList.value = UiState.Error()
        }.collect { result ->
            if (result == null) {
                _alarmList.value = UiState.Error()
                return@collect
            }
            _alarmList.value = UiState.Success(result)
        }
    }
}