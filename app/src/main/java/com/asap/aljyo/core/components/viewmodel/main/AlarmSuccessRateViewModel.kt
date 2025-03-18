package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.UserViewModel
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.alarm.AlarmOffRate
import com.asap.domain.usecase.alarm.FetchAlarmOffRateUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSuccessRateViewModel @Inject constructor(
    userInfoUseCase: GetUserInfoUseCase,
    private val fetchAlarmOffRateUseCase: FetchAlarmOffRateUseCase
) : UserViewModel(userInfoUseCase) {
    private val _offRateState = MutableStateFlow<UiState<AlarmOffRate?>>(UiState.Loading)
    val successRateState get() = _offRateState.asStateFlow()

    private val mascotVisible = flow {
        println("flow block")
        emit(true)
    }.shareIn(viewModelScope, started = SharingStarted.WhileSubscribed())

    fun fetchOffRate() {
        viewModelScope.launch(Dispatchers.Default) {
            fetchAlarmOffRateUseCase().catch {
                _offRateState.emit(UiState.Error(null))
            }.collect { result ->
                result?.let { offRate ->
                    _offRateState.emit(UiState.Success(offRate))
                }
            }
        }
    }

    fun animate() {
        viewModelScope.launch {
            mascotVisible.collect {  }
        }
    }
}