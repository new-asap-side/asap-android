package com.asap.aljyo.core.components.release_alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.alarm.AlarmOffUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReleaseAlarmViewModel @Inject constructor(
    private val alarmOffUseCase: AlarmOffUseCase
) : ViewModel(){
    private val _rState = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val rState get() = _rState.asStateFlow()

    fun alarmOff(groupId: Int) = viewModelScope.launch {
        _rState.value = RequestState.Loading
        alarmOffUseCase(groupId = groupId).catch { e ->
            val errorCode = when(e) {
                is HttpException -> e.code()
                else -> -1
            }

            _rState.value = RequestState.Error(errorCode)
        }.collect { response ->
            val result = response?.result ?: false
            _rState.value = RequestState.Success(result)
        }
    }
}