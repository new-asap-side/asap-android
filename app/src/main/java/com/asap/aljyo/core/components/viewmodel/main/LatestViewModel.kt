package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.NetworkViewModel
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.usecase.group.FetchLatestGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestViewModel @Inject constructor(
    private val fetchLatestGroupUseCase: FetchLatestGroupUseCase,
) : NetworkViewModel() {
    private val _latestState = MutableStateFlow<UiState<List<AlarmGroup>>>(UiState.Loading)
    val latestState get() = _latestState.asStateFlow()

    init {
        fetchLatestGroup()
    }

    private fun fetchLatestGroup() {
        viewModelScope.launch {
            fetchLatestGroupUseCase()
                .catch { e -> _latestState.emit(handleThrowable(e)) }
                .collect { result ->
                    _latestState.emit(UiState.Success(result ?: emptyList()))
                }
        }
    }
}