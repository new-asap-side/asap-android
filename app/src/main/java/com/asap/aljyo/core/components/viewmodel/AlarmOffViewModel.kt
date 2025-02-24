package com.asap.aljyo.core.components.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.data.utility.DateTimeManager
import com.asap.domain.usecase.alarm.AlarmOffUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AlarmOffViewModel @Inject constructor(
    private val alarmOffUseCase: AlarmOffUseCase
) : ViewModel(){
    private var active = true

    private val _state = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val state get() = _state.asStateFlow()

    private val _currentTime = mutableStateOf("")
    val currentTime get() = _currentTime.value

    private val currentTimeFlow = flow {
        val currentTime = DateTimeManager.formatCurrentTime()
        emit(currentTime)
    }

    init {
        viewModelScope.launch {
            while (active) {
                currentTimeFlow.collect { currentTime ->
                    _currentTime.value = currentTime
                }
                delay(1000)
            }
        }
    }

    fun alarmOff(groupId: Int) = viewModelScope.launch {
        _state.value = RequestState.Loading
        alarmOffUseCase(groupId = groupId).catch { e ->
            val errorCode = when(e) {
                is HttpException -> e.code()
                else -> -1
            }

            _state.value = RequestState.Error(errorCode)
        }.collect { response ->
            val result = response?.result ?: false
            _state.value = RequestState.Success(result)
        }
    }
}