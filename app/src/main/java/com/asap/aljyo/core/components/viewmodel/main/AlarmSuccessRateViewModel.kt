package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.UserViewModel
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.alarm.AlarmOffRate
import com.asap.domain.usecase.alarm.FetchAlarmOffRateUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSuccessRateViewModel @Inject constructor(
    userInfoUseCase: GetUserInfoUseCase,
    private val fetchAlarmSuccessRateUseCase: FetchAlarmOffRateUseCase
) : UserViewModel(userInfoUseCase) {
    private val _successRateState = MutableStateFlow<UiState<AlarmOffRate?>>(UiState.Loading)
    val successRateState get() = _successRateState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchAlarmSuccessRateUseCase().catch {
                _successRateState.emit(UiState.Success(null))
            }.collect { result ->
                _successRateState.emit(UiState.Success(result))
            }
        }
    }
}